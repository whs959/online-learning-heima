package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;

import java.util.List;

/**
 * Author: whs
 * Date: 2024/6/4 11:45
 * FileName: TeachplanService
 * Description:
 */
public interface TeachplanService {
    /**
     * @description 查询课程计划树型结构
     * @param courseId  课程id
     */
    List<TeachplanDto> findTeachplanTree(long courseId);

    /**
     * @description 保存课程计划信息
     */
    void saveTeachplan(SaveTeachplanDto saveTeachplanDto);

    /**
     * 删除课程计划
     * @param id
     */
    void deleteTeachPlanById(Long id);

    /**
     * 课程计划下移
     * @param id
     */
    void movedown(Long id);

    /**
     * 课程计划上移
     * @param id
     */
    void moveup(Long id);
}
