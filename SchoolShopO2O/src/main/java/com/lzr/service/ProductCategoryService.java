package com.lzr.service;

import com.lzr.dto.ProductCategoryExecution;
import com.lzr.entity.ProductCategory;
import com.lzr.exceptions.ProductOperationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductCategoryService {
    /**
     * 查询指定某个店铺下的所有商品类别信息
     * @param shopId
     * @return
     */
    List<ProductCategory> getProductCategoryList(Long shopId);

    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
        throws ProductOperationException;

    /**
     * 将此类别的商品里的类别id设置为空，再删除掉该商品的类别
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws ProductOperationException
     */
    ProductCategoryExecution deleteProductCategory(long productCategoryId,long shopId) throws ProductOperationException;




}
