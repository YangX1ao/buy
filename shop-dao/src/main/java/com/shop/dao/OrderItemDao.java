package com.shop.dao;

import com.shop.core.model.OrderItem;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yangxiao on 2017/5/7.
 */
@Repository
public interface OrderItemDao {

    void insertBatch(List<OrderItem> orderItems);
}
