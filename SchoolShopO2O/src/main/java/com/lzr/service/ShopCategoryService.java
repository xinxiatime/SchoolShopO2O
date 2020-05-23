package com.lzr.service;

import com.lzr.entity.ShopCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShopCategoryService {
    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
