package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuecheng.content.mapper.CourseTeacherMapper;
import com.xuecheng.content.model.dto.CourseTeacherDto;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseTeacherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Author: whs
 * Date: 2024/6/4 17:39
 * FileName: CourseTeacherServiceImpl
 * Description:
 */
@Service
public class CourseTeacherServiceImpl extends ServiceImpl<CourseTeacherMapper,CourseTeacher> implements CourseTeacherService{
    @Autowired
    private CourseTeacherMapper courseTeacherMapper;
    /**
     * 新增师资信息
     * @param dto 师资基本信息
     * @return
     */
    @Override
    public CourseTeacher saveCourseTeacher(CourseTeacherDto dto) {
        CourseTeacher courseTeacher = new CourseTeacher();
        BeanUtils.copyProperties(dto,courseTeacher);
        courseTeacher.setCreateDate(LocalDateTime.now());
        courseTeacherMapper.insert(courseTeacher);
        return null;
    }
}
