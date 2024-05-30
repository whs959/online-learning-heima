package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.model.po.CourseCategory;

import java.util.List;

/**
 * Author: whs
 * Date: 2024/5/30 8:34
 * FileName: CourseCategoryService
 * Description:
 */
public interface CourseCategoryService {
    List<CourseCategoryTreeDto> queryTreeNodes();
}
