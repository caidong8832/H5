package flybear.hziee.app.conf;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import flybear.hziee.app.interceptor.CoreInterceptor;
import flybear.hziee.core.base.BaseInterceptor;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	@Value("${FILE_REPOSITORY}")
	private String FILE_REPOSITORY;
	
	@Value("${FILE_PATH_PATTERN}")
	private String FILE_PATH_PATTERN;
	

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("/static/**").addResourceLocations("/static/");
       // String FILE_REPOSITORY = UploadUtils.getConfig("FILE_REPOSITORY");
        if(!FILE_REPOSITORY.endsWith(File.separator))
        	FILE_REPOSITORY += File.separator;
        registry.addResourceHandler(FILE_PATH_PATTERN+"/**").addResourceLocations("file:"+FILE_REPOSITORY);
        super.addResourceHandlers(registry);
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new BaseInterceptor()).addPathPatterns("/**");  
        registry.addInterceptor(new CoreInterceptor()).addPathPatterns("/**").excludePathPatterns("/public","/static",FILE_PATH_PATTERN);
        super.addInterceptors(registry);
    }
}
