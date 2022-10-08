package com.yiran.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiran.model.entity.ProAttributeInfo;
import com.yiran.model.entity.Product;
import com.yiran.product.mapper.ColorMapper;
import com.yiran.product.mapper.ProAttributeInfoMapper;
import com.yiran.product.mapper.ProductMapper;
import com.yiran.product.mapper.SizeMapper;
import com.yiran.product.service.IProductService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Yang Song
 * @since 2022-10-03
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
    private ProductMapper productMapper;
    private SizeMapper sizeMapper;
    private ColorMapper colorMapper;
    private ProAttributeInfoMapper proAttributeInfoMapper;

    public ProductServiceImpl(ProductMapper productMapper, SizeMapper sizeMapper, ColorMapper colorMapper, ProAttributeInfoMapper proAttributeInfoMapper) {
        this.productMapper = productMapper;
        this.sizeMapper = sizeMapper;
        this.colorMapper = colorMapper;
        this.proAttributeInfoMapper = proAttributeInfoMapper;
    }

    @Override
    public List<Product> getByBrandId(String BrandId) {
        List<Product> products = productMapper.getByBrandId(BrandId);
        return products;
    }

    @Override
    public List<ProAttributeInfo> getByBrandKindSizeColor(String brandId, String kind, String sizeId, String colorId) {
        List<ProAttributeInfo> products = proAttributeInfoMapper.getByBrandKindSizeColor(brandId, kind, sizeId, colorId);
        return products;
    }
}

