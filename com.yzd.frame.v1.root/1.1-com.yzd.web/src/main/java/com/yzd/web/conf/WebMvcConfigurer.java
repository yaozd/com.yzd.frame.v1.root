package com.yzd.web.conf;

import com.yzd.web.logging.LogTraceInterceptor;
import com.yzd.web.logging.LogURLInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
    //
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //为每个请求增加非登录下访问的Token
        registry.addInterceptor(new TokenCheckInterceptor())
                .addPathPatterns("/**");
        //
        // addPathPatterns 用于添加拦截规则, 这里假设拦截 /url 后面的全部链接
        // excludePathPatterns 用户排除拦截
        //位置的前后代表拦拦截器的执行顺序
        registry.addInterceptor(new LogTraceInterceptor())
                .addPathPatterns("/**");
        // LogInterceptor apply to all URLs.
        registry.addInterceptor(new LogURLInterceptor())
                .addPathPatterns("/api/**");
        //spring boot 可以配置多个拦截器
        //参考：Spring Boot Interceptors Tutorial
        //https://o7planning.org/en/11689/spring-boot-interceptors-tutorial
        // Old Login url, no longer use.
        // Use OldURLInterceptor to redirect to a new URL.
        //registry.addInterceptor(new OldLoginInterceptor())//
        //        .addPathPatterns("/admin/oldLogin");

        // This interceptor apply to URL like /admin/*
        // Exclude /admin/oldLogin
        //registry.addInterceptor(new AdminInterceptor())//
        //        .addPathPatterns("/admin/*")//
        //       .excludePathPatterns("/admin/oldLogin");
    }

}
