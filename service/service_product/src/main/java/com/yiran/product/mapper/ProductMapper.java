package com.yiran.product.mapper;

import com.yiran.model.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Yang Song
 * @since 2022-10-03
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {
     /**
      *根据品牌id查询商品
      * @param BrandId 品牌id
      * @return 商品集合
      */
     List<Product> getByBrandId(String BrandId);

}
