package com.yzd.web.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by Administrator on 2017/2/24.
 * http://localhost:8890/
 */
@SpringBootApplication
public class ApplicationWebApi  extends SpringBootServletInitializer {
    /**
     * Used when run as JAR
     */
    public static void main(String[] args) {

        SpringApplication.run(ApplicationWebApi.class, args);
    }
}
