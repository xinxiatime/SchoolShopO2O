package com.lzr.service;


import com.lzr.BaseTest;
import com.lzr.dto.ShopExecution;
import com.lzr.entity.Area;
import com.lzr.entity.PersonInfo;
import com.lzr.entity.Shop;
import com.lzr.entity.ShopCategory;
import com.lzr.enums.ShopStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;
   /* @Test
    public void testAddShop() throws FileNotFoundException {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();

        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(10L);

        shop.setArea(area);
        shop.setOwner(owner);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试店铺service");
        shop.setShopDesc("testService");
        shop.setPhone("testService");
        shop.setAdvice("审核中");
        shop.setShopAddr("testService");
        shop.setPriority(1);
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setCreateTime(new Date());
        File shopImg = new File("C:\\Users\\ranran\\Pictures\\fusc.png");
        InputStream is = new FileInputStream(shopImg);
        ShopExecution shopExecution = shopService.addShop(shop, is,shopImg.getName());
        assertEquals(ShopStateEnum.CHECK.getState(),shopExecution.getState());
    }*/
}
