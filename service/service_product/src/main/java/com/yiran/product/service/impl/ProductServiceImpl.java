package com.yiran.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yiran.model.entity.*;
import com.yiran.model.vo.ProductVO;
import com.yiran.product.mapper.*;
import com.yiran.product.service.IProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Yang Song
 * @since 2022-10-03
 */
@Service
public class ProductServiceImpl implements IProductService {
    private final ProductMapper productMapper;
    private final BrandMapper brandMapper;
    private final ProAttributeInfoMapper proAttributeInfoMapper;


    public ProductServiceImpl(ProductMapper productMapper, BrandMapper brandMapper, ProAttributeInfoMapper proAttributeInfoMapper) {
        this.productMapper = productMapper;
        this.brandMapper = brandMapper;
        this.proAttributeInfoMapper = proAttributeInfoMapper;
    }

    @Override
    public List<ProductVO> getByBrandId(String brandId) {
        //根据brand_name查询brand_id
        QueryWrapper<Brand> brandQueryWrapper = new QueryWrapper<>();
        brandQueryWrapper.eq("brand_name", brandId);
        Brand brand = brandMapper.selectOne(brandQueryWrapper);
        List<ProductVO> productVos = new ArrayList<>();
        //根据品牌id查询商品
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("brand_id", brand.getBrandId());
        List<Product> products = productMapper.selectList(queryWrapper);
        //商品的信息
        for (Product p : products) {
            ProductVO productVO = new ProductVO();
            BeanUtils.copyProperties(p, productVO);
            productVos.add(productVO);
        }
        return productVos;
    }

    @Override
    public List<ProductVO> getByBrandKindSizeColor(String brandId, String[] kindIdArr, String[] sizeIdArr, String[] colorIdArr) {
        List<String> colorList = Arrays.asList(colorIdArr);
        System.out.println("================="+colorList.size());
        List<String> kindList = Arrays.asList(kindIdArr);

        List<String> sizeList = Arrays.asList(sizeIdArr);



        //数组转集合
//        List<String> sizeList = new ArrayList<>();
//        System.out.println(sizeIdArr.length+"========================");
//        if(sizeIdArr.length>0){
//            Collections.addAll(sizeList, sizeIdArr);
//        }
//        else{
//            sizeList = new ArrayList<>();
//        }
//        List<String> colorList = new ArrayList<>();
//        Collections.addAll(colorList, colorIdArr);
//        List<String> kindList = new ArrayList<>();
//        Collections.addAll(kindList, kindIdArr);

        //查询颜色和尺码在sizeIdStr,colorIdStr里面的 的pro_ids
        List<String> proIds = proAttributeInfoMapper
                .selectList(new QueryWrapper<ProAttributeInfo>().select("pro_id").in(!CollectionUtils.isEmpty(sizeList),"size_id",sizeList).in(!CollectionUtils.isEmpty(colorList),"color_id",colorList))
                .stream()
                .map(ProAttributeInfo::getProId)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(proIds);
        //查询pro_id在pro_ids里，并且品牌和品类为brandId，kindIdStr的pro_id
        List<Product> products = productMapper
                .selectList(new QueryWrapper<Product>().select("pro_id").in(!CollectionUtils.isEmpty(proIds),"pro_id",proIds).in(!CollectionUtils.isEmpty(kindList),"kind","kindList").eq("brandId",brandId));

        List<ProductVO> productVOList = products.stream()
                .map(p -> {
                    ProductVO vo = new ProductVO();
                    BeanUtils.copyProperties(p, vo);
                    return vo;
                })
                .collect(Collectors.toList());

        productVOList.forEach(System.out::println);
        return productVOList;
//        return null;
    }


}

