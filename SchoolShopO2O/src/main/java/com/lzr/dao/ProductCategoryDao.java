package com.lzr.dao;


import com.lzr.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ProductCategoryDao {
    /**
     * 通过店铺id查询店铺商品类别
     * @param shopId
     * @return
     */
    List<ProductCategory> quaryProductCategoryList(Long shopId);

    /**
     * 批量新增商品类别
     * @param productCategoryList
     * @return
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);

    int deleteProductCategory(@Param("productCategoryId")long producuteCategoryId,
                              @Param("shopId")long shopId);
}


