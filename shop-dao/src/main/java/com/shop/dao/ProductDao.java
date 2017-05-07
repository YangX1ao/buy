package com.shop.dao;

import com.shop.core.model.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangxiao on 2017/5/3.
 */
@Repository
public interface ProductDao {

    @Select("select id, allocated_stock,cost,exchange_point,market_price,"
            + "price,reward_point,sn, specification_values, stock from "
            + "xx_product where goods = #{goodsId} and is_default = 1")
    Product findGoodsDefaultProduct(@Param(value = "goodsId") Integer goodsIs);

    @Select("select id, allocated_stock,cost,exchange_point,market_price,"
            + "price,reward_point,sn, specification_values, stock from "
            + "xx_product where goods = #{goodsId}")
    List<Product> findGoodsProducts(@Param(value = "goodsId") Integer goodsId);


    @Select("select id, allocated_stock,cost,exchange_point,market_price,"
            + "price,reward_point,sn, specification_values, stock from "
            + "xx_product where id = #{productId}")
    Product findById(@Param(value="productId") Integer productId);

    @Update("update xx_product set allocated_stock = #{allocatedStock} where id = #{id} and stock - #{allocatedStock} >= 0")
    int updateAllocatedStock(@Param(value="id")Integer id, @Param(value="allocatedStock")int allocatedStock);
}
