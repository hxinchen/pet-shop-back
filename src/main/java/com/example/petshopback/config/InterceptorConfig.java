package com.example.petshopback.config;

import com.example.petshopback.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET","HEAD","POST","PUT","DELETE","OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry reg) {
        reg.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludePattern());
    }

    public List<String> excludePattern(){
        List<String> ret = new ArrayList<String>();
        ret.add("/user/login");
        ret.add("/user/register");
        ret.add("/user/validate");
        ret.add("/search/*");
        ret.add("/pet/getById");
        ret.add("/pet/getByCategory");
        ret.add("/product/*");
        ret.add("/review/get");
        ret.add("/petCategory/*");
        ret.add("/productCategory/*");
//        ret.add("/**");
//        ret.add("/favor/*");
        return ret;
    }

    /**
     * 资源映射到本地路径
     * @param registry
     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/book/**")
//                .addResourceLocations("file:D:/vue/bookstore_app/bookstore_app/public/image/");
//    }
}