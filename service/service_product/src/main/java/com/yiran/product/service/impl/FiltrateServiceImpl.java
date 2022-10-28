package com.yiran.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yiran.model.entity.*;
import com.yiran.model.vo.FiltrateVO;
import com.yiran.product.mapper.*;
import com.yiran.product.service.IFiltrateService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author by LvJunLong
 * @date 2022/10/26
 */
@Service
public class FiltrateServiceImpl implements IFiltrateService {
    private final ProductMapper productMapper;
    private final MultiMenuMapper multiMenuMapper;
    private final SizeMapper sizeMapper;
    private final ColorMapper colorMapper;
    private final BrandMapper brandMapper;

    public FiltrateServiceImpl(ProductMapper productMapper, MultiMenuMapper multiMenuMapper, SizeMapper sizeMapper, ColorMapper colorMapper, BrandMapper brandMapper) {
        this.productMapper = productMapper;
        this.multiMenuMapper = multiMenuMapper;
        this.sizeMapper = sizeMapper;
        this.colorMapper = colorMapper;
        this.brandMapper = brandMapper;
    }

    @Override
    public FiltrateVO getFiltrate(String brandId,String kindId) {
        FiltrateVO filtrateVO = new FiltrateVO();
        List<String> brandList = new ArrayList<>();
        List<String> kindList = new ArrayList<>();
        //只传品牌时
        if (kindId == null) {
            //根据品牌id查找品牌名
            Brand brand = brandMapper.selectById(brandId);
            brandList.add(brand.getBrandName());
            filtrateVO.setBrandList(brandList);
            //根据品牌id查询商品
            QueryWrapper<Product> brandWrapper = new QueryWrapper<>();
            brandWrapper.eq("brand_id", brandId);
            List<Product> products = productMapper.selectList(brandWrapper);
            //商品kindIds
            Set<String> kindIds = new HashSet<>();
            for (Product p : products) {
                kindIds.add(p.getKind());
            }
            //根据商品kindId查询kinds
            List<String> kinds = multiMenuMapper
                    .selectList(new QueryWrapper<MultiMenu>().select("title").in("menu_id", kindIds))
                    .stream()
                    .map(MultiMenu::getTitle)
                    .distinct()
                    .collect(Collectors.toList());
            filtrateVO.setKindList(kinds);
        }
        //只传品类时
        if (brandId == null){
            //根据品类id查询品类名
            MultiMenu menu = multiMenuMapper.selectById(kindId);
            kindList.add(menu.getTitle());
            filtrateVO.setKindList(kindList);
            //根据品类id查询商品
            QueryWrapper<Product> brandWrapper = new QueryWrapper<>();
            brandWrapper.eq("kind", kindId);
            List<Product> products = productMapper.selectList(brandWrapper);
            //商品的proIds
            List<String> proIds = new ArrayList<>();
            for (Product p : products){
                proIds.add(p.getProId());
            }
            //根据proIds查询brandIds
            List<String> brandIds = productMapper
                    .selectList(new QueryWrapper<Product>().select("brand_id").in("pro_id",proIds))
                    .stream()
                    .map(Product::getBrandId)
                    .distinct()
                    .collect(Collectors.toList());
            //根据brandIds查询brand_name
            List<String> brandNames = brandMapper
                    .selectList(new QueryWrapper<Brand>().select("brand_name").in("brand_id",brandIds))
                    .stream()
                    .map(Brand::getBrandName)
                    .distinct()
                    .collect(Collectors.toList());
            filtrateVO.setBrandList(brandNames);
        }
        //查询数据库中size_type
        List<String> sizeType = sizeMapper
                .selectList(new QueryWrapper<Size>().orderByAsc("size_id").select("size_type"))
                .stream()
                .map(Size::getSizeType)
                .distinct()
                .collect(Collectors.toList());
        filtrateVO.setSizeList(sizeType);
        //查询数据库中color_name
        List<String> colorName = colorMapper
                .selectList(new QueryWrapper<Color>().orderByAsc("color_id").select("color_name"))
                .stream()
                .map(Color::getColorName)
                .distinct()
                .collect(Collectors.toList());
        filtrateVO.setColorList(colorName);
        return filtrateVO;
    }
}
