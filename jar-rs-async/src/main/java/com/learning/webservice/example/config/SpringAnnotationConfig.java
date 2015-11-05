package com.learning.webservice.example.config;

import com.learning.webservice.example.Application;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Ming.Li on 04/11/2015.
 *
 * @author Ming.Li
 */
@Configuration
@ComponentScan(basePackageClasses = Application.class)
public class SpringAnnotationConfig {
}
