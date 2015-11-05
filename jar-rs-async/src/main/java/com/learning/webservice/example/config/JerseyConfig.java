package com.learning.webservice.example.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.fasterxml.jackson.jaxrs.xml.JacksonJaxbXMLProvider;
import com.fasterxml.jackson.jaxrs.xml.JacksonXMLProvider;
import com.learning.webservice.example.Application;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.HttpMethodOverrideFilter;
import org.glassfish.jersey.server.filter.UriConnegFilter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;

/**
 * Created by Ming.Li on 03/11/2015.
 *
 * @author Ming.Li
 */
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.learning.webservice.example package

        JacksonXMLProvider xmlProvider = new JacksonJaxbXMLProvider()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        JacksonJsonProvider jsonProvider = new JacksonJaxbJsonProvider()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        HashMap<String, MediaType> mappings = new HashMap<>();
        mappings.put("xml", MediaType.APPLICATION_XML_TYPE);
        mappings.put("json", MediaType.APPLICATION_JSON_TYPE);

        packages(Application.class.getPackage().getName())
                .register(xmlProvider)
                .register(jsonProvider)
                .property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true)
                .register(HttpMethodOverrideFilter.class)
                .register(new UriConnegFilter(mappings, null))
                .property("contextConfig", new AnnotationConfigApplicationContext(SpringAnnotationConfig.class))
        ;
    }
}
