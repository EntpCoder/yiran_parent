package com.yiran.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yiran.model.entity.*;
import com.yiran.model.vo.FiltrateVO;
import com.yiran.model.vo.ProductDetailVO;
import com.yiran.model.vo.ProductInfoNumVO;
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
    private final InventoryMapper inventoryMapper;


    public ProductServiceImpl(ProductMapper productMapper,BrandMapper brandMapper, InventoryMapper inventoryMapper,ProImageMapper proImageMapper,ColorMapper colorMapper, SizeMapper sizeMapper, ProAttributeInfoMapper proAttributeInfoMapper, MultiMenuMapper multiMenuMapper) {
        this.productMapper = productMapper;
        this.proAttributeInfoMapper = proAttributeInfoMapper;
        this.colorMapper = colorMapper;
        this.sizeMapper = sizeMapper;
        this.proImageMapper = proImageMapper;
        this.brandMapper = brandMapper;
        this.multiMenuMapper = multiMenuMapper;
        this.inventoryMapper = inventoryMapper;
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
    public List<ProductVO> getByBrandKindSizeColor(String[] brandIdArr, String[] kindIdArr, String[] sizeIdArr, String[] colorIdArr) {
        List<String> colorList = null;
        List<String> sizeList = null;
        List<String> kindList = null;
        List<String> brandList = null;

        if(colorIdArr.length>0){
            colorList = Arrays.asList(colorIdArr);
        }
        if (kindIdArr.length>0){
            kindList = Arrays.asList(kindIdArr);
        }
        if(sizeIdArr.length > 0){
            sizeList = Arrays.asList(sizeIdArr);
        }
        if(brandIdArr.length > 0){
            brandList = Arrays.asList(brandIdArr);
        }
        List<ProductVO> productVOList = new ArrayList<>();
        //查询颜色和尺码在sizeIdStr,colorIdStr里面的 的pro_ids
        List<String> proIds = proAttributeInfoMapper
                .selectList(new QueryWrapper<ProAttributeInfo>().select("pro_id").in(!CollectionUtils.isEmpty(sizeList),"size_id",sizeList).in(!CollectionUtils.isEmpty(colorList),"color_id",colorList))
                .stream()
                .map(ProAttributeInfo::getProId)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(proIds);
        if (proIds.size()>0){
        //查询pro_id在pro_ids里，并且品牌和品类为brandId，kindIdStr的商品
        List<Product> products = productMapper
                .selectList(new QueryWrapper<Product>().in(!CollectionUtils.isEmpty(proIds),"pro_id",proIds).in(!CollectionUtils.isEmpty(kindList),"kind",kindList).in(!CollectionUtils.isEmpty(brandList),"brand_id",brandList));

            productVOList = products.stream()
                    .map(p -> {
                        ProductVO vo = new ProductVO();
                        BeanUtils.copyProperties(p, vo);
                        vo.setProCount(products.size());
                        return vo;
                    })
                    .collect(Collectors.toList());
        }
        return productVOList;
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
        Set<Size> sizeSet = new HashSet<>();
        Set<Color> colorSet = new HashSet<>();
        for(ProAttributeInfo proAttributeInfo : proAttributeInfos){
            Size size = sizeMapper.selectById(proAttributeInfo.getSizeId());
            sizeSet.add(size);
            Color color = colorMapper.selectById(proAttributeInfo.getColorId());
            colorSet.add(color);
        }
        List<Size> sizeList = new ArrayList<>(sizeSet);
        List<Color> colorList = new ArrayList<>(colorSet);
        productDetailVO.setSizeList(sizeList);
        productDetailVO.setColorList(colorList);


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
            List<Brand> brandList = new ArrayList<>();
            //根据品牌id查找品牌名
            Brand brand = brandMapper.selectById(brandId);
            brandList.add(brand);
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
            List<String> list = new ArrayList<>(kindIds);
            //根据商品kindId查询kinds
            List<String> kinds = multiMenuMapper
                    .selectList(new QueryWrapper<MultiMenu>().select("title").in("menu_id", kindIds))
                    .stream()
                    .map(MultiMenu::getTitle)
                    .collect(Collectors.toList());
            List<MultiMenu> kindList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++){
                MultiMenu menu = new MultiMenu();
                menu.setMenuId(list.get(i));
                menu.setTitle(kinds.get(i));
                kindList.add(menu);
            }
            filtrateVO.setKindList(kindList);
        }
        //只传品类时
        if (brandId == null){
            //根据品类id查询品类名
            MultiMenu menu = multiMenuMapper.selectById(kindId);
            List<MultiMenu> kindList = new ArrayList<>();
            kindList.add(menu);
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

            List<Brand> brandList = new ArrayList<>();
            for (int i = 0; i < brandIds.size(); i++){
                Brand brand = new Brand();
                brand.setBrandId(brandIds.get(i));
                brand.setBrandName(brandNames.get(i));
                brandList.add(brand);
            }
            filtrateVO.setBrandList(brandList);
        }
        //查询数据库中size_type
        List<Size> sizeList = sizeMapper
                .selectList(new QueryWrapper<>())
                .stream()
                .distinct()
                .collect(Collectors.toList());
        filtrateVO.setSizeList(sizeList);
        //查询数据库中color_name
        List<Color> colorList = colorMapper
                .selectList(new QueryWrapper<>())
                .stream()
                .distinct()
                .collect(Collectors.toList());
        filtrateVO.setColorList(colorList);
        return filtrateVO;
    }

    @Override
    public ProductInfoNumVO getProInfoId(String proId, String sizeId, String colorId) {
        ProductInfoNumVO productInfoNumVO = new ProductInfoNumVO();

        QueryWrapper<ProAttributeInfo> wrapper = new QueryWrapper<>();
        wrapper.select("pro_attribute_info_id").eq("pro_id",proId).eq("size_id",sizeId).eq("color_id",colorId);
        ProAttributeInfo proAttributeInfo = proAttributeInfoMapper.selectOne(wrapper);
        //获取商品属性id
        String proAttributeInfoId = proAttributeInfo.getProAttributeInfoId();
        QueryWrapper<Inventory> wrapper1 = new QueryWrapper<>();
        wrapper1.select("nums").eq("pro_attribute_info_id",proAttributeInfoId);
        Inventory inventory = inventoryMapper.selectOne(wrapper1);
        //获取商品属性的库存
        Integer nums = inventory.getNums();
        productInfoNumVO.setNums(nums);
        productInfoNumVO.setProAttributeInfoId(proAttributeInfoId);
        return productInfoNumVO;
    }
    /**
     * ES根据传入的商品名字或商品描述搜索商品
     * @return 商品集合
     */
    @Override
    public List<ProductVO> getAllProductVo() {
        List<Product> list = productMapper.selectList(null);
        List<ProductVO> productVos = new ArrayList<>();
        for (Product p :
                list) {
            ProductVO productVO = new ProductVO();
            BeanUtils.copyProperties(p,productVO);
            productVos.add(productVO);
        }
        return productVos;
    }
}

