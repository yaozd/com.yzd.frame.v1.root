package com.yzd.dubbo.service.consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import java.io.IOException;

/**
 * Created by Administrator on 2017/2/28.
 */
@SpringBootApplication
@ComponentScan("com.yzd.dubbo.service.consumer")
@ImportResource("classpath:com-yzd-dubbo-service-consumer.xml")
public class ApplicationConsumer {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationConsumer.class);
    public static void main(String[] args) throws IOException {
        logger.info("项目启动--BEGIN");
        SpringApplication.run(ApplicationConsumer.class, args);
        //System.in.read();
    }
}