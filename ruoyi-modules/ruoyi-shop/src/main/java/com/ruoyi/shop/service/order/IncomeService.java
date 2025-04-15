package com.ruoyi.shop.service.order;

import com.ruoyi.shop.domain.order.dto.IncomeTrendVO;

import java.time.LocalDate;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/4/15
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
public interface IncomeService {
    IncomeTrendVO getIncomeTrend(String timeRange, LocalDate startDate, LocalDate endDate);

    IncomeTrendVO getIncomeRange(String timeRange);

}
