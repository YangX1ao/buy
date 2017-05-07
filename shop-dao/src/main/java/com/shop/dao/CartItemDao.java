package com.shop.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shop.core.model.Cart;
import com.shop.core.model.CartItem;
import com.shop.core.model.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangxiao on 2017/5/5.
 */
@Repository
public interface CartItemDao {
    //购物车唯一和商品唯一
    @Select("SELECT id,quantity,product from xx_cart_item WHERE cart=#{cartId} " +
            " and product=#{productId} and version>-1")
    CartItem findByCart(@Param(value="cartId") Integer cartId,@Param(value = "productId") Integer productId);

    @Insert("insert into xx_cart_item (create_date,modify_date,version,quantity,cart,product"
            + " ) values (now(), now(), 0, #{quantity}, #{cart}, #{product})")
    void addItem(CartItem cartItem);

    @Select("UPDATE xx_cart_item set quantity=#{quantity} , modify_date = now() where id=#{id}")
    void updateCartItem(@Param(value = "quantity")Integer quantity,@Param(value ="id") Integer id);

    PageList<CartItem> selectForPage(PageBounds pageBounds,@Param(value ="cartId") Integer cartId);

    @Select("select id, quantity, cart, product from xx_cart_item where id=${cartItemId} and version>-1")
    CartItem findById(@Param(value = "cartItemId") Integer cartItemId);

    @Update("UPDATE xx_cart_item set version=-1 where id=${id}")
    void delete(@Param(value = "id") Integer id);


    @Update("UPDATE xx_cart_item set version=-1 where cart=${id}")
    void clear(@Param(value = "id")Integer id);

    @Select("SELECT sum(quantity) from xx_cart_item WHERE cart=${id} AND version>-1")
    Integer countQuantity(@Param(value = "id") Integer id);

    List<CartItem> findCartItemByIds(@Param(value = "ids") String cartItemIds);

    @Update("update xx_cart_item set version = #{status} where id in (${ids})")
    void updateStatus(@Param(value="ids") String cartItemIds, @Param(value="status") int status);
}
