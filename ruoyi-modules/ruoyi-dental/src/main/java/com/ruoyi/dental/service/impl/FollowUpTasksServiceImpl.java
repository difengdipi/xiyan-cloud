package com.ruoyi.dental.service.impl;

import com.ruoyi.dental.domain.FollowUpTasks;
import com.ruoyi.dental.mapper.FollowUpTasksMapper;
import com.ruoyi.dental.service.IFollowUpTasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 跟进任务Service业务层处理
 * 
 * @author zh
 * @date 2025-03-15
 */
@Service
public class FollowUpTasksServiceImpl implements IFollowUpTasksService 
{
    @Autowired
    private FollowUpTasksMapper followUpTasksMapper;

    /**
     * 查询跟进任务
     * 
     * @param taskId 跟进任务主键
     * @return 跟进任务
     */
    @Override
    public FollowUpTasks selectFollowUpTasksByTaskId(Long taskId)
    {
        return followUpTasksMapper.selectFollowUpTasksByTaskId(taskId);
    }

    /**
     * 查询跟进任务列表
     * 
     * @param followUpTasks 跟进任务
     * @return 跟进任务
     */
    @Override
    public List<FollowUpTasks> selectFollowUpTasksList(FollowUpTasks followUpTasks)
    {
        return followUpTasksMapper.selectFollowUpTasksList(followUpTasks);
    }

    /**
     * 新增跟进任务
     * 
     * @param followUpTasks 跟进任务
     * @return 结果
     */
    @Override
    public int insertFollowUpTasks(FollowUpTasks followUpTasks)
    {
        return followUpTasksMapper.insertFollowUpTasks(followUpTasks);
    }

    /**
     * 修改跟进任务
     * 
     * @param followUpTasks 跟进任务
     * @return 结果
     */
    @Override
    public int updateFollowUpTasks(FollowUpTasks followUpTasks)
    {
        return followUpTasksMapper.updateFollowUpTasks(followUpTasks);
    }

    /**
     * 批量删除跟进任务
     * 
     * @param taskIds 需要删除的跟进任务主键
     * @return 结果
     */
    @Override
    public int deleteFollowUpTasksByTaskIds(Long[] taskIds)
    {
        return followUpTasksMapper.deleteFollowUpTasksByTaskIds(taskIds);
    }

    /**
     * 删除跟进任务信息
     * 
     * @param taskId 跟进任务主键
     * @return 结果
     */
    @Override
    public int deleteFollowUpTasksByTaskId(Long taskId)
    {
        return followUpTasksMapper.deleteFollowUpTasksByTaskId(taskId);
    }
}
