package com.xuecheng.content.service;

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
}
