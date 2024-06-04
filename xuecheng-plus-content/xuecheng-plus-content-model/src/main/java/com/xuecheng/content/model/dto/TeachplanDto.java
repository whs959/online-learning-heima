package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.model.po.TeachplanMedia;
import lombok.Data;
import lombok.ToString;

import java.util.Comparator;
import java.util.List;

/**
 * Author: whs
 * Date: 2024/6/4 11:37
 * FileName: TeachplanDto
 * Description:
 */
@Data
@ToString
public class TeachplanDto extends Teachplan{
    //课程计划关联的媒资信息
    TeachplanMedia teachplanMedia;
    //子节点
    private List<TeachplanDto> teachPlanTreeNodes;
}
