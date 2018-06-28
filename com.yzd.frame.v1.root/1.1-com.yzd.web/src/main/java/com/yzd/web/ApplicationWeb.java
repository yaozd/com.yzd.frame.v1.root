package com.yzd.web;

import com.yzd.logging.consts.ParamEnum;
import com.yzd.logging.util.MDCUtil;
import com.yzd.logging.util.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import java.util.UUID;

/**
 * Created by Administrator on 2017/2/24.
 */
@SpringBootApplication
public class ApplicationWeb extends SpringBootServletInitializer {
    /**
     * Used when run as JAR
     */
    public static void main(String[] args) {

        SpringApplication.run(ApplicationWeb.class, args);
    }

    /**
     * Used when run as WAR
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ApplicationWeb.class);
    }
}
