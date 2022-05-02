package com.wt.gpms.teacher.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileConfig implements WebMvcConfigurer {
    //这个配置类用来添加文件访问的访问路径映射规则
    //设置文件路径的映射，把文件写入到一个绝对路径下，然后设置映射来访问
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //需要添加映射的绝对路径（路径最后的/一定要加）
        String filePath = "D:\\codes\\resources\\gpms\\";
        //设置映射规则，源路径（ResourceLocations）被设置成可以通过映射路径（ip:port/files）来访问
        registry.addResourceHandler("/files/**").
                addResourceLocations("file:" + filePath);
        //例如，未映射时，访问上传的图片需要 localhost:8080/uploads/xxx.jpg （在static目录下放的uploads目录）
        //设置了映射后，直接 localhost:8080/files/xxx.jpg 就能访问到服务器的某个路径下的文件，不用放在static文件夹下
        //本项目还设置了一个访问前缀，访问需要加上前缀，如localhost:8080/teacher/files/xxx.jpg
    }
}
