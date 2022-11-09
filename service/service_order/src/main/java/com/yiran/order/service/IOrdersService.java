package com.yiran.order.service;

import com.yiran.model.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yiran.model.vo.OrdersVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Yang Song
 * @since 2022-10-03
 */
public interface IOrdersService extends IService<Orders> {
    /**
     * 生成订单id 需要参数
     * @param userId 用户id
     * @param receiveId 地址id
     * @param receiveCouponId 优惠券id
     * @param cartIds 购物车集合
     * @return 订单
     */
    String createOrder(String userId, String receiveId,String receiveCouponId, String[] cartIds);

    /**
     * 根据订单id查询订单
     * @param orderId 订单
     * @return 订单
     */
    Orders queryOrder(String orderId);

    /**
     * 根据订单id查询订单详情
     * @param orderId 订单id
     * @return 订单详情
     */
    Orders getOrderAndDetails(String orderId);
    /**
     * 根据用户id来查询用户的全部订单
     * @param usersId 用户id
     * @return 订单集合
     */
    List<OrdersVO> getAllOrders(String usersId);
    /**
     * 根据用户id以及状态编码来查询用户状态订单
     * @param usersId 用户id
     * @param orderState 状态编码-订单状态：0未支付 1已支付 2已取消  3待收货 4已完成
     * @return 状态集合
     */
    List<OrdersVO> getOrdersByStatus(String usersId, Byte orderState);
    /**
     * 根据订单id来查询一条订单的详情
     * @param orderId 订单id
     * @return 一条订单的集合
     */
    OrdersVO getOrderDetailsByOrderId(String orderId);

    /**
     *根据订单id改收获状态
     * @param orderId 订单id
     * @return Boolean
     */
    Boolean updateOrderStatusByOrderId(String orderId);

    /**
     * 封装复用方法
     * @param orders 输入orders类型的参数
     * @return 返回一个VO集合
     */
    List<OrdersVO> getPackagingList(List<Orders> orders);

    /**
     * 测试接口，用于用户支付订单后 修改状态为已发货(待收货)
     * @param orderNum 订单编号(不是订单id！！！)
     * @return 是否成功
     */
    Boolean updateOrderStatusByOrderNumWith3(String orderNum);
}
