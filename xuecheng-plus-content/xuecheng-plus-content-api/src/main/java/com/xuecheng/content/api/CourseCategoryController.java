package com.xuecheng.content.api;

import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author: whs
 * Date: 2024/5/29 16:01
 * FileName: CourseCategoryController
 * Description:
 */
@RestController
@Slf4j
@Api(value = "课程分类",tags = "课程分类")
public class CourseCategoryController {
    @Autowired
    CourseCategoryService categoryService;
    @GetMapping("/course-category/tree-nodes")
    public List<CourseCategoryTreeDto> queryTreeNodes(){
        //测试3
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = categoryService.queryTreeNodes();
        return courseCategoryTreeDtos;
    }
}
