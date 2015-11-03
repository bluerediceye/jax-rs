package com.learning.webservice.example.client;

import com.learning.webservice.example.model.Activity;
import com.learning.webservice.example.model.ActivitySearch;
import com.learning.webservice.example.model.ActivitySearchType;
import com.learning.webservice.example.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created on 02/11/2015
 *
 * @author Ming Li
 */
public class TestActivityClient {

    private ActivityClient client;
    private ActivitySearchClient searchClient;

    @Before
    public void setUp() throws Exception {
        client = new ActivityClient();
        searchClient = new ActivitySearchClient();
    }

    @Test
    public void testGet() throws Exception {
        Activity activity = client.get("22222");
        System.out.println(activity.getDescription());
        assertNotNull(activity);
    }

    @Test(expected = RuntimeException.class)
    public void testGet_ShortId() throws Exception {
        Activity activity = client.get("2");
        System.out.println(activity.getDescription());
        assertNotNull(activity);
    }

    @Test(expected = RuntimeException.class)
    public void testGet_NotFound() throws Exception {
        Activity activity = client.get("12");
        System.out.println(activity.getDescription());
        assertNotNull(activity);
    }

    @Test
    public void testGetList(){
        List<Activity> activities = client.getList();
        assertNotNull(activities);
    }

    @Test
    public void testCreate(){
        Activity activity = new Activity();
        activity.setDescription("Test description");
        activity.setDuration(120);
        User user = new User();
        activity.setUser(user);

        Activity response = client.create(activity);

        assertNotNull(response);
        assertEquals(activity.getDescription(), response.getDescription());
        assertEquals(activity.getDuration(), response.getDuration());
    }

    @Test
    public void testPut(){
        Activity activity = new Activity();
        activity.setId("3456");
        activity.setDuration(90);
        activity.setDescription("法克鱿");
        Activity response = client.update(activity);
        assertNotNull(response);
    }

    @Test
    public void testDelete(){
        client.delete("1234");
    }

    @Test
    public void testSearch(){
        client.delete("1234");

        String description = "hello";
        List<String> searchValues = new ArrayList<>();
        searchValues.add("hello");
        searchValues.add("he");

        String from = "durationFrom";
        String fromVal = "30";
        String to = "durationTo";
        String toVal = "100";

        List<Activity> activities = searchClient.search(description, searchValues);
        assertFalse(activities.isEmpty());
    }

    @Test
    public void testSearchObject(){
        List<String> searchValues = new ArrayList<>();
        searchValues.add("cycle");
        searchValues.add("car");
        searchValues.add("bus");

        ActivitySearch search = new ActivitySearch();
        search.setDescriptions(searchValues);
        search.setDurationFrom(100);
        search.setDurationTo(200);

        search.setSearchType(ActivitySearchType.SEARCH_BY_DESCRIPTION);

        List<Activity> activities = searchClient.search(search);

        assertFalse(activities.isEmpty());
    }

}