package com.learning.webservice.example;

import com.learning.webservice.example.repository.BookDao;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.net.URI;

/**
 * Created by Ming.Li on 04/11/2015.
 *
 * @author Ming.Li
 */
@Configuration
public class ApplicationConfig {

    public static final String BASE_URI = "http://localhost:8080/api/";

    @Bean
    public BookDao bookDao(){
        return new BookDao();
    }

    @Bean
    public ResourceConfig resourceConfig(BookDao bookDao){
        return new Application(bookDao);
    }

    @Bean
    public HttpServer httpServer(){
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), resourceConfig(bookDao()));
    }

    @PreDestroy
    public void shutDown(){
        httpServer().shutdownNow();
    }
}
