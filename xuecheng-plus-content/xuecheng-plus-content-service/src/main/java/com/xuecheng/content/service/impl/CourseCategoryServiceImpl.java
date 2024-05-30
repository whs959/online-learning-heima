package com.xuecheng.content.service.impl;

import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.model.po.CourseCategory;
import com.xuecheng.content.service.CourseCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: whs
 * Date: 2024/5/30 8:36
 * FileName: CourseCategoryServiceImpl
 * Description:
 */
@Service
@Slf4j
public class CourseCategoryServiceImpl implements CourseCategoryService {
    @Autowired
    CourseCategoryMapper courseCategoryMapper;

    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes() {
        //先查询出所有
        List<CourseCategory> categoryList = courseCategoryMapper.selectList(null);
        List<CourseCategoryTreeDto> alls = categoryList.stream()
                .map(category -> {
                    CourseCategoryTreeDto dto = new CourseCategoryTreeDto();
                    BeanUtils.copyProperties(category,dto);
                    return dto;
                }).collect(Collectors.toList());
        //查询出所有的父级菜单
        List<CourseCategoryTreeDto> collect = alls.stream()
                .filter(all -> all.getParentid().equals("0"))
                .map(categoryParent -> {
                    categoryParent.setChildrenTreeNodes(getChilds(categoryParent, alls));
                    return categoryParent;
                }).collect(Collectors.toList());
        return collect;
    }

    /**
     * stream流方式写法
     * @param root 根节点
     * @param alls 所有数据
     * @return
     */
    private List<CourseCategoryTreeDto> getChilds(CourseCategoryTreeDto root, List<CourseCategoryTreeDto> alls) {
        List<CourseCategoryTreeDto> collect = alls.stream()
                .filter(all -> all.getParentid().equals(root.getId()))
                .map(categoryList -> {
                    categoryList.setChildrenTreeNodes(getChilds(categoryList, alls));
                    return categoryList;
                }).collect(Collectors.toList());
        return collect;
    }


    /**
     * 非stream流方式写法
     * @param root 父节点
     * @param childs 所有数据
     * @return
     */
    public static CourseCategoryTreeDto findChildren(CourseCategoryTreeDto root, List<CourseCategoryTreeDto> childs) {
        for (CourseCategoryTreeDto child : childs) {
            //判断该节点的父节点id是否等于传递进来的节点的id
            if (root.getId().equals(child.getParentid())){
                if (root.getChildrenTreeNodes() == null){
                    root.setChildrenTreeNodes(new ArrayList<>());
                }
                //递归查询当前节点是否还有子节点，直到没有子节点那就就将当前节点
                root.getChildrenTreeNodes().add(findChildren(child,childs));
            }
        }
        return root;
    }
}
