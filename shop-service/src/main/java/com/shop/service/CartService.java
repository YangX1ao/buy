package com.shop.service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shop.core.base.BaseDto;
import com.shop.core.base.ResultListInfo;
import com.shop.core.model.Cart;
import com.shop.core.model.CartItem;
import com.shop.core.model.Product;
import com.shop.core.util.AssertUtil;
import com.shop.core.util.ResultListInfoUtil;
import com.shop.dao.CartDao;
import com.shop.dao.CartItemDao;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by yangxiao on 2017/5/5.
 */
@Service
public class CartService {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private CartItemDao cartItemDao;

    @Autowired
    private ProductService productService;
    /**
     * 1. 将商品添加到购物车：
     *    a）基本参数验证：productId, quantity, userId
     *    b）商品库存判断:xx_product
     *    c）用户是否存在购物车xx_cart
     *    d）用户是否将此商品添加过：添加过：更新数量，没有就插入数据
     */

    /***
     * 添加商品到购物车
     * @param productId
     * @param quantity
     * @param loginUserId
     * @param goodsId
     */
    public void add(Integer productId,Integer quantity,Integer loginUserId,Integer goodsId){
        //基本参数验证
        AssertUtil.isTrue(productId==null || productId<1 && (goodsId == null || goodsId < 1),"请选择要添加的商品！");
        AssertUtil.isTrue(quantity==null ||quantity<1,"请选择要添加商品的数量！");
        AssertUtil.isTrue(loginUserId==null ||loginUserId<1,"请登录后在进行操作！");


        Product product =null;
        if(goodsId!=null){
            Product defaultProduct = productService.findGoodsDefaultProduct(goodsId);
            AssertUtil.isTrue(defaultProduct==null,"该商品不存在！");
            productId=defaultProduct.getId();
        }
        //商品库存查询
        if(product==null){
            product=productService.findById(productId);
        }
        AssertUtil.isTrue(product==null,"该商品不存在！");
        //库存判断
        AssertUtil.isTrue(product.getAvailableStock()<quantity,"该商品的库存量不足！");

        //用户的购物车查询
        Cart cart = cartDao.findByMemberId(loginUserId);
        if(cart==null){
            //添加购物车
            cart=insert(null,loginUserId);
            addItem(productId,quantity,cart.getId());
        }
        //查询商品是否存在，存在则只更改数量
        CartItem cartItem = cartItemDao.findByCart(cart.getId(),productId);
        if(cartItem==null){
            addItem(productId,quantity,cart.getId());
        }else{
            cartItemDao.updateCartItem(cartItem.getQuantity()+quantity,cartItem.getId());
        }

    }


    /***
     * 分页展示购物车中的数据
     * @param baseDto
     * @param loginUserId
     * @return
     */
    public ResultListInfo<CartItem> selectForPage(BaseDto baseDto, Integer loginUserId) {

        AssertUtil.isTrue(loginUserId == null || loginUserId < 1, "请先登录,在进行操作！");
        baseDto.setSort("modify_date.desc");
        // 获取当前的购物车
        Cart cart = getCurrentCart(loginUserId);
        PageList<CartItem> cartItems = cartItemDao.selectForPage(baseDto.toPageBounds(), cart.getId());

        ResultListInfoUtil<CartItem> result = new ResultListInfoUtil<>();
        return result.buildSuccessResultList(cartItems, baseDto);
    }

    /***
     * 修改购物车中商品的数量
     * @param cartItemId
     * @param quantity
     * @param loginUserId
     */
    public void editCartItemQuantity(Integer cartItemId, Integer quantity, Integer loginUserId) {
        //基本参数验证
        AssertUtil.isTrue(quantity==null ||quantity<1,"请输入选择需要的个数！");
        //购物车验证
        CartItem cartItem = checkMemeberCart(cartItemId, loginUserId);
       //商品库存验证
        Product product= productService.findById(cartItem.getProduct());
        AssertUtil.isTrue(product==null,"该商品不存在，请移除！");
        AssertUtil.isTrue(product.getAvailableStock()<quantity,"库存不足，请减少个数！");
        cartItemDao.updateCartItem(quantity,cartItemId);
    }

    /***
     * 删除购物车中的商品
     * @param id
     * @param loginUserId
     */
    public void delete(Integer id, Integer loginUserId) {
        CartItem cartItem = checkMemeberCart(id, loginUserId);
        cartItemDao.delete(id);

    }

    /***
     * 清空购物车的商品
     * @param loginUserId
     */
    public void clear(Integer loginUserId) {
        Cart cart = getCurrentCart(loginUserId);
        //将cart中的version置为-1
        //将cartItem的veision置为-1
        cartItemDao.clear(cart.getId());
    }

    /***
     * 统计购物车中的商品个数
     * @param loginUserId
     * @return
     */
    public Integer countQuantity(Integer loginUserId) {
        Cart cart = getCurrentCart(loginUserId);
        Integer count= cartItemDao.countQuantity(cart.getId());
        return count;
    }

    /**
     * 获取订单中商品
     * @param cartItemIds
     * @return
     */
    public List<CartItem> findCartItemByIds(String cartItemIds) {
        AssertUtil.notNull(cartItemIds,"请选择商品进行提交！");
        List<CartItem> cartItems=cartItemDao.findCartItemByIds(cartItemIds);
        AssertUtil.isTrue(cartItems == null || cartItems.size() < 1, "请选择正确的商品进行提交");
        return cartItems;

    }


    /***
     * 添加购物车
     * @param cartKey
     * @param member
     * @return
     */
    public Cart insert(String cartKey,Integer member){

           Cart cart=new Cart();
            if(StringUtils.isBlank(cartKey)){
                cartKey= DigestUtils.md5Hex(UUID.randomUUID()+ RandomStringUtils.randomAlphabetic(30));
            }

        cart.setCartKey(cartKey);
        Date now = new Date();
        cart.setCreateDate(now);
        cart.setExpire(new Date(now.getTime() + Cart.TIMEOUT));
        cart.setMember(member);
        cart.setModifyDate(now);
        cart.setVersion(0);
        cartDao.insert(cart);
        return cart;
    }

    /***
     * 添加购物车详情
     * @param productId
     * @param quantity
     * @param cartId
     * @return
     */
    public void addItem(Integer productId,Integer quantity,Integer cartId){
        CartItem cartItem=new CartItem();
        cartItem.setQuantity(quantity);
        cartItem.setCart(cartId);
        cartItem.setProduct(productId);
        cartItemDao.addItem(cartItem);
    }



    /***
     * 购物车验证
     * @param cartItemId
     * @param loginUserId
     * @return
     */
    public CartItem checkMemeberCart(Integer cartItemId, Integer loginUserId) {
        // 基本参数验证
        AssertUtil.isTrue(cartItemId == null || cartItemId < 1, "请选择要修改的商品");
        AssertUtil.isTrue(loginUserId == null || loginUserId < 1, "请登录");
        //获取当前的购物车
        Cart cart = getCurrentCart(loginUserId);
        CartItem cartItem = cartItemDao.findById(cartItemId);
        AssertUtil.isTrue(!cart.getId().equals(cartItem.getCart()),"该购物车不包含此商品！");
        return cartItem;
    }
    /**
     * 获取到当前购物车
     * @param loginUserId
     * @return
     */
    public Cart getCurrentCart(Integer loginUserId) {
        Cart cart = cartDao.findByMemberId(loginUserId);
        AssertUtil.isTrue(cart == null, "该购物车不存在");
        return  cart;
    }

}
