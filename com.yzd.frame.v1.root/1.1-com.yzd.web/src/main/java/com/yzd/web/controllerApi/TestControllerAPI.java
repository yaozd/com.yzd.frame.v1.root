package com.yzd.web.controllerApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/test")
@Api(description = "测试管理")
public class TestControllerAPI {
    //当前只是用于测试，实际生产过程中不要把文件上传写在此处，应放在controllerFile下
    //如何用SpringBoot框架来接收multipart/form-data文件
    //https://blog.csdn.net/qq_27088383/article/details/77980432
    //
    //http://localhost:8880/multipartFileTest.html
    @PostMapping("upload")
    @ApiOperation(value = "模拟文件上传（DONE）")
    public String upload(@RequestParam("file") MultipartFile file) {
    //public String upload(@RequestParam("file") MultipartFile[] file) {
        return "test/upload";
    }
}
