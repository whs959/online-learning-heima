package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.base.execption.XueChengPlusException;
import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.mapper.TeachplanMediaMapper;
import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.model.po.TeachplanMedia;
import com.xuecheng.content.service.TeachplanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: whs
 * Date: 2024/6/4 11:46
 * FileName: TeachplanServiceImpl
 * Description:
 */
@Service
public class TeachplanServiceImpl implements TeachplanService {
    @Autowired 
    private TeachplanMapper teachplanMapper;

    @Autowired
    private TeachplanMediaMapper teachplanMediaMapper;

    /**
     * 课程计划下移
     *
     * @param id
     */
    @Override
    @Transactional
    public void movedown(Long id) {
        Teachplan teachplan = teachplanMapper.selectById(id);
        if (teachplan == null){
            XueChengPlusException.cast("无匹配的课程计划");
        }

        //根据父级id，课程id,查询对应的同级教学计划
        Long parentId = teachplan.getParentid();
        Long courseId = teachplan.getCourseId();
        Teachplan teachPlanSwp = getTeachPlanDown(parentId, courseId, id);
        if (teachPlanSwp != null){
            //交互两对象的orderby
            Integer orderby = teachPlanSwp.getOrderby();
            teachPlanSwp.setOrderby(teachplan.getOrderby());
            teachplan.setOrderby(orderby);
            teachplanMapper.updateById(teachPlanSwp);
            teachplanMapper.updateById(teachplan);
        }
    }

    private Teachplan getTeachPlanDown(Long parentId, Long courseId,Long teachPlanId) {
        LambdaQueryWrapper<Teachplan> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Teachplan::getCourseId, courseId);
        lambdaQueryWrapper.eq(Teachplan::getParentid, parentId);
        lambdaQueryWrapper.orderByAsc(Teachplan::getOrderby);
        List<Teachplan> teachplanList = teachplanMapper.selectList(lambdaQueryWrapper);

        if (teachplanList.size() == 1){
            XueChengPlusException.cast("无法移动，无同级数据");
        }

