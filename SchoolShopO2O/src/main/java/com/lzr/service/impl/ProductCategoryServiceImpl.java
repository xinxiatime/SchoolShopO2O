package com.lzr.service.impl;

import com.lzr.dao.ProductCategoryDao;
import com.lzr.dao.ProductDao;
import com.lzr.dto.ProductCategoryExecution;
import com.lzr.entity.ProductCategory;
import com.lzr.enums.ProductCategoryStateEnum;
import com.lzr.exceptions.ProductOperationException;
import com.lzr.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    ProductCategoryDao productCategoryDao;
    @Autowired
    private ProductDao productDao;
    @Override
    public List<ProductCategory> getProductCategoryList(Long shopId) {

        return productCategoryDao.quaryProductCategoryList(shopId);
    }

    @Override
    @Transactional
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductOperationException {
        if(productCategoryList != null && productCategoryList.size() > 0){
            try {
                int effNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
                if (effNum <= 0){
                    throw new ProductOperationException("店铺类别创建失败");
                }else {
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            }catch (Exception e){
                throw new ProductOperationException("batchInsertProductCategory error:" + e.getMessage());
            }
        }else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }
    }

    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductOperationException {
        //先解除tb_product里的商品与该productcategoryId的关联
        try {
            int effNum = productDao.updateProductCategoryToNull(productCategoryId);
            if(effNum < 0){
                throw new ProductOperationException("商品类别更新失败");
            }
        }catch (Exception e){
            throw new ProductOperationException("deleteProductCategory error:" + e.getMessage());
        }
        //删除该productcategory
        try {
            int effNum = productCategoryDao.deleteProductCategory(productCategoryId,shopId);
            if (effNum <= 0){
                throw new ProductOperationException("商品类别删除失败");
            }else {
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        }catch (Exception e){
            throw new ProductOperationException("删除商品类别失败" + e.getMessage());
        }
    }

}
