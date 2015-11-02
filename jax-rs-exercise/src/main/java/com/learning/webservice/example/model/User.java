package com.learning.webservice.example.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Ming.Li on 02/11/2015.
 *
 * @author Ming Li
 */
@XmlRootElement
public class User {

    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}