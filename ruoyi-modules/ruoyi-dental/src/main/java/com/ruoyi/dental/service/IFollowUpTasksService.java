package com.ruoyi.dental.service;

import com.ruoyi.dental.domain.FollowUpTasks;

import java.util.List;

/**
 * 跟进任务Service接口
 * 
 * @author zh
 * @date 2025-03-15
 */
public interface IFollowUpTasksService 
{
    /**
     * 查询跟进任务
     * 
     * @param taskId 跟进任务主键
     * @return 跟进任务
     */
    public FollowUpTasks selectFollowUpTasksByTaskId(Long taskId);

    /**
     * 查询跟进任务列表
     * 
     * @param followUpTasks 跟进任务
     * @return 跟进任务集合
     */
    public List<FollowUpTasks> selectFollowUpTasksList(FollowUpTasks followUpTasks);

    /**
     * 新增跟进任务
     * 
     * @param followUpTasks 跟进任务
     * @return 结果
     */
    public int insertFollowUpTasks(FollowUpTasks followUpTasks);

    /**
     * 修改跟进任务
     * 
     * @param followUpTasks 跟进任务
     * @return 结果
     */
    public int updateFollowUpTasks(FollowUpTasks followUpTasks);

    /**
     * 批量删除跟进任务
     * 
     * @param taskIds 需要删除的跟进任务主键集合
     * @return 结果
     */
    public int deleteFollowUpTasksByTaskIds(Long[] taskIds);

    /**
     * 删除跟进任务信息
     * 
     * @param taskId 跟进任务主键
     * @return 结果
     */
    public int deleteFollowUpTasksByTaskId(Long taskId);
}
