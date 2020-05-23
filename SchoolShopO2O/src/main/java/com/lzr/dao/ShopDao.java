package com.lzr.dao;

import com.lzr.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopDao {

    /**
     * 分页查询店铺，可输入条件有：店铺名（模糊查询 like）,店铺状态，店铺类别，区域id，owner
     * @param shopCondition @Param参数的唯一标识 根据这个唯一的标识去取参数才能取到唯一的值
     * @param rowIndex 从第几行开始取数据
     * @param pageSize 返回的条数
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,
                             @Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);


    /**
     * 返回queryShopList 总数
     * @param shopCondition
     * @return
     */
    int queryShopCount(@Param("shopCondition") Shop shopCondition);

    /**
     *新增店铺
     * @param shop
     * @return
     */
    int insertShop(Shop shop);

    /**
     * 更新店铺
     * @param shop
     * @return
     */
    int updateShop(Shop shop);

    /**
     * 通过shop id查询店铺的信息
     * @param id
     * @return
     */
    Shop selectByShopId(Long id);

}