        for (int i = 0; i < teachplanList.size(); i++) {
            Teachplan teachplan = teachplanList.get(i);
            if (teachplan.getId().equals(teachPlanId) && i == teachplanList.size() - 1){
                XueChengPlusException.cast("无法移动，最下级无法移动");
            }
            if (teachplan.getId().equals(teachPlanId)){
                return teachplanList.get(i+1);
            }
        }
        return null;
    }

    /**
     * 课程计划上移
     *
     * @param id
     */
    @Override
    @Transactional
    public void moveup(Long id) {
        Teachplan teachplan = teachplanMapper.selectById(id);
        if (teachplan == null){
            XueChengPlusException.cast("无匹配的课程计划");
        }

        //根据父级id，课程id,查询对应的同级教学计划
        Long parentId = teachplan.getParentid();
        Long courseId = teachplan.getCourseId();
        Teachplan teachPlanSwp = getTeachPlanUp(parentId, courseId, id);

        if (teachPlanSwp != null){
            //交互两对象的orderby
            Integer orderby = teachPlanSwp.getOrderby();
            teachPlanSwp.setOrderby(teachplan.getOrderby());
            teachplan.setOrderby(orderby);
            teachplanMapper.updateById(teachPlanSwp);
            teachplanMapper.updateById(teachplan);
        }
    }

    private Teachplan getTeachPlanUp(Long parentId, Long courseId,Long teachPlanId) {
        LambdaQueryWrapper<Teachplan> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Teachplan::getCourseId, courseId);
        lambdaQueryWrapper.eq(Teachplan::getParentid, parentId);
        lambdaQueryWrapper.orderByAsc(Teachplan::getOrderby);
        List<Teachplan> teachplanList = teachplanMapper.selectList(lambdaQueryWrapper);

        if (teachplanList.size() == 1){
            XueChengPlusException.cast("无法移动，无同级数据");
        }

        for (int i = 0; i < teachplanList.size(); i++) {
            Teachplan teachplan = teachplanList.get(i);
            if (teachplan.getId().equals(teachPlanId) && i == 0){
                XueChengPlusException.cast("无法移动，最上级无法移动");
            }
            if (teachplan.getId().equals(teachPlanId)){
                return teachplanList.get(i-1);
            }
        }
        return null;
    }

    /**
     * 删除课程计划
     *
     * @param id
     */
    @Override
    public void deleteTeachPlanById(Long id) {
        //查询对应的课程计划
        Teachplan teachplan = teachplanMapper.selectById(id);
        if (teachplan == null){
            XueChengPlusException.cast("无匹配的课程计划");
        }

        //查询是否有子级章节
        LambdaQueryWrapper<Teachplan> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Teachplan::getParentid,id);
        List<Teachplan> teachplanList = teachplanMapper.selectList(lambdaQueryWrapper);
        if (teachplanList.size() > 0){
            XueChengPlusException.cast("课程计划信息还有子级信息，无法操作");
        }

        //进行删除
        teachplanMapper.deleteById(id);
    }

    /**
     * @param saveTeachplanDto
     * @description 保存课程计划信息
     */
    @Override
    public void saveTeachplan(SaveTeachplanDto saveTeachplanDto) {
        //课程计划id
        Long id = saveTeachplanDto.getId();

        //id存在则为修改，id不存在则为新增
        if (id != null){
            //修改
            Teachplan teachplan = teachplanMapper.selectById(id);
            BeanUtils.copyProperties(saveTeachplanDto,teachplan);
            teachplanMapper.updateById(teachplan);
        }else {
            //新增
            Teachplan teachplan = new Teachplan();
            //设置排序号
            int count = this.getTeachPlanCount(saveTeachplanDto.getCourseId(), saveTeachplanDto.getParentid());
            teachplan.setOrderby(count+1);
            BeanUtils.copyProperties(saveTeachplanDto,teachplan);
            teachplanMapper.insert(teachplan);
        }
    }

    private int getTeachPlanCount(long courseId,long parentId){
        LambdaQueryWrapper<Teachplan> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Teachplan::getCourseId,courseId);
        lambdaQueryWrapper.eq(Teachplan::getParentid,parentId);
        Integer count = teachplanMapper.selectCount(lambdaQueryWrapper);
        return count;
    }

    /**
     * @param courseId 课程id
     * @description 查询课程计划树型结构
     */
    @Override
    public List<TeachplanDto> findTeachplanTree(long courseId) {
        //查询课程计划
        LambdaQueryWrapper<Teachplan> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Teachplan::getCourseId,courseId);
        List<Teachplan> teachplans = teachplanMapper.selectList(lambdaQueryWrapper);

        //将Teachplan封装到TeachplanDto中
        List<TeachplanDto> teachplanDtoList = teachplans.stream().map(item -> {
            TeachplanDto teachplanDto = new TeachplanDto();
            BeanUtils.copyProperties(item, teachplanDto);
            return teachplanDto;
        }).collect(Collectors.toList());

        //收集课程计划id
        List<Long> teachplanIds = teachplans.stream().map(item -> item.getId()).collect(Collectors.toList());
        if (teachplanIds.size() > 0){
            //查询课程计划关联的视频信息
            LambdaQueryWrapper<TeachplanMedia> mediaQueryWrapper = new LambdaQueryWrapper<>();
            mediaQueryWrapper.in(TeachplanMedia::getTeachplanId,teachplanIds);
            List<TeachplanMedia> teachplanMediaList = teachplanMediaMapper.selectList(mediaQueryWrapper);
            Map<Long, TeachplanMedia> teachplanMediaMap = teachplanMediaList.stream().collect(Collectors.toMap(
                                                                    TeachplanMedia::getTeachplanId,
                                                                    item -> item,
                                                                    (existingValue, newValue) -> existingValue
                                                            ));
            //给课程计划设置关联的视频信息，课程计划与视频信息是一对一关系
            if (teachplanMediaMap.size() > 0){
                for (TeachplanDto teachplanDto : teachplanDtoList) {
                    TeachplanMedia teachplanMedia = teachplanMediaMap.get(teachplanDto.getId());
                    teachplanDto.setTeachplanMedia(teachplanMedia);
                }
            }
        }

        //将teachplanDtoList转换为树形结构，进行返回，其中grade字段表示层级，从1开始，parentid为0表示根节点
        List<TeachplanDto> collect = teachplanDtoList.stream().filter(all -> all.getParentid().equals(0l)).map(
                teachplanDto -> {
                    teachplanDto.setTeachPlanTreeNodes(getChilds(teachplanDto, teachplanDtoList));
                    return teachplanDto;
                }).sorted(Comparator.comparingInt(Teachplan::getOrderby)).collect(Collectors.toList());

        return collect;
    }

    private List<TeachplanDto> getChilds(TeachplanDto root, List<TeachplanDto> alls) {
        List<TeachplanDto> collect = alls.stream()
                .filter(all -> all.getParentid().equals(root.getId()))
                .map(teachplanDto -> {
                    teachplanDto.setTeachPlanTreeNodes(getChilds(teachplanDto, alls));
                    return teachplanDto;
                }).sorted(Comparator.comparingInt(Teachplan::getOrderby)).collect(Collectors.toList());
        return collect;
    }
}
