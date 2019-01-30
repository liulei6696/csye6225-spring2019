package edu.neu.coe.csye6225.config;


import edu.neu.coe.csye6225.config.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {


    private LoginInterceptor loginInterceptor;


    @Autowired
    public void setLoginInterceptor(LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }

    //this method is to configure static resources such as html, js, css, etc..
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    }


    //this method is to register interceptors. All interceptors need to be registered here to be effective
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns("/**") means intercept all requests，
        // excludePathPatterns("/login", "/register")excludes login and register pasge
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/login", "/register");


    }
}

