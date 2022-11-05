package com.yiran.product.service;

import com.yiran.model.vo.FiltrateVO;
import com.yiran.model.vo.ProductDetailVO;
import com.yiran.model.vo.ProductInfoNumVO;
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
     * @param brandArr 品牌id
     * @param kindIdArr 品类id数组
     * @param sizeIdArr 尺码id数组
     * @param colorIdArr 颜色id数组
     * @return 符合筛选条件的商品
     */
    List<ProductVO> getByBrandKindSizeColor(String[] brandArr, String[] kindIdArr, String[] sizeIdArr, String[] colorIdArr);

    /**
     * 根据商品id点击查询商品详情
     * @param proId 商品id
     * @return 商品详情
     */
    ProductDetailVO getByProId(String proId);

    /**
     * 查询筛选栏栏
     * @param brandId 品牌id
     * @param kindId 品类id
     * @return 筛选栏
     */
    FiltrateVO getFiltrate(String brandId, String kindId);

    /**
     * 根据商品id，颜色id，尺码id查询商品属性id
     * @param proId 商品id
     * @param sizeId 颜色id
     * @param colorId 尺码id
     * @return 商品属性id
     */
    ProductInfoNumVO getProInfoId(String proId, String sizeId, String colorId);

    /**
     * ES根据传入的商品名字或商品描述搜索商品
     * @return 商品集合
     */
    List<ProductVO> getAllProductVo();
}
