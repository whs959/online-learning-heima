package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuecheng.content.mapper.CourseTeacherMapper;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseTeacherService;
import org.springframework.stereotype.Service;

/**
 * Author: whs
 * Date: 2024/6/4 17:39
 * FileName: CourseTeacherServiceImpl
 * Description:
 */
@Service
public class CourseTeacherServiceImpl extends ServiceImpl<CourseTeacherMapper,CourseTeacher> implements CourseTeacherService{
}
