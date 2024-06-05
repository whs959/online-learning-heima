package com.xuecheng.content.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sun.org.apache.regexp.internal.RE;
import com.xuecheng.base.execption.XueChengPlusException;
import com.xuecheng.content.model.dto.CourseTeacherDto;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/courseTeacher")
    @ApiOperation("新增师资信息")
    public CourseTeacher saveCourseTeacher(@RequestBody CourseTeacherDto dto){
        CourseTeacher courseTeacher = courseTeacherService.saveCourseTeacher(dto);
        return courseTeacher;
    }

    @PutMapping("/courseTeacher")
    @ApiOperation("修改师资信息")
    public CourseTeacher updateCourseTeacher(@RequestBody CourseTeacher courseTeacher){
        boolean flag = courseTeacherService.updateById(courseTeacher);
        if (!flag){
            XueChengPlusException.cast("更新失败，请稍后重试");
        }
        return courseTeacherService.getById(courseTeacher.getId());
    }

    @DeleteMapping("/courseTeacher/course/{courseId}/{teacherId}")
    @ApiOperation("删除师资信息")
    public void deleteCourseTeacherById(@PathVariable(name = "courseId") Long courseId,@PathVariable(name = "teacherId") Long teacherId){
        courseTeacherService.removeById(teacherId);
    }
}
