package com.yiran.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yiran.model.entity.MultiMenu;
import com.yiran.model.entity.Product;
import com.yiran.model.vo.FiltrateVO;
import com.yiran.product.mapper.MultiMenuMapper;
import com.yiran.product.mapper.ProductMapper;
import com.yiran.product.service.IFiltrateService;
import org.springframework.stereotype.Service;
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

    public FiltrateServiceImpl(ProductMapper productMapper, MultiMenuMapper multiMenuMapper) {
        this.productMapper = productMapper;
        this.multiMenuMapper = multiMenuMapper;
    }

    @Override
    public FiltrateVO getFiltrateByBrandId(String brandId) {
        FiltrateVO filtrateVO = new FiltrateVO();
        Set<String> kindIds = new HashSet<>();
        //根据品牌id查询商品
        QueryWrapper<Product> brandWrapper = new QueryWrapper<>();
        brandWrapper.eq("brand_id", brandId);
        List<Product> products = productMapper.selectList(brandWrapper);
        //商品kindId
        for (Product p : products){
            kindIds.add(p.getKind());
        }
        //根据商品kindId查询title
        List<String> kinds = multiMenuMapper
                .selectList(new QueryWrapper<MultiMenu>().select("title").in("menu_id",kindIds))
                .stream()
                .map(MultiMenu::getTitle)
                .distinct()
                .collect(Collectors.toList());
        filtrateVO.setKindList(kinds);
        return filtrateVO;
    }
}
