package com.ruoyi.dental.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.dental.domain.FollowUpTasks;
import com.ruoyi.dental.service.IFollowUpTasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 跟进任务Controller
 * 
 * @author zh
 * @date 2025-03-15
 */
@RestController
@RequestMapping("/tasks")
public class FollowUpTasksController extends BaseController
{
    @Autowired
    private IFollowUpTasksService followUpTasksService;

    /**
     * 查询跟进任务列表
     */
    @RequiresPermissions("system:tasks:list")
    @GetMapping("/list")
    public TableDataInfo list(FollowUpTasks followUpTasks)
    {
        startPage();
        List<FollowUpTasks> list = followUpTasksService.selectFollowUpTasksList(followUpTasks);
        return getDataTable(list);
    }

    /**
     * 导出跟进任务列表
     */
    @RequiresPermissions("system:tasks:export")
    @Log(title = "跟进任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FollowUpTasks followUpTasks)
    {
        List<FollowUpTasks> list = followUpTasksService.selectFollowUpTasksList(followUpTasks);
        ExcelUtil<FollowUpTasks> util = new ExcelUtil<FollowUpTasks>(FollowUpTasks.class);
        util.exportExcel(response, list, "跟进任务数据");
    }

    /**
     * 获取跟进任务详细信息
     */
    @RequiresPermissions("system:tasks:query")
    @GetMapping(value = "/{taskId}")
    public AjaxResult getInfo(@PathVariable("taskId") Long taskId)
    {
        return success(followUpTasksService.selectFollowUpTasksByTaskId(taskId));
    }

    /**
     * 新增跟进任务
     */
    @RequiresPermissions("system:tasks:add")
    @Log(title = "跟进任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FollowUpTasks followUpTasks)
    {
        return toAjax(followUpTasksService.insertFollowUpTasks(followUpTasks));
    }

    /**
     * 修改跟进任务
     */
    @RequiresPermissions("system:tasks:edit")
    @Log(title = "跟进任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FollowUpTasks followUpTasks)
    {
        return toAjax(followUpTasksService.updateFollowUpTasks(followUpTasks));
    }

    /**
     * 删除跟进任务
     */
    @RequiresPermissions("system:tasks:remove")
    @Log(title = "跟进任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{taskIds}")
    public AjaxResult remove(@PathVariable Long[] taskIds)
    {
        return toAjax(followUpTasksService.deleteFollowUpTasksByTaskIds(taskIds));
    }
}
