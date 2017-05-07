package com.shop.service;

import com.shop.core.model.Attribute;
import com.shop.dao.AttributeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangxiao on 2017/5/3.
 */
@Service
public class AttributeService {

    @Autowired
    private AttributeDao attributeDao;

    public List<Attribute> findAttributeList(Integer productCategoryId){
        List<Attribute> attributeList = attributeDao.findAttributeList(productCategoryId);
        return attributeList;
    }
}
