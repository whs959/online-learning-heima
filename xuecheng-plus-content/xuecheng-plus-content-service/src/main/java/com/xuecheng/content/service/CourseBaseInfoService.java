package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
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
     * 课程基本信息管理业务接口
     * @param pageParams 分页参数
     * @param queryCourseParamsDto 查询条件
     * @return 返回分页结果
     */
    PageResult<CourseBase>  queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto);
}