package com.yzd.service.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/***
 *
 *
 * Created by yzd on 2018/8/27 12:40.
 */

@SpringBootApplication
@EnableScheduling
public class ApplicationMonitor {
    public static void main(String[] args){
        SpringApplication.run(ApplicationMonitor.class, args);
    }
}
