package com.xuecheng.content.api;

import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.service.TeachplanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author: whs
 * Date: 2024/6/4 11:38
 * FileName: TeachplanController
 * Description:
 */
@Api(value = "课程计划",tags = "课程计划" )
@RestController
public class TeachplanController {
    @Autowired
    private TeachplanService teachplanService;

    @ApiOperation("查询课程计划树形结构")
    @ApiImplicitParam(value = "courseId",name = "课程Id",required = true,dataType = "Long",paramType = "path")
    @GetMapping("/teachplan/{courseId}/tree-nodes")
    public List<TeachplanDto> getTreeNodes(@PathVariable(value = "courseId") Long courseId){
        return teachplanService.findTeachplanTree(courseId);
    }

    @ApiOperation("课程计划创建或修改")
    @PostMapping("/teachplan")
    public void saveTeachplan(@RequestBody SaveTeachplanDto saveTeachplanDto){
        teachplanService.saveTeachplan(saveTeachplanDto);
    }

    @ApiOperation("根据id删除课程信息")
    @DeleteMapping("/teachplan/{planId}")
    public void  deleteTeachplan(@PathVariable(name = "planId") Long id){
        teachplanService.deleteTeachPlanById(id);
    }

    @ApiOperation("课程计划下移")
    @PostMapping("/teachplan/movedown/{planId}")
    public void movedown(@PathVariable(name = "planId") Long id){
        teachplanService.movedown(id);
    }

    @ApiOperation("课程计划上移")
    @PostMapping("/teachplan/moveup/{planId}")
    public void moveup(@PathVariable(name = "planId") Long id){
        teachplanService.moveup(id);
    }
}
