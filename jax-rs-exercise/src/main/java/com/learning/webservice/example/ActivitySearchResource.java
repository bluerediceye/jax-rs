package com.learning.webservice.example;

import com.learning.webservice.example.model.Activity;
import com.learning.webservice.example.model.ActivitySearch;
import com.learning.webservice.example.repository.ActivityRepository;
import com.learning.webservice.example.repository.impl.ActivityRepositoryStub;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ming.Li on 03/11/2015.
 *
 * @author Ming.Li
 */
@Path("search/activities")
public class ActivitySearchResource {


    private ActivityRepository activityRepository = new ActivityRepositoryStub();

    @GET
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML })
    @Consumes(MediaType.APPLICATION_JSON)
    public Response searchForActivities(@QueryParam(value = "description")List<String> descriptions){
        System.out.println(descriptions);

        List<Activity> activities = activityRepository.findByDescription();

        if(activities == null || activities.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(new GenericEntity<List<Activity>>(activities){}).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML })
    @Consumes(MediaType.APPLICATION_JSON)
    public Response search(ActivitySearch search){
        System.out.println(search);
        List<Activity> activities = activityRepository.findByDescription();

        if(activities == null || activities.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(new GenericEntity<List<Activity>>(activities){}).build();
    }
}
