package com.yzd.web.conf;

import com.yzd.logging.filter.ServletLogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean filterDemo4Registration() {
        // * 分布式日志跟踪拦截器LogTraceInterceptor
        // * LogTraceInterceptor等于LogURLInterceptor+ServletLogFilter
        // * 优点 出现异常时不再打印LogTraceInterceptor的堆栈信息，但如里使用ServletLogFilter则打印ServletLogFilter的堆栈信息
        // * 如果是spring mvc框架建议使用LogTraceInterceptor
        //=====================================================
        //springboot之多个filter的执行顺序以及配置
        //https://blog.csdn.net/east123321/article/details/80856389
        FilterRegistrationBean registration = new FilterRegistrationBean();
        //注入过滤器
        registration.setFilter(new ServletLogFilter());
        //拦截规则
        registration.addUrlPatterns("/*");
        //过滤器名称
        registration.setName("mdcFilter");
        //是否自动注册 false 取消Filter的自动注册
        //registration.setEnabled(false);
        //过滤器顺序
        registration.setOrder(1);
        return registration;
    }

}
