package com.learning.webservice.example.client;

import com.learning.webservice.example.model.Activity;
import com.learning.webservice.example.model.ActivitySearch;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

/**
 * Created by Ming.Li on 03/11/2015.
 *
 * @author Ming.Li
 */
public class ActivitySearchClient {

    private Client client;

    public ActivitySearchClient(){
        client = ClientBuilder.newClient();
    }

    public List<Activity> search(String param, List<String> searchValues){
        URI uri = UriBuilder.fromUri("http://localhost:8080/webapi").path("search/activities")
                .queryParam(param, searchValues).build();

        WebTarget target = client.target(uri);

        List<Activity> response = target.request(MediaType.APPLICATION_JSON_TYPE).get(new GenericType<List<Activity>>(){});

        System.out.println(response);
        return response;
    }

    public List<Activity> search(ActivitySearch search){
        URI uri = UriBuilder.fromUri("http://localhost:8080/webapi").path("search/activities").build();
        WebTarget target = client.target(uri);
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(search, MediaType.APPLICATION_JSON_TYPE));

        if(response.getStatus() != 200){
            throw new RuntimeException("There was an error when searching");
        }

        return response.readEntity(new GenericType<List<Activity>>(){});
    }
}
