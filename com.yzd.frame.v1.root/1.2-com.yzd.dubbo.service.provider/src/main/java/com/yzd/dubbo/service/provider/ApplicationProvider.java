package com.yzd.dubbo.service.provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2017/2/28.
 */
@SpringBootApplication
@ComponentScan("com.yzd.dubbo.service.provider,com.yzd.db.temp.dao")
@ImportResource("classpath:com-yzd-dubbo-service-provider.xml")
public class ApplicationProvider {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationProvider.class);
    @Bean
    public CountDownLatch closeLatch() {
        return new CountDownLatch(1);
    }

    public static void main(String[] args) throws InterruptedException {
        logger.info("项目启动--BEGIN");
        ApplicationContext ctx = SpringApplication.run(ApplicationProvider.class, args);
        logger.info("项目启动--END");
        CountDownLatch closeLatch = ctx.getBean(CountDownLatch.class);
        closeLatch.await();
    }
}
