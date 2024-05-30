package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.CourseCategory;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Author: whs
 * Date: 2024/5/29 15:58
 * FileName: CourseCategoryTreeDto
 * Description:
 */
@Data
public class CourseCategoryTreeDto extends CourseCategory implements Serializable,Comparable {
    private List<CourseCategoryTreeDto> childrenTreeNodes;

    @Override
    public int compareTo(Object o) {
        CourseCategoryTreeDto o1 = (CourseCategoryTreeDto)o;
        return this.getOrderby().compareTo(o1.getOrderby());
    }
}
