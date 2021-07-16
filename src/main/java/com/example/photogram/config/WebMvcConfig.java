package com.example.photogram.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private String uploadPath = "/Users/cho/Documents/upload/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        registry
                .addResourceHandler("/upload/*")
                .addResourceLocations("file:///"+uploadPath)
                .setCachePeriod(60*10*6)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
}
