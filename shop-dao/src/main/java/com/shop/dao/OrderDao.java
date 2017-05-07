package com.shop.dao;

import com.shop.core.model.Order;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Created by yangxiao on 2017/5/7.
 */
@Repository
public interface OrderDao {

    @Select("select max(id) from xx_order")
    Integer findLastOrderId();

    void insert(Order order);

}
