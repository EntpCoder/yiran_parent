package com.yiran.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiran.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Yang Song
 * @since 2022-10-03
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
