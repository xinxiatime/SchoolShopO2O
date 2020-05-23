package com.lzr.dao;

import com.lzr.BaseTest;
import com.lzr.entity.Area;
import com.lzr.entity.PersonInfo;
import com.lzr.entity.Shop;
import com.lzr.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao;
    @Test
    public void testInsertShop (){
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
        shop.setShopName("新增店铺");
        shop.setShopDesc("test");
        shop.setPhone("test");
        shop.setAdvice("审核中");
        shop.setShopAddr("test");
        shop.setShopImg("test");
        shop.setPriority(1);
        shop.setEnableStatus(1);
        shop.setCreateTime(new Date());
        int i = shopDao.insertShop(shop);
        assertEquals(1,i);
    } @Test
    public void testUpdateShop (){
        Shop shop = new Shop();

        shop.setShopId(36L);
        shop.setShopName("测试更新店铺");
        shop.setShopDesc("测试描述");
        shop.setShopAddr("测试地址");
        shop.setLastEditTime(new Date());
        int i = shopDao.updateShop(shop);
        assertEquals(1,i);
    }
    @Test
    public void testSelectShopById(){
        Shop shop = shopDao.selectByShopId(1L);
        System.out.println("areaId:"+shop.getArea().getAreaId());
        System.out.println("areaName:"+shop.getArea().getAreaName());

    }
}
