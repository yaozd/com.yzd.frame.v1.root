package com.yzd.web.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by Administrator on 2017/2/24.
 * http://localhost:8890/
 */
@SpringBootApplication
@EnableSwagger2
public class ApplicationWebApi  extends SpringBootServletInitializer {
    /**
     * Used when run as JAR
     */
    public static void main(String[] args) {

        SpringApplication.run(ApplicationWebApi.class, args);
    }
}
