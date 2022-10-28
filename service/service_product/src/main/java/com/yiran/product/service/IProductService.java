package com.yiran.product.service;

import com.yiran.model.vo.FiltrateVO;
import com.yiran.model.vo.ProductDetailVO;
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
     * 根据品类id查商品
     * @param kindId 品类id
     * @return 商品集合
     */
    List<ProductVO> getByKindId(String kindId);

    /**
     * 多条件筛选
     * @param brandId 品牌id
     * @param kindIdArr 品类id数组
     * @param sizeIdArr 尺码id数组
     * @param colorIdArr 颜色id数组
     * @return 符合筛选条件的商品
     */
    List<ProductVO> getByBrandKindSizeColor(String brandId, String[] kindIdArr, String[] sizeIdArr, String[] colorIdArr);

    /**
     * 根据商品id点击查询商品详情
     * @param proId 商品id
     * @param userId 用户id
     * @return 商品详情
     */
    ProductDetailVO getByProId(String proId,String userId);

    /**
     * 查询筛选栏栏
     * @param brandId 品牌id
     * @param kindId 品类id
     * @return 筛选栏
     */
    FiltrateVO getFiltrate(String brandId, String kindId);
}
