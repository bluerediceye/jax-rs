package com.learning.webservice.example;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.xml.JacksonJaxbXMLProvider;
import com.fasterxml.jackson.jaxrs.xml.JacksonXMLProvider;
import com.learning.webservice.example.repository.BookDao;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.HttpMethodOverrideFilter;
import org.glassfish.jersey.server.filter.UriConnegFilter;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;

/**
 * Created by Ming.Li on 03/11/2015.
 *
 * @author Ming.Li
 */
public class Application extends ResourceConfig {

    public Application(final BookDao bookDao) {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.learning.webservice.example package

        JacksonXMLProvider xmlProvider = new JacksonJaxbXMLProvider()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        HashMap<String, MediaType> mappings = new HashMap<>();
        mappings.put("xml", MediaType.APPLICATION_XML_TYPE);
        mappings.put("json", MediaType.APPLICATION_JSON_TYPE);

        packages("com.learning.webservice.example")
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(bookDao).to(BookDao.class);
                    }
                }).
                register(xmlProvider).
                property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true).
                register(HttpMethodOverrideFilter.class).
                register(new UriConnegFilter(mappings, null));
    }
}
