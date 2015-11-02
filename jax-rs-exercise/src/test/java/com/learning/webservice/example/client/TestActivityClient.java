package com.learning.webservice.example.client;

import com.learning.webservice.example.model.Activity;
import com.learning.webservice.example.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created on 02/11/2015
 *
 * @author Ming Li
 */
public class TestActivityClient {

    private ActivityClient client;

    @Before
    public void setUp() throws Exception {
        client = new ActivityClient();
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
}