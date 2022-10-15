package com.yiran.product.service;

import com.yiran.model.vo.ProductVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Yang Song
 * @since 2022-10-03
 */
public interface IProductService{
    /**
     * 根据品牌id查询商品
     * @param brandId 品牌id
     * @return 商品集合
     */
    List<ProductVO> getByBrandId(String brandId);

    /**
     * 多条件筛选
     * @param brandId 品牌id
     * @param kindIdArr 品类id数组
     * @param sizeIdArr 尺码id数组
     * @param colorIdArr 颜色id数组
     * @return 符合筛选条件的商品
     */
    List<ProductVO> getByBrandKindSizeColor(String brandId, String[] kindIdArr, String[] sizeIdArr, String[] colorIdArr);


}
