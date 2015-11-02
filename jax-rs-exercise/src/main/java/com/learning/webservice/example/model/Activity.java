package com.learning.webservice.example.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Ming.Li on 02/11/2015.
 *
 * @author Ming Li
 */
@XmlRootElement
public class Activity {

    private String id;
    private String description;
    private int duration;
    private User user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
