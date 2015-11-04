package com.learning.webservice.example.config;

import com.learning.webservice.example.Application;
import com.learning.webservice.example.repository.BookDao;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.net.URI;

/**
 * Created by Ming.Li on 04/11/2015.
 *
 * @author Ming.Li
 */
@Configuration
@ComponentScan(basePackageClasses = Application.class)
public class SpringAnnotationConfig {
}
