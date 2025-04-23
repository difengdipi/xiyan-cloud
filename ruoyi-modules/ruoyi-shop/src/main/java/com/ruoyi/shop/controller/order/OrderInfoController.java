package com.ruoyi.shop.controller.order;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.shop.domain.order.OrderInfo;
import com.ruoyi.shop.domain.order.dto.IncomeTrendVO;
import com.ruoyi.shop.domain.order.dto.OrderAdminInfoDto;
import com.ruoyi.shop.service.order.IncomeService;
import com.ruoyi.shop.service.order.OrderInfoIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 订单管理Controller
 * 
 * @author zh
 * @date 2025-04-09
 */
@RestController
@RequestMapping("/info")
@Slf4j
@Tag(name = "订单管理")
public class OrderInfoController extends BaseController
{
    @Autowired
    private OrderInfoIService orderInfoService;
    @Autowired
    private RedisService redisService;
    private final String CACHE_PREFIX = "orderInfoList";
    /**
     * 查询订单管理列表
     */
    @RequiresPermissions("shop:info:list")
    @GetMapping("/list")
    @Operation(summary = "查询订单管理列表")
    public TableDataInfo list(OrderInfo orderInfo)
    {
        startPage();
        List<OrderInfo> list = orderInfoService.selectOrderInfoList(orderInfo);
        if(!list.isEmpty()){
            redisService.setCacheList(CACHE_PREFIX,list);
        }
        return getDataTable(list);
    }

    @GetMapping("/all")
    @Operation(summary = "查询订单列表")
    public R listAll(){
        List<OrderAdminInfoDto> list = orderInfoService.listAll();
        return R.ok(list);
    }

    /**
     * 导出订单管理列表
     */
    @RequiresPermissions("shop:info:export")
    @Log(title = "订单管理", businessType = BusinessType.EXPORT)
    @Operation(summary = "导出订单管理列表")
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrderInfo orderInfo)
    {
        ExcelUtil<OrderInfo> util = new ExcelUtil<OrderInfo>(OrderInfo.class);
        List<OrderInfo> list = orderInfoService.selectOrderInfoList(orderInfo);
        util.exportExcel(response, list, "订单管理数据");
    }

    /**
     * 获取订单管理详细信息
     */
    @RequiresPermissions("shop:info:query")
    @GetMapping(value = "/{id}")
    @Operation(summary = "获取订单管理详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(orderInfoService.selectOrderInfoById(id));
    }

    /**
     * 新增订单管理
     */
    @RequiresPermissions("shop:info:add")
    @Log(title = "订单管理", businessType = BusinessType.INSERT)
    @Operation(summary = "新增订单管理")
    @PostMapping
    public AjaxResult add(@RequestBody OrderInfo orderInfo)
    {
        CompletableFuture.runAsync(() -> {
            redisService.deleteObject(CACHE_PREFIX);
        });
        return toAjax(orderInfoService.insertOrderInfo(orderInfo));
    }

    /**
     * 修改订单管理
     */
    @RequiresPermissions("shop:info:edit")
    @Log(title = "订单管理", businessType = BusinessType.UPDATE)
    @Operation(summary = "修改订单管理")
    @PutMapping
    public AjaxResult edit(@RequestBody OrderInfo orderInfo)
    {
        CompletableFuture.runAsync(() -> {
            redisService.deleteObject(CACHE_PREFIX);
        });
        return toAjax(orderInfoService.updateOrderInfo(orderInfo));
    }

    /**
     * 删除订单管理
     */
    @RequiresPermissions("shop:info:remove")
    @Log(title = "订单管理", businessType = BusinessType.DELETE)
    @Operation(summary = "删除订单管理")
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        CompletableFuture.runAsync(() -> {
            redisService.deleteObject(CACHE_PREFIX);
        });
        return toAjax(orderInfoService.deleteOrderInfoByIds(ids));
    }


    @RequiresPermissions("shop:info:import")
    @Log(title = "导出模版", businessType = BusinessType.EXPORT)
    @Operation(summary = "导出模版")
    @PostMapping("/template")
    public void template(HttpServletResponse response){
        List<OrderInfo> list = new ArrayList<>();
        list.add(new OrderInfo());
        ExcelUtil<OrderInfo> util = new ExcelUtil<OrderInfo>(OrderInfo.class);
        util.exportExcel(response, list, "订单信息模版");
    }

    /**
     * 导入订单信息包含更新
     * @param file
     * @return
     */
    @SneakyThrows
    @PostMapping("/import")
    @RequiresPermissions("shop:info:import")
    @Log(title = "医生行程", businessType = BusinessType.IMPORT)
    @Operation(summary = "导入订单信息")
    public AjaxResult importExcel(@RequestPart("file") MultipartFile file){
        ExcelUtil<OrderInfo> ExcelUtil = new ExcelUtil<>(OrderInfo.class);
        List<OrderInfo> collect = ExcelUtil.importExcel(file.getInputStream());
        log.info("orderInfoList:{}",collect);
        //批量导入数据
        CompletableFuture.runAsync(() -> {
            redisService.deleteObject(CACHE_PREFIX);
        });
        for (OrderInfo orderInfo : collect) {
            LambdaUpdateWrapper<OrderInfo> orderInfoLambdaUpdateWrapper =
                    new LambdaUpdateWrapper<OrderInfo>()
                            .eq(OrderInfo::getOrderId,orderInfo.getOrderId())
                            .set(OrderInfo::getOrderState,orderInfo.getOrderState())
                            .set(OrderInfo::getUpdateTime,orderInfo.getUpdateTime());
            if(orderInfo.getOrderState() == 6){
                orderInfoLambdaUpdateWrapper
                        .set(OrderInfo::getCancelReason,orderInfo.getCancelReason());
            }
            if(orderInfo.getOrderState() == 3){
                orderInfoLambdaUpdateWrapper.set(OrderInfo::getTrackingNumber,orderInfo.getTrackingNumber());
            }
            orderInfoService.update(orderInfoLambdaUpdateWrapper);
        }
        return  success();
    }


    @GetMapping("/income")
    @Operation(summary = "收益曲线")
    public R<IncomeTrendVO> getScheduleData(   @RequestParam @NotBlank(message = "timeRange is required")
                                @Pattern(regexp = "week|month|year|custom", message = "Invalid timeRange. Allowed values: week, month, year, custom.")
                                String timeRange,

                                @RequestParam(required = false)
                                @DateTimeFormat(pattern = "yyyy-MM-dd")
                                LocalDate startDate,

                                @RequestParam(required = false)
                                @DateTimeFormat(pattern = "yyyy-MM-dd")
                                LocalDate endDate){
        if(!"custom".equals(timeRange)){
           return R.ok (incomeService.getIncomeRange(timeRange));
        }
        if(startDate == null || endDate == null){
            return R.fail("请选择时间范围");
        }
        IncomeTrendVO incomeTrend = incomeService.getIncomeTrend(timeRange, startDate, endDate);
        return R.ok(incomeTrend);
    }
    @Autowired
    IncomeService incomeService;

}
