package com.shop.service;

import com.shop.core.constant.OrderStatus;
import com.shop.core.constant.OrderType;
import com.shop.core.model.*;
import com.shop.core.util.AssertUtil;
import com.shop.core.util.IdWorker;
import com.shop.core.util.MathUtil;
import com.shop.core.vo.PayRequestVo;
import com.shop.dao.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangxiao on 2017/5/7.
 */
@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private CartService cartService;

    @Autowired
    private ReceiverDao receiverDao;

    @Autowired
    private CartItemDao cartItemDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private OrderItemDao orderItemDao;

    /***
     * 创建一个订单
     * @param loginUserId
     * @param cartItemIds
     * @param receiverId
     * @param memo
     * @return
     * @throws Exception
     */
    public String create(Integer loginUserId, String cartItemIds, Integer receiverId,
                         String memo) throws Exception {
        /*
         a) 基本参数校验
         b）明细中的商品校验：商品是否存在，库存是否足够
         c) 收货地址校验
         e) 添加订单（生成唯一的订单编号）
         f) 添加订单明细
         g) 扣库存(如果订单未支付或者支付失败后都有回收订单的机制)
         h) 从购物车中删除
         i) 返回订单编号
         */
        //基本参数校验
        AssertUtil.isTrue(loginUserId==null || loginUserId<1,"请登录后在进行操作！");
        AssertUtil.isTrue(StringUtils.isBlank(cartItemIds),"请选择要购买的商品！");
        AssertUtil.isTrue(receiverId==null || receiverId<1,"请选择收货地址！");

        // 商品以及库存验证
        // TODO 这两条明细是否存在于此用户的购物车中
        // 获取购物车
        List<CartItem> cartItems=cartService.findCartItemByIds(cartItemIds);
        Cart cart = cartService.getCurrentCart(loginUserId);
        BigDecimal totalAmount=BigDecimal.ZERO;
        int totalQuantity=0;
        long totalRewardPoints=0L;
        int totalWeight=0;
        for(CartItem cartItem:cartItems){
            //判断商品是否存在购物车中
            AssertUtil.isTrue(!cartItem.getCart().equals(cart.getId()),"该商品不在购物车中！");
            //判断库存是否足够
            AssertUtil.isTrue(cartItem.getProductInfo().getAvailableStock()<cartItem.getQuantity(),
                    "库存不足，请重新选择数量！");
            //获取订单总价    总价=单价 * 数量
            totalAmount=totalAmount.add(cartItem.getProductInfo().getPrice().multiply(new BigDecimal(cartItem.getQuantity())));
            totalQuantity+=cartItem.getQuantity();
            totalRewardPoints+=cartItem.getProductInfo().getRewardPoint()*cartItem.getQuantity();
            totalWeight+=cartItem.getProductInfo().getWeight()*cartItem.getQuantity();
        }
        //收货地址验证

        Receiver receiver=receiverDao.findById(receiverId);
        AssertUtil.isTrue(receiver==null,"该收货地址不存在，请重新选择！");
        AssertUtil.isTrue(!loginUserId.equals(receiver.getMember()),"该收货地址不存在，请重新选择！");

        //插入订单表
        String sn=generateOrderSn(); //订单编号
        Order order=insertOrder(sn, totalAmount, totalRewardPoints, 0L,
                totalWeight, totalQuantity, receiver, memo, loginUserId);

        //商品插入订单详情表
        addOrderItems(cartItems,order.getId());

        //移除购物车中的商品
        cartItemDao.updateStatus(cartItemIds,-2);

        return  sn;
    }



    /***
     * 插入数据库
     * @param sn
     * @param totalPrice
     * @param totalRewardPoints
     * @param totalExchangePoint
     * @param totalWeight
     * @param totalQuantity
     * @param receiver
     * @param memo
     * @param loginUserId
     * @return
     */
    private Order insertOrder(String sn, BigDecimal totalPrice, long totalRewardPoints, long totalExchangePoint,
                              int totalWeight, int totalQuantity, Receiver receiver, String memo,
                                    Integer loginUserId) {

        Order order=new Order();
        order.setSn(sn);
        order.setType(OrderType.general.getType());
        order.setPrice(MathUtil.setScale(totalPrice));
        order.setFee(BigDecimal.ZERO);
        order.setFreight(BigDecimal.ZERO);
        order.setPromotionDiscount(BigDecimal.ZERO);
        order.setOffsetAmount(BigDecimal.ZERO);
        order.setAmountPaid(BigDecimal.ZERO);
        order.setRefundAmount(BigDecimal.ZERO);
        order.setRewardPoint(totalRewardPoints);
        order.setExchangePoint(totalExchangePoint);
        order.setWeight(totalWeight);
        order.setQuantity(totalQuantity);
        order.setShippedQuantity(0);
        order.setReturnedQuantity(0);

        //订单地址
        order.setConsignee(receiver.getConsignee());
        order.setAreaName(receiver.getAreaName());
        order.setAddress(receiver.getAddress());
        order.setZipCode(receiver.getZipCode());
        order.setPhone(receiver.getPhone());
        order.setArea(receiver.getArea());

        order.setMemo(memo);
        order.setIsUseCouponCode(false);
        order.setIsExchangePoint(false);
        order.setIsAllocatedStock(false);
        order.setMember(loginUserId);
        order.setAmount(MathUtil.setScale(totalPrice));
        order.setCouponDiscount(BigDecimal.ZERO);
        order.setTax(BigDecimal.ZERO);

        //保存订单
        orderDao.insert(order);
        return  order;
    }

    /**
     * 生成订单编号
     * @return
     * @throws Exception
     */
    private String generateOrderSn() throws Exception  {
        Integer lastOrderId = orderDao.findLastOrderId();
        if (lastOrderId == null) {
            lastOrderId = 0;
        }
        IdWorker idWorker = new IdWorker(lastOrderId);
        String sn = idWorker.nextId();
        return sn;
    }

    /***
     * 添加明细并更新数据库
     * @param cartItems
     * @param orderId
     */
    private void addOrderItems(List<CartItem> cartItems, Integer orderId) {

        List<OrderItem> orderItems=new ArrayList<>();
        for(CartItem cartItem:cartItems){
            Product product=cartItem.getProductInfo();
            OrderItem orderItem=new OrderItem();
            orderItem.setSn(product.getSn());
            orderItem.setName(product.getName());
            orderItem.setType(product.getType());
            orderItem.setPrice(product.getPrice());
            orderItem.setWeight(product.getWeight());
            orderItem.setIsDelivery(product.getIsDelivery());
            orderItem.setThumbnail(product.getThumbnail());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setShippedQuantity(0);
            orderItem.setReturnedQuantity(0);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setOrders(orderId);
            orderItem.setSpecifications(product.getSpecificationValues());

            orderItems.add(orderItem);
            //扣除库存(如果订单未支付或者支付失败后都有回收订单的机制)
            int upt=productDao.updateAllocatedStock(product.getId(),product.getAllocatedStock()+cartItem.getQuantity());
            AssertUtil.isTrue(upt==0,"商品["+product.getName()+"]库存不足");//避免高并发情况下多kou
        }
        orderItemDao.insertBatch(orderItems);//批量插入


    }



}
