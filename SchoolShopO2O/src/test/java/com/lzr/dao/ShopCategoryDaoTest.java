package com.lzr.dao;

import java.util.List;

import com.lzr.BaseTest;
import com.lzr.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;



public class ShopCategoryDaoTest extends BaseTest {
	@Autowired
	private ShopCategoryDao shopCategoryDao;

	@Test
	public void testQueryShopCategory() {
		ShopCategory testCategory = new ShopCategory();

		ShopCategory parentCategory = new ShopCategory();

		parentCategory.setShopCategoryId(10L);

		testCategory.setParent(parentCategory);

		List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(parentCategory);
		for (ShopCategory shopCategory:shopCategoryList
			 ) {
			System.out.println(shopCategory);
		}
	}

	@Test
	public void testFindAll(){
		List<ShopCategory> shopCategoryList = shopCategoryDao.findAll();
		for (ShopCategory s:shopCategoryList
			 ) {
			System.out.println(s);
		}
	}
}
