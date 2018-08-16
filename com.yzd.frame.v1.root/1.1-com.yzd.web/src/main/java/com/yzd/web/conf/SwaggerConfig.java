package com.yzd.web.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
@Profile({"dev", "local"})//在生产环境不开启
public class SwaggerConfig {

    /***
     * http://localhost:8880/swagger-ui.html
     * @return
     */
    @Bean
    public Docket buildDocket() {
        //在header 中添加一个默认值
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("access_token11")
                .defaultValue("user-eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjAifQ.EzOtp4tB1dD7xTGWIc5Dlceoi7undj9ikhDdkuz23N_te3iLoE61nqSd-X-9hmC_ERIdKMXu62ZHbuV4vqWzhQ")
                .description("令牌")
                .modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        //rest full 的API要与controller的命令空间分开。分开以后才不会影响到swagger文档的正常显示
        return new Docket(DocumentationType.SWAGGER_2)
                //.groupName("分组名")
                //调整swagger请求的URL
                //.host("192.168.0.22")
                //调整请求的前缀路径，一般情况这里是不添加的
                //.pathMapping("/v1/v2")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yzd.web.controllerApi"))
                .paths(PathSelectors.any())
                .build()
                //全局head变量
                //.globalOperationParameters(pars)
                //设置全局认证的apikey的信息
                .securitySchemes(newArrayList(apiKey()));
    }
    //设置全局认证的apikey的信息
    private ApiKey apiKey() {
        return new ApiKey("X-Authorization", "Authorization", "header");
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot 中使用 Swagger2构建 RESTfulAPI 文档")
                .description("更多 Spring Boot 相关文章 http://localhost:8880")
                .termsOfServiceUrl("http://localhost:8880")
                .version("V1.0")
                .build();
    }
}