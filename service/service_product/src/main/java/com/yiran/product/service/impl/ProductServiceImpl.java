package com.yiran.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yiran.model.entity.*;
import com.yiran.model.vo.ProductDetailVO;
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
    private final ProAttributeInfoMapper proAttributeInfoMapper;
    private final ColorMapper colorMapper;
    private final SizeMapper sizeMapper;
    private final ReceiveAddressMapper addressMapper;
    private final ProImageMapper proImageMapper;
    private final BrandMapper brandMapper;


    public ProductServiceImpl(ProductMapper productMapper, BrandMapper brandMapper,ProImageMapper proImageMapper,ReceiveAddressMapper addressMapper,ColorMapper colorMapper,SizeMapper sizeMapper,ProAttributeInfoMapper proAttributeInfoMapper) {
        this.productMapper = productMapper;
        this.proAttributeInfoMapper = proAttributeInfoMapper;
        this.colorMapper = colorMapper;
        this.sizeMapper = sizeMapper;
        this.addressMapper = addressMapper;
        this.proImageMapper = proImageMapper;
        this.brandMapper = brandMapper;
    }

    @Override
    public List<ProductVO> getByBrandId(String brandId) {
        List<ProductVO> productVos = new ArrayList<>();
        //根据品牌id查询商品
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("brand_id", brandId);
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
    public List<ProductVO> getByKindId(String kindId) {
        List<ProductVO> productVos = new ArrayList<>();
        //根据品类id查询商品集合
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("kind", kindId);
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
        List<String> colorList = null;
        List<String> sizeList = null;
        List<String> kindList = null;

        if(colorIdArr.length>0){
            colorList = Arrays.asList(colorIdArr);
        }
        if (kindIdArr.length>0){
            kindList = Arrays.asList(kindIdArr);
        }
        if(sizeIdArr.length > 0){
            sizeList = Arrays.asList(kindIdArr);
        }

        //查询颜色和尺码在sizeIdStr,colorIdStr里面的 的pro_ids
        List<String> proIds = proAttributeInfoMapper
                .selectList(new QueryWrapper<ProAttributeInfo>().select("pro_id").in(!CollectionUtils.isEmpty(sizeList),"size_id",sizeList).in(!CollectionUtils.isEmpty(colorList),"color_id",colorList))
                .stream()
                .map(ProAttributeInfo::getProId)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(proIds);
        //查询pro_id在pro_ids里，并且品牌和品类为brandId，kindIdStr的商品
        List<Product> products = productMapper
                .selectList(new QueryWrapper<Product>().in(!CollectionUtils.isEmpty(proIds),"pro_id",proIds).in(!CollectionUtils.isEmpty(kindList),"kind","kindList").eq("brand_id",brandId));

        List<ProductVO> productVOList = products.stream()
                .map(p -> {
                    ProductVO vo = new ProductVO();
                    BeanUtils.copyProperties(p, vo);
                    return vo;
                })
                .collect(Collectors.toList());

        productVOList.forEach(System.out::println);
        return productVOList;
    }

    @Override
    public ProductDetailVO getByProId(String proId,String userId) {
        ProductDetailVO productDetailVO = new ProductDetailVO();
        //根据商品id查商品
        Product product = productMapper.selectById(proId);
        //根据品牌id查找品牌名；
        QueryWrapper<Brand> brandWrapper = new QueryWrapper<>();
        brandWrapper.select("brand_name").eq("brand_id",product.getBrandId());
        Brand brand = brandMapper.selectOne(brandWrapper);
        BeanUtils.copyProperties(product,productDetailVO);
        productDetailVO.setBrandName(brand.getBrandName());
        //根据商品id 查商品属性 颜色，尺码
        QueryWrapper<ProAttributeInfo> wrapper = new QueryWrapper<>();
        wrapper.select("size_id","color_id").eq("pro_id",proId);
        List<ProAttributeInfo> proAttributeInfos = proAttributeInfoMapper.selectList(wrapper);
        List<String> sizeTypeList = new ArrayList<>();
        List<String> colorNameList = new ArrayList<>();
        for(ProAttributeInfo proAttributeInfo : proAttributeInfos){
            Size size = sizeMapper.selectById(proAttributeInfo.getSizeId());
            sizeTypeList.add(size.getSizeType());

            Color color = colorMapper.selectById(proAttributeInfo.getColorId());
            colorNameList.add(color.getColorName());
        }
        productDetailVO.setColorNameList(colorNameList);
        productDetailVO.setSizeTypeList(sizeTypeList);

        //根据用户id查询收件地址
        QueryWrapper<ReceiveAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("province","city","area","stree","detail").eq("user_id",userId);
        List<ReceiveAddress> addressList = addressMapper.selectList(queryWrapper);
        productDetailVO.setAddressesList(addressList);
        //根据商品id查询商品图片缩略图列表
        QueryWrapper<ProImage> queryWrapper1 = new QueryWrapper<>();
        queryWrapper.eq("pro_id",proId);
        List<ProImage> proImageList = proImageMapper.selectList(queryWrapper1);
        productDetailVO.setProImageList(proImageList);
        return productDetailVO;
    }
}

