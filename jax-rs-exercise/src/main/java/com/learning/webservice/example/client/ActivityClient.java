package com.learning.webservice.example.client;

import com.learning.webservice.example.model.Activity;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created on 02/11/2015
 *
 * @author Ming Li
 */
public class ActivityClient {

    private Client client;

    public ActivityClient() {
        client = ClientBuilder.newClient();
    }

    public Activity get(String id) {
        WebTarget target = client.target("http://localhost:8080/webapi/");

        Response response = target.path("activities/" + id).request().get(Response.class);
        if(response.getStatus() != Response.Status.OK.getStatusCode()){
            throw  new RuntimeException("There is a error to retrieve activity " + id);
        }
        return response.readEntity(Activity.class);
    }

    public String getAsString(String id) {
        WebTarget target = client.target("http://localhost:8080/webapi/");
        return target.path("activities/" + id).request(MediaType.APPLICATION_XML).get(String.class);
    }

    public List<Activity> getList() {
        WebTarget target = client.target("http://localhost:8080/webapi");
        return target.path("activities").request(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<List<Activity>>() {
                });
    }

    public Activity create(Activity activity) {
        WebTarget target = client.target("http://localhost:8080/webapi/");
        Response response = target.path("activities/activity").request().post(Entity.entity(activity, MediaType.APPLICATION_JSON_TYPE));
        return response.readEntity(Activity.class);
    }

    public Activity update(Activity activity) {
        WebTarget target = client.target("http://localhost:8080/webapi/");
        Response response = target.path("activities/" + activity.getId()).request().put(Entity.entity(activity, MediaType.APPLICATION_JSON_TYPE));
        if(response.getStatus() != Response.Status.OK.getStatusCode()){
            throw  new RuntimeException("There is a error to update activity " + activity.getId());
        }
        return response.readEntity(Activity.class);

    }

    public void delete(String activityId) {
        WebTarget target = client.target("http://localhost:8080/webapi/");
        Response response = target.path("activities/" + activityId).request(MediaType.APPLICATION_JSON).delete();
        if(response.getStatus() != Response.Status.OK.getStatusCode()){
            throw  new RuntimeException("There is a error to update activity " + activityId);
        }
    }
}
