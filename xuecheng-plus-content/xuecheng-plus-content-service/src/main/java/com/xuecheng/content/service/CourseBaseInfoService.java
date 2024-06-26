package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;

/**
 * Author: whs
 * Date: 2024/5/28 13:47
 * FileName: CourseBaseInfoService
 * Description:
 */
public interface CourseBaseInfoService {
    /**
     * 删除课程信息，并且删除其课程营销信息、课程计划、课程计划关联的媒体信息、课程师资
     * @param id
     */
    void  deleteCourseBaseById(Long id);
    /**
     * 课程基本信息管理业务接口
     * @param pageParams           分页参数
     * @param queryCourseParamsDto 查询条件
     * @return 返回分页结果
     */
    PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto);

    /**
     * 添加课程基本信息
     * @param companyId    教学机构id
     * @param addCourseDto 课程基本信息
     * @return 课程基本信息
     */
    CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);

    /**
     * 根据课程id查询课程基本信息，包括基本信息和营销信息
     * @param courseBase 课程对象
     * @return
     */
    CourseBaseInfoDto getCourseBaseInfoDto(CourseBase courseBase);

    /**
     * 修改课程信息
     * @param companyId 机构id
     * @param dto 课程信息
     * @return 返回课程信息+营销信息
     */
    CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto dto);
}