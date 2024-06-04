package com.xuecheng.content.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Author: whs
 * Date: 2024/6/4 17:55
 * FileName: CourseTeacherDto
 * Description:
 */
@Data
@ApiModel(value="CourseTeacherDto", description="新增课程基本信息")
public class CourseTeacherDto {
    /**
     * 课程标识
     */
    @NotNull
    private Long courseId;

    /**
     * 教师标识
     */
    @NotNull
    @NotEmpty
    private String teacherName;

    /**
     * 教师职位
     */
    @NotNull
    @NotEmpty
    private String position;

    /**
     * 教师简介
     */
    @NotNull
    @NotEmpty
    private String introduction;
}
