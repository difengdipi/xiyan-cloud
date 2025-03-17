package com.ruoyi.dental.mapper;

import com.ruoyi.dental.domain.FollowUpTasks;

import java.util.List;

/**
 * 跟进任务Mapper接口
 * 
 * @author zh
 * @date 2025-03-15
 */
public interface FollowUpTasksMapper 
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
     * 删除跟进任务
     * 
     * @param taskId 跟进任务主键
     * @return 结果
     */
    public int deleteFollowUpTasksByTaskId(Long taskId);

    /**
     * 批量删除跟进任务
     * 
     * @param taskIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFollowUpTasksByTaskIds(Long[] taskIds);
}
