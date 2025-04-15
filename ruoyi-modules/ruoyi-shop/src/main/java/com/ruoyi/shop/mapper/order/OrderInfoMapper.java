package com.ruoyi.shop.mapper.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.shop.domain.order.OrderInfo;
import com.ruoyi.shop.domain.order.dto.IncomeTrendItemVO;
import com.ruoyi.shop.domain.order.dto.OrderAdminInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/4/2
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

    /**
     * 查询订单管理
     *
     * @param id 订单管理主键
     * @return 订单管理
     */
    public OrderInfo selectOrderInfoById(Long id);

    /**
     * 查询订单管理列表
     *
     * @param orderInfo 订单管理
     * @return 订单管理集合
     */
    public List<OrderInfo> selectOrderInfoList(OrderInfo orderInfo);

    /**
     * 新增订单管理
     *
     * @param orderInfo 订单管理
     * @return 结果
     */
    public int insertOrderInfo(OrderInfo orderInfo);

    /**
     * 修改订单管理
     *
     * @param orderInfo 订单管理
     * @return 结果
     */
    public int updateOrderInfo(OrderInfo orderInfo);

    /**
     * 删除订单管理
     *
     * @param id 订单管理主键
     * @return 结果
     */
    public int deleteOrderInfoById(Long id);

    /**
     * 批量删除订单管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderInfoByIds(Long[] ids);

    List<OrderAdminInfoDto> listAll();
    @Select("SELECT " +
            "DATE_FORMAT(create_time, #{dateFormat}) AS date, " +
            "SUM(pay_money) AS amount, " +
            "COUNT(*) AS count " +
            "FROM sys_order_info " +
            "WHERE order_state in (4,5) " +  // 只查询已收货的订单
            "AND create_time BETWEEN #{beginTime} AND #{endTime} " +
            "GROUP BY date " +
            "ORDER BY date ASC")
    List<IncomeTrendItemVO> selectIncomeTrend(@Param("beginTime")LocalDateTime beginTime, @Param("endTime") LocalDateTime endTime,@Param("dateFormat") String dateFormatPattern);
}
