package com.yiran.product.mapper;

import com.yiran.model.entity.ProAttributeInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiran.model.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Yang Song
 * @since 2022-10-03
 */
@Mapper
public interface ProAttributeInfoMapper extends BaseMapper<ProAttributeInfo> {
    /**
     * 根据品牌，品类，尺码，颜色 单选查询商品
     * @param brandId 品牌id
     * @param kind 品类类别
     * @param sizeId 尺码id
     * @param colorId 颜色id
     * @return
     */
    List<ProAttributeInfo> getByBrandKindSizeColor(String brandId, String kind, String sizeId, String colorId);
}
