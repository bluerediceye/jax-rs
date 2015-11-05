package com.learning.webservice.example.config;

import com.learning.webservice.example.Application;
import com.learning.webservice.example.config.mode.util.ModeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.MethodInvokingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

/**
 * Created by Ming.Li on 04/11/2015.
 *
 * @author Ming.Li
 */
@Configuration
@ComponentScan(basePackageClasses = Application.class)
@PropertySource("classpath:application.properties")
public class SpringAnnotationConfig {

    private Logger LOG = LoggerFactory.getLogger(SpringAnnotationConfig.class);

    @Autowired
    private Environment environment;

    @Value("${application.version}")
    private String applicationVersion;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }

    @PostConstruct
    public void displayApplicationVersion(){
        LOG.info("Application version: " + applicationVersion);
    }

    @Bean
    public static MethodInvokingBean initApplicationMode(Environment environment){
        MethodInvokingBean invokingBean = new MethodInvokingBean();
        invokingBean.setTargetClass(ModeUtils.class);
        invokingBean.setStaticMethod(ModeUtils.class.getName() + ".setEnvironment");
        invokingBean.setArguments(new Object[]{environment});
        return invokingBean;
    }
}
