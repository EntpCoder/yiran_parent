package com.yiran.order.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yiran.model.entity.OrderDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiran.model.entity.Orders;
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
public interface OrderDetailsMapper extends BaseMapper<OrderDetails> {
}
