package com.example.petshopback.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName SwaggerConfig
 * @Description: TODO
 * @Author hahaha
 * @Date 6/6/23
 * @Version V1.0`
 **/
@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {
    @Bean
    public Docket exampleApi() {
        return new Docket(DocumentationType.SWAGGER_2) // v2 不同
                .groupName("example")
                .apiInfo(exampleApiInfo())
                .select()

                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.example.petshopback"))
                //所有的controller
                //.apis(RequestHandlerSelectors.any())
                .build();
    }

    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo exampleApiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("example模块 API")
                //版本号
                // .version("1.0")
                //描述
                // .description("API 描述")
                .build();
    }


    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 解决静态资源无法访问
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        // 解决swagger无法访问
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        // 解决swagger的js文件无法访问
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
