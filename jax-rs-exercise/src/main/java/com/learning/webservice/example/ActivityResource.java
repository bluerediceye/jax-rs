package com.learning.webservice.example;

import com.learning.webservice.example.model.Activity;
import com.learning.webservice.example.repository.ActivityRepository;
import com.learning.webservice.example.repository.impl.ActivityRepositoryStub;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Ming.Li on 02/11/2015.
 */

@Path("activities")
public class ActivityResource {

    private ActivityRepository activityRepository = new ActivityRepositoryStub();

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Activity> getAllActivities(){
        return activityRepository.findAllActivity();
    }
}
