package com.yzd.service.monitor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;

/***
 *
 *
 * Created by yzd on 2018/8/27 15:06.
 */

@Configuration
//@EnableScheduling //把所有的启动配置入口统一入在application上面
public class ScheduleConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }

    //@Bean(destroyMethod="shutdown")
    @Bean
    public Executor taskExecutor() {
        //可以根据调度任务的个数来设置线程池的大小
        //return Executors.newScheduledThreadPool(30);
        //Executors.newScheduledThreadPool(30);一样没有根本的区别只是自定义线程池的名字
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(30);
        scheduler.setThreadNamePrefix("TaskScheduler-");
        //设置调度任务线程池终止等待时间为12秒
        //scheduler的终止是在应用关闭【applicationEvent instanceof ContextClosedEvent】之后执行的。
        //计划任务的中断等待时间设置
        scheduler.setAwaitTerminationSeconds(3);
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        return scheduler;
    }
}