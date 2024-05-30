package com.xuecheng.content.api;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author: whs
 * Date: 2024/5/29 16:01
 * FileName: CourseCategoryController
 * Description:
 */
@RestController
@Slf4j
public class CourseCategoryController {
    @GetMapping("/course-category/tree-nodes")
    private List<CourseCategoryTreeDto> queryTreeNodes(){
        return null;
    }
}
