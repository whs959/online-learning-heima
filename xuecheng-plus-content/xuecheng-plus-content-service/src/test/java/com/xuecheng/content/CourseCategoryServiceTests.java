package com.xuecheng.content;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Author: whs
 * Date: 2024/5/30 8:56
 * FileName: CourseCategoryServiceTests
 * Description:
 */
@SpringBootTest
public class CourseCategoryServiceTests {
    @Autowired
    CourseCategoryService categoryService;


    @Test
    void testCourseBaseMapper() {
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = categoryService.queryTreeNodes();
    }
}
