package com.yzd.web.api.common.config;

import com.yzd.web.api.common.interceptor.ApiTokenInterceptor;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/***
 *
 *
 * @author yzd
 * @date 2018/9/12 16:38.
 */

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    /**
     * 配置拦截器
     *
     * @param registry
     * @author yzd
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ApiTokenInterceptor())
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/test/token/**");
    }

    /**
     * SpringMvc_@RequestMapping设置Router Url大小写不敏感
     * http://www.cnblogs.com/gossip/p/5441358.html
     * 如何取消 /index.*映射
     * http://www.oschina.net/question/190714_116949
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        AntPathMatcher matcher = new AntPathMatcher();
        matcher.setCaseSensitive(false);
        configurer.setPathMatcher(matcher);
        configurer.setUseSuffixPatternMatch(false);
    }

    @Bean
    public EmbeddedServletContainerFactory createEmbeddedServletContainerFactory() {
        TomcatEmbeddedServletContainerFactory tomcatFactory = new TomcatEmbeddedServletContainerFactory();
        //tomcatFactory.setPort(8081);
        tomcatFactory.addConnectorCustomizers(new MyTomcatConfig());
        return tomcatFactory;
    }

    //设置静态资源目录
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // Resources controlled by Spring Security, which
        // adds "Cache-Control: must-revalidate".
        registry.addResourceHandler("/feedbacks/**")
                .addResourceLocations("classpath:/feedbacks/")
                .setCachePeriod(3600 * 24 * 100);//设置静态资源缓存时间为100天
    }
}