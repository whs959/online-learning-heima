package com.xuecheng.content.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sun.org.apache.regexp.internal.RE;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author: whs
 * Date: 2024/6/4 17:44
 * FileName: CourseTeacherController
 * Description:
 */
@RestController
@Api("课程师资管理")
public class CourseTeacherController {
    @Autowired
    private CourseTeacherService courseTeacherService;

    @GetMapping("/courseTeacher/list/{courseId}")
    @ApiOperation("根据课程id查询师资信息")
    public List<CourseTeacher> courseTeacherList(@PathVariable(name = "courseId") Long courseId){
        LambdaQueryWrapper<CourseTeacher> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CourseTeacher::getCourseId,courseId);
        List<CourseTeacher> list = courseTeacherService.list(lambdaQueryWrapper);
        return list;
    }
}
