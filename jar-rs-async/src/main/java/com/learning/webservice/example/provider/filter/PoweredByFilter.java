/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.learning.webservice.example.provider.filter;

import com.learning.webservice.example.annotation.PoweredBy;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.annotation.Annotation;

/**
 * Created by Ming.Li on 04/11/2015.
 *
 * @author Ming.Li
 */
@Provider
@PoweredBy
public class PoweredByFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {

        for(Annotation annotation : responseContext.getEntityAnnotations()){
            if(annotation.annotationType() == PoweredBy.class){
                String value =((PoweredBy) annotation).value();
                responseContext.getHeaders().add("X-Powered-By", value);
            }
        }
    }
}
