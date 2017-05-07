package com.shop.dao;

import com.shop.core.model.Attribute;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yangxiao on 2017/5/3.
 */
@Repository
public interface AttributeDao {


    @Select("SELECT id, t.`name`, t.`options` FROM xx_attribute t "
            + "where t.product_category = #{productCategoryId} ORDER BY orders, property_index")
    List<Attribute> findAttributeList(@Param(value = "productCategoryId") Integer productCategoryId);
}
