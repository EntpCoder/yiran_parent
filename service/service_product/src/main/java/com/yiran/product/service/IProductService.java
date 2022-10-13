package com.yiran.product.service;

import com.yiran.model.entity.ProAttributeInfo;
import com.yiran.model.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;
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
public interface IProductService extends IService<Product> {
    /**
     * 根据品牌id查询商品
     * @param brandId 品牌id
     * @return 商品集合
     */
    List<ProductVO> getByBrandId(String brandId);

    /**
     * 多条件筛选查询
     * @param brandId 品牌id
     * @param kind 品类id
     * @param sizeId 尺码id
     * @param colorId 颜色id
     * @return 商品集合
     */
    List<ProAttributeInfo> getByBrandKindSizeColor(String brandId, String kind, String sizeId, String colorId);


}
