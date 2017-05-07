package com.shop.service;

import com.shop.core.model.Product;
import com.shop.core.util.AssertUtil;
import com.shop.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangxiao on 2017/5/3.
 */
@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    /***
     * 返回默认商品信息
     * @param goodsId
     * @return
     */
    public Product findGoodsDefaultProduct(Integer goodsId){
        AssertUtil.isTrue(goodsId==null ||goodsId<1,"请选择货品！");
        Product goodsDefaultProduct = productDao.findGoodsDefaultProduct(goodsId);
        AssertUtil.isTrue(goodsDefaultProduct==null,"该货品没有默认信息，请确认后查询！");
        return goodsDefaultProduct;
    }

    /***
     * 返回所有商品的所有信息
     * @param goodsId
     * @return
     */
    public List<Product> findGoodsProducts(Integer goodsId){
        AssertUtil.isTrue(goodsId==null ||goodsId<1,"请选择货品！");
        List<Product> goodsProducts = productDao.findGoodsProducts(goodsId);
        AssertUtil.isTrue(goodsProducts==null,"该货品不存在，请确认后查询！");
        return goodsProducts;
    }

    /***
     * 查询商品是否存在
     * @param productId
     * @return
     */
    public Product findById(Integer productId){
        AssertUtil.isTrue(productId==null ||productId<1,"请选择商品！");
        Product product = productDao.findById(productId);
        AssertUtil.isTrue(product==null,"该商品不存在！");
        return  product;
    }


}
