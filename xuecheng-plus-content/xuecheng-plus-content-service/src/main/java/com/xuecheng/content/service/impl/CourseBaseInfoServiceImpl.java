package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.mapper.CourseMarketMapper;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseCategory;
import com.xuecheng.content.model.po.CourseMarket;
import com.xuecheng.content.service.CourseBaseInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Author: whs
 * Date: 2024/5/28 13:49
 * FileName: CourseBaseInfoServiceImpl
 * Description:
 */
@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {
    @Autowired
    CourseBaseMapper courseBaseMapper;

    @Autowired
    CourseMarketMapper courseMarketMapper;

    @Autowired
    CourseCategoryMapper courseCategoryMapper;
    /**
     * 添加课程基本信息
     * @param companyId 教学机构id
     * @param addCourseDto 课程基本信息
     * @return 课程基本信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto) {
        //合法性校验
        createCourseValidate(addCourseDto);
        //构建课程基本信息
        CourseBase courseBase = new CourseBase();
        BeanUtils.copyProperties(addCourseDto,courseBase); //将填写的课程信息赋值给新增对象
        courseBase.setAuditStatus("202002");          //设置审核状态
        courseBase.setStatus("203001");               //设置发布状态
        courseBase.setCompanyId(companyId);           //机构id
        courseBase.setCreateDate(LocalDateTime.now());//添加时间
        //调用mapper将数据落库
        int insert = courseBaseMapper.insert(courseBase);
        if (insert <= 0){
            throw new RuntimeException("新增课程基本信息失败");
        }

        //向课程营销表保存课程营销信息
        int saveSuccessAcount = saveCourseMarket(addCourseDto, courseBase);
        if (saveSuccessAcount <= 0){
            throw new RuntimeException("保存课程营销信息失败");
        }

        //查询课程基本信息及营销信息并返回
        return getCourseBaseInfoDto(courseBase);
    }

    private CourseBaseInfoDto getCourseBaseInfoDto(CourseBase courseBase) {
        CourseBase courseBase1 = courseBaseMapper.selectById(courseBase.getId()); //课程基本信息
        if (courseBase1 == null){
            return null;
        }
        CourseMarket courseMarket = courseMarketMapper.selectById(courseBase.getId());//营销信息
        //构建返回对象
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase1,courseBaseInfoDto);
        if (courseMarket != null){
            BeanUtils.copyProperties(courseMarket,courseBaseInfoDto);
        }

        //查询分类名称
        CourseCategory courseCategoryBySt = courseCategoryMapper.selectById(courseBase.getSt()); //小分类
        courseBaseInfoDto.setStName(courseCategoryBySt.getName());
        CourseCategory courseCategoryByMt = courseCategoryMapper.selectById(courseBase.getMt());//大分类
        courseBaseInfoDto.setMtName(courseCategoryByMt.getName());
        return courseBaseInfoDto;
    }

    /**
     * 向课程营销表保存课程营销信息
     */
    private int saveCourseMarket(AddCourseDto addCourseDto, CourseBase courseBase) {
        CourseMarket courseMarket = new CourseMarket();
        BeanUtils.copyProperties(addCourseDto,courseMarket);
        Long courseBaseId = courseBase.getId();//课程基本信息id
        courseMarket.setId(courseBaseId); //课程营销信息的id与对应的课程基本信息id一样

        //收费明细
        String charge = courseMarket.getCharge();
        if (StringUtils.isBlank(charge)){
            throw new RuntimeException("收费规则没有选择");
        }
        //收费规则为收费
        if (charge.equals("201001")){
            Float price = courseMarket.getPrice();
            if (price == null || price.floatValue() <= 0){
                throw new RuntimeException("课程为收费价格不能为空且必须大于0");
            }
        }
        //根据id从课程营销表查询
        CourseMarket courseMarketObj = courseMarketMapper.selectById(courseMarket.getId());
        if (courseMarketObj == null){
            return courseMarketMapper.insert(courseMarket);
        }else {
            BeanUtils.copyProperties(courseMarket,courseMarketObj);
            courseMarketObj.setId(courseMarket.getId());
            return courseMarketMapper.updateById(courseMarketObj);
        }
    }

    /**
     * 添加课程基本信息时进行校验
     * @param addCourseDto 课程基本信息
     */
    private static void createCourseValidate(AddCourseDto addCourseDto) {
        if (StringUtils.isBlank(addCourseDto.getName())) {
            throw new RuntimeException("课程名称为空");
        }

        if (StringUtils.isBlank(addCourseDto.getMt())) {
            throw new RuntimeException("课程分类为空");
        }

        if (StringUtils.isBlank(addCourseDto.getSt())) {
            throw new RuntimeException("课程分类为空");
        }

        if (StringUtils.isBlank(addCourseDto.getGrade())) {
            throw new RuntimeException("课程等级为空");
        }

        if (StringUtils.isBlank(addCourseDto.getTeachmode())) {
            throw new RuntimeException("教育模式为空");
        }

        if (StringUtils.isBlank(addCourseDto.getUsers())) {
            throw new RuntimeException("适应人群为空");
        }

        if (StringUtils.isBlank(addCourseDto.getCharge())) {
            throw new RuntimeException("收费规则为空");
        }
    }

    /**
     * 课程基本信息管理业务接口
     * @param pageParams 分页参数
     * @param queryCourseParamsDto 查询条件
     * @return 返回分页结果
     */
    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto) {
        //构建查询条件
        LambdaQueryWrapper<CourseBase> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //构建查询条件，根据课程名称查询
        lambdaQueryWrapper.like(!StringUtils.isEmpty(queryCourseParamsDto.getCourseName()),CourseBase::getName,queryCourseParamsDto.getCourseName());
        //构建查询条件，根据课程审核状态查询
        lambdaQueryWrapper.eq(!StringUtils.isEmpty(queryCourseParamsDto.getAuditStatus()),CourseBase::getAuditStatus,queryCourseParamsDto.getAuditStatus());
        //构建查询条件，根据课程发布状态查询
        lambdaQueryWrapper.eq(!StringUtils.isEmpty(queryCourseParamsDto.getPublishStatus()),CourseBase::getStatus,queryCourseParamsDto.getPublishStatus());

        //分页对象
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(),pageParams.getPageSize());
        Page<CourseBase> courseBasePage = courseBaseMapper.selectPage(page, lambdaQueryWrapper);

        //构建返回数据
        List<CourseBase> records = courseBasePage.getRecords();
        long total = courseBasePage.getTotal();
        PageResult<CourseBase> pageResult = new PageResult<>(records,total,pageParams.getPageNo(),pageParams.getPageSize());
        return pageResult;
    }
}
