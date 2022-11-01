package com.yiran.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yiran.model.entity.*;
import com.yiran.model.vo.FiltrateVO;
import com.yiran.model.vo.ProductDetailVO;
import com.yiran.model.vo.ProductVO;
import com.yiran.product.mapper.*;
import com.yiran.product.service.IProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
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
    private final ProImageMapper proImageMapper;
    private final BrandMapper brandMapper;
    private final MultiMenuMapper multiMenuMapper;


    public ProductServiceImpl(ProductMapper productMapper,BrandMapper brandMapper, ProImageMapper proImageMapper,ColorMapper colorMapper, SizeMapper sizeMapper, ProAttributeInfoMapper proAttributeInfoMapper, MultiMenuMapper multiMenuMapper) {
        this.productMapper = productMapper;
        this.proAttributeInfoMapper = proAttributeInfoMapper;
        this.colorMapper = colorMapper;
        this.sizeMapper = sizeMapper;
        this.proImageMapper = proImageMapper;
        this.brandMapper = brandMapper;
        this.multiMenuMapper = multiMenuMapper;
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
            productVO.setProCount(products.size());
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
            productVO.setProCount(products.size());
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
            sizeList = Arrays.asList(sizeIdArr);
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
                .selectList(new QueryWrapper<Product>().in(!CollectionUtils.isEmpty(proIds),"pro_id",proIds).in(!CollectionUtils.isEmpty(kindList),"kind",kindList).eq("brand_id",brandId));

        return products.stream()
                .map(p -> {
                    ProductVO vo = new ProductVO();
                    BeanUtils.copyProperties(p, vo);
                    vo.setProCount(products.size());
                    return vo;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ProductDetailVO getByProId(String proId) {

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
        Set<String> sizeTypeList = new HashSet<>();
        Set<String> colorNameList = new HashSet<>();
        for(ProAttributeInfo proAttributeInfo : proAttributeInfos){
            Size size = sizeMapper.selectById(proAttributeInfo.getSizeId());
            sizeTypeList.add(size.getSizeType());

            Color color = colorMapper.selectById(proAttributeInfo.getColorId());
            colorNameList.add(color.getColorName());
        }
        productDetailVO.setColorNameList(colorNameList);
        productDetailVO.setSizeTypeList(sizeTypeList);


        //根据商品id查询商品图片缩略图列表
        List<String> proImageList = proImageMapper
                .selectList(new QueryWrapper<ProImage>().select("image").eq("pro_id", proId))
                .stream()
                .map(ProImage::getImage)
                .collect(Collectors.toList());
        productDetailVO.setProImageList(proImageList);
        return productDetailVO;
    }

    @Override
    public FiltrateVO getFiltrate(String brandId, String kindId) {
        FiltrateVO filtrateVO = new FiltrateVO();
        //只传品牌时
        if (kindId == null) {
            Map<String,String> brandMap = new HashMap<>(16);
            //根据品牌id查找品牌名
            Brand brand = brandMapper.selectById(brandId);
            brandMap.put(brandId,brand.getBrandName());
            filtrateVO.setBrandMap(brandMap);
            //根据品牌id查询商品
            QueryWrapper<Product> brandWrapper = new QueryWrapper<>();
            brandWrapper.eq("brand_id", brandId);
            List<Product> products = productMapper.selectList(brandWrapper);
            //商品kindIds
            Set<String> kindIds = new HashSet<>();
            for (Product p : products) {
                kindIds.add(p.getKind());
            }
            List<String> list = new ArrayList<>(kindIds);
            //根据商品kindId查询kinds
            List<String> kinds = multiMenuMapper
                    .selectList(new QueryWrapper<MultiMenu>().select("title").in("menu_id", kindIds))
                    .stream()
                    .map(MultiMenu::getTitle)
                    .collect(Collectors.toList());
            Map<String,String> kindMap = list.stream().collect(Collectors.toMap(key -> key, key -> kinds.get(list.indexOf(key))));
            filtrateVO.setKindMap(kindMap);
        }
        //只传品类时
        if (brandId == null){
            Map<String,String> kindMap = new HashMap<>(16);
            //根据品类id查询品类名
            MultiMenu menu = multiMenuMapper.selectById(kindId);
            kindMap.put(kindId,menu.getTitle());
            filtrateVO.setKindMap(kindMap);
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

            Map<String,String> brandMap = brandIds.stream().collect(Collectors.toMap(key -> key, key -> brandNames.get(brandIds.indexOf(key))));
            filtrateVO.setBrandMap(brandMap);
        }
        //查询数据库中size_type
        List<String> sizeIds = sizeMapper
                .selectList(new QueryWrapper<Size>().orderByAsc("size_id").select("size_id"))
                .stream()
                .map(Size::getSizeId)
                .distinct()
                .collect(Collectors.toList());
        List<String> sizeType = sizeMapper
                .selectList(new QueryWrapper<Size>().orderByAsc("size_id").select("size_type"))
                .stream()
                .map(Size::getSizeType)
                .distinct()
                .collect(Collectors.toList());
        Map<String,String> sizeMap = sizeIds.stream().collect(Collectors.toMap(key -> key, key -> sizeType.get(sizeIds.indexOf(key))));
        filtrateVO.setSizeMap(sizeMap);
        //查询数据库中color_name
        List<String> colorIds = colorMapper
                .selectList(new QueryWrapper<Color>().orderByAsc("color_id").select("color_id"))
                .stream()
                .map(Color::getColorId)
                .distinct()
                .collect(Collectors.toList());
        List<String> colorName = colorMapper
                .selectList(new QueryWrapper<Color>().orderByAsc("color_id").select("color_name"))
                .stream()
                .map(Color::getColorName)
                .distinct()
                .collect(Collectors.toList());
        Map<String,String> colorMap = colorIds.stream().collect(Collectors.toMap(key -> key, key -> colorName.get(colorIds.indexOf(key))));
        filtrateVO.setColorMap(colorMap);
        return filtrateVO;
    }
}

