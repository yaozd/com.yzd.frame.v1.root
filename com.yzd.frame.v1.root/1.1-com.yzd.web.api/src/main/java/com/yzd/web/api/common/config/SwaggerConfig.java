package com.yzd.web.api.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/***
 *
 *
 * @author yzd
 * @date 2018/9/12 16:38.
 */
@Configuration
public class SwaggerConfig {
    /***
     * http://localhost:8890/swagger-ui.html
     * @return
     */
    @Bean
    public Docket buildDocket() {
        //rest full 的API要与controller的命令空间分开。分开以后才不会影响到swagger文档的正常显示
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yzd.web.api.controllerApi"))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(getGlobalParamInHeader());
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot 中使用 Swagger2构建 RESTfulAPI 文档")
                .description("更多 Spring Boot 相关文章 http://localhost:8880")
                .termsOfServiceUrl("http://localhost:8890")
                .version("V1.0")
                .build();
    }

    /***
     * 全局head变量
     * @return
     */
    private List<Parameter> getGlobalParamInHeader(){
        //在header 中添加一个默认值
        ParameterBuilder tokenParam = new ParameterBuilder();
        tokenParam.name("Authorization")
                .defaultValue("")
                .description("Token令牌")
                .modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        List<Parameter> pars = new ArrayList<Parameter>();
        pars.add(tokenParam.build());
        return pars;
    }
}