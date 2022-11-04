package com.yiran.product.mapper;

import com.yiran.model.entity.Collections;
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
public interface CollectionsMapper extends BaseMapper<Collections> {
    /**
     * 删除收藏
     * @param userId 用户id
     * @param proId 商品id
     * @return boolean
     */
    Boolean deleteByUserByPro(String userId,String proId);
    /**
     * 根据用户商品id查询收藏
     * @param proId 用户id
     * @param userId 商品id
     * @return 查询收藏
     */
    Collections selectCollection(String proId,String userId);
}
