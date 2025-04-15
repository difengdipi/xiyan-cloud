package com.ruoyi.shop.service.impl.order;
import com.ruoyi.shop.domain.order.dto.IncomeTrendItemVO;
import com.ruoyi.shop.domain.order.dto.IncomeTrendVO;
import com.ruoyi.shop.mapper.order.OrderInfoMapper;
import com.ruoyi.shop.service.order.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class IncomeServiceImpl implements IncomeService {
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Override
    public IncomeTrendVO getIncomeTrend(String timeRange, LocalDate startDate, LocalDate endDate) {
        // 确定时间范围和查询条件
        LocalDateTime[] dateRange = calculateDateRange(timeRange, startDate, endDate);
        LocalDateTime beginTime = dateRange[0];
        LocalDateTime endTime = dateRange[1];

        // 确定时间单位和分组方式
        IncomeTrendVO.TimeUnit timeUnit = determineTimeUnit(timeRange, beginTime, endTime);
        String dateFormatPattern = timeUnit == IncomeTrendVO.TimeUnit.DAY ? "%Y-%m-%d" : "%Y-%m";

        // 查询数据库获取已收货订单的收益数据
        List<IncomeTrendItemVO> items = orderInfoMapper.selectIncomeTrend(
                beginTime,
                endTime,
                dateFormatPattern);

        // 计算总收益和总订单数
        BigDecimal totalAmount = items.stream()
                .map(IncomeTrendItemVO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalCount = items.stream()
                .mapToInt(IncomeTrendItemVO::getCount)
                .sum();

        // 构建返回对象
        return IncomeTrendVO.builder()
                .timeUnit(timeUnit)
                .items(items)
                .totalAmount(totalAmount)
                .totalCount(totalCount)
                .build();
    }

    @Override
    public IncomeTrendVO getIncomeRange(String timeRange) {

        if(timeRange == null || timeRange.isEmpty()){
            return null;
        }
        LocalDateTime beginTime = null;
        LocalDateTime endTime = null;
        String dateFormatPattern = null;
        if("week".equals(timeRange) || "month".equals(timeRange)){
            dateFormatPattern  = "%Y-%m-%d";
        }
        if("year".equals(timeRange)){
             dateFormatPattern = "%Y-%m";
        }
        //根据week,month,year确定时间
        if("week".equals(timeRange)){
            beginTime =LocalDateTime.now().minusWeeks(1);
            endTime = LocalDateTime.now();

        }
        if("month".equals(timeRange)){
            beginTime =LocalDateTime.now().minusMonths(4);
            endTime = LocalDateTime.now();
        }
        if("year".equals(timeRange)){
            beginTime = LocalDateTime.now().minusYears(1);
            endTime =  LocalDateTime.now();
        }
        IncomeTrendVO.TimeUnit timeUnit = determineTimeUnit(timeRange, beginTime, endTime);

        // 查询数据库获取已收货订单的收益数据
        List<IncomeTrendItemVO> items = orderInfoMapper.selectIncomeTrend(
                beginTime,
                endTime,
                dateFormatPattern);
        // 计算总收益和总订单数
        BigDecimal totalAmount = items.stream()
                .map(IncomeTrendItemVO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalCount = items.stream()
                .mapToInt(IncomeTrendItemVO::getCount)
                .sum();

        // 构建返回对象
        return IncomeTrendVO.builder()
                .timeUnit(timeUnit)
                .items(items)
                .totalAmount(totalAmount)
                .totalCount(totalCount)
                .build();
    }

    private LocalDateTime[] calculateDateRange(String timeRange, LocalDate startDate, LocalDate endDate) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime beginTime;
        LocalDateTime endTime = now;

        switch (timeRange) {
            case "week":
                beginTime = now.minusWeeks(1);
                break;
            case "month":
                beginTime = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
                break;
            case "year":
                beginTime = now.withDayOfYear(1).withHour(0).withMinute(0).withSecond(0);
                break;
            case "custom":
                beginTime = startDate.atStartOfDay();
                endTime = endDate.atTime(23, 59, 59);
                break;
            default:
                throw new IllegalArgumentException("Invalid time range: " + timeRange);
        }

        return new LocalDateTime[]{beginTime, endTime};
    }

    private IncomeTrendVO.TimeUnit determineTimeUnit(String timeRange, LocalDateTime beginTime, LocalDateTime endTime) {
        // 如果时间范围小于等于31天，按天显示，否则按月显示
        long daysBetween = ChronoUnit.DAYS.between(beginTime.toLocalDate(), endTime.toLocalDate());
        return daysBetween <= 31 ? IncomeTrendVO.TimeUnit.DAY : IncomeTrendVO.TimeUnit.MONTH;
    }
}