package com.xuecheng.media;

import io.minio.MinioClient;
import io.minio.UploadObjectArgs;

public class MinioTest {

    static MinioClient minioClient =
            MinioClient.builder()
                    .endpoint("http://47.108.25.176:9000")
                    .credentials("minioadmin", "minioadmin")
                    .build();

   //上传文件
    public  void upload() {
        try {
            UploadObjectArgs testbucket = UploadObjectArgs.builder()
                    .bucket("testbucket")
//                    .object("test001.mp4")
                    .object("001/test001.mp4")//添加子目录
                    .filename("D:\\Java\\后端\\尚硅谷高级技术之Linux+Shell教程\\视频\\001_尚硅谷课程系列之Linux_课程介绍.mp4")
                    .contentType("video/mp4")//默认根据扩展名确定文件内容类型，也可以指定
                    .build();
            minioClient.uploadObject(testbucket);
            System.out.println("上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("上传失败");
        }
    }

    public static void main(String[] args) {
        MinioTest minioTest = new MinioTest();
        minioTest.upload();
    }

}