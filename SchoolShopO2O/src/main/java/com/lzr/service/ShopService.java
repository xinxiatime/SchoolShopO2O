package com.lzr.service;

import com.lzr.dto.ImageHolder;
import com.lzr.dto.ShopExecution;
import com.lzr.entity.Shop;
import com.lzr.exceptions.ShopOperationException;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public interface ShopService {

    /**
     * 根据shopCondition分页返回相应的店铺列表
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);

    /**
     * 新增店铺
     * @param shop
     * @param thumbnail
     * @return
     */
    ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

    /**
     * 通过shopid获取店铺信息
     * @param ShopId
     * @return
     */
    Shop getByShopId(long ShopId);

    /**
     * 更新商铺的信息，包括对图片的处理
     * @param shop
     * @param thumbnail
     * @return
     */
    ShopExecution modifyShop(Shop shop,ImageHolder thumbnail) throws ShopOperationException;
}
