package com.yiran.product.service;

import com.yiran.model.entity.ProAttributeInfo;
import com.yiran.model.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * @return 商品集合
     */
    List<Product> getByBrandId(String BrandId);

    /**
     *
     * @param brandId
     * @param kind
     * @param sizeId
     * @param colorId
     * @return
     */
    List<ProAttributeInfo> getByBrandKindSizeColor(String brandId, String kind, String sizeId, String colorId);


}
