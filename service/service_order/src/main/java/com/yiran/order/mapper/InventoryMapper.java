package com.yiran.order.mapper;

import com.yiran.model.entity.Inventory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Yang Song
 * @since 2022-10-03
 */
@Mapper
public interface InventoryMapper extends BaseMapper<Inventory> {
    /**
     * 扣减库存
     * @param proAttributeInfoId 商品属性id
     * @param nums 商品数量
     * @return 影响行数
     */
    int deductInventory(@Param("proAttributeInfoId") String proAttributeInfoId,
                        @Param("nums") Integer nums);

    /**
     * 恢复库存
     * @param proAttributeInfoId 商品属性id
     * @param nums 数量
     * @return 影响行数
     */
    int restoreInventory(@Param("proAttributeInfoId") String proAttributeInfoId,
                         @Param("nums") Integer nums);
}
