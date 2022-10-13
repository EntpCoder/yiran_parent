package com.yiran.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiran.model.entity.Color;
import com.yiran.model.entity.ProAttributeInfo;
import com.yiran.model.entity.Product;
import com.yiran.model.entity.Size;
import com.yiran.model.vo.ProductVO;
import com.yiran.product.mapper.ColorMapper;
import com.yiran.product.mapper.ProAttributeInfoMapper;
import com.yiran.product.mapper.ProductMapper;
import com.yiran.product.mapper.SizeMapper;
import com.yiran.product.service.IProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private final ColorMapper colorMapper;
    private final ProAttributeInfoMapper proAttributeInfoMapper;
    private final SizeMapper sizeMapper;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ColorMapper colorMapper, ProAttributeInfoMapper proAttributeInfoMapper, SizeMapper sizeMapper, ProductMapper productMapper) {
        this.colorMapper = colorMapper;
        this.proAttributeInfoMapper = proAttributeInfoMapper;
        this.sizeMapper = sizeMapper;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductVO> getByBrandId(String brandId) {
        List<ProductVO> productVos = new ArrayList<>();

        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("brand_id",brandId);
        List<Product> products = productMapper.selectList(queryWrapper);
        //商品的信息
        for(Product p : products){
            ProductVO productVO = new ProductVO();
            BeanUtils.copyProperties(p,productVO);

            QueryWrapper<ProAttributeInfo> queryWrappers = new QueryWrapper<>();
            queryWrappers.eq("pro_id",p.getProId());
            List<ProAttributeInfo> proAttributeInfos = proAttributeInfoMapper.selectList(queryWrappers);
            for (ProAttributeInfo proAttributeInfo: proAttributeInfos) {
                Color color = colorMapper.selectById(proAttributeInfo.getColorId());
                BeanUtils.copyProperties(color,productVO);
                Size size = sizeMapper.selectById(proAttributeInfo.getSizeId());
                BeanUtils.copyProperties(size,productVO);
                productVos.add(productVO);
            }
        }
        return productVos;
    }

    @Override
    public List<ProAttributeInfo> getByBrandKindSizeColor(String brandId, String kind, String sizeId, String colorId) {
        return proAttributeInfoMapper.getByBrandKindSizeColor(brandId, kind, sizeId, colorId);
    }
}

