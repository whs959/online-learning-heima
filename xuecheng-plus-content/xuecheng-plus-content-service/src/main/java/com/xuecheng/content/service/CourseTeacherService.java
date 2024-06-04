package com.xuecheng.content.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xuecheng.content.model.dto.CourseTeacherDto;
import com.xuecheng.content.model.po.CourseTeacher;

import javax.jnlp.BasicService;
import java.util.List;

/**
 * Author: whs
 * Date: 2024/6/4 17:38
 * FileName: CourseTeacherService
 * Description:
 */
public interface CourseTeacherService extends IService<CourseTeacher> {
    /**
     * 新增师资信息
     * @param dto 师资基本信息
     * @return
     */
    CourseTeacher saveCourseTeacher(CourseTeacherDto dto);
}
