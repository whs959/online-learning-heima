package com.xuecheng;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Author: whs
 * Date: 2024/5/20 20:10
 * FileName: ContentApplication
 * Description:
 */
@SpringBootApplication
@EnableSwagger2Doc
public class ContentApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentApplication.class,args);
    }
}
