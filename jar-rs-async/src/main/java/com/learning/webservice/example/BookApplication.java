/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.learning.webservice.example;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by Ming.Li on 03/11/2015.
 *
 * @author Ming.Li
 */
public class BookApplication extends ResourceConfig {

    public BookApplication(final BookDao bookDao) {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.learning.webservice.example package
        JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
        provider.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        packages("com.learning.webservice.example")
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(bookDao).to(BookDao.class);
                    }
                })
                .register(provider);
        ;
    }
}
