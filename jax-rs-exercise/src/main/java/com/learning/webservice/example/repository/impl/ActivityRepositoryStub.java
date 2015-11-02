package com.learning.webservice.example.repository.impl;

import com.learning.webservice.example.model.Activity;
import com.learning.webservice.example.model.User;
import com.learning.webservice.example.repository.ActivityRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ming.Li on 02/11/2015.
 *
 * @author Ming Li
 */
public class ActivityRepositoryStub implements ActivityRepository {

    @Override
    public List<Activity> findAllActivities() {
        List<Activity> activities = new ArrayList<>();

        Activity activity1 = new Activity();
        activity1.setDescription("Swimming");
        activity1.setDuration(45);

        activities.add(activity1);

        Activity activity2 = new Activity();
        activity2.setDescription("Cycling");
        activity2.setDuration(120);

        activities.add(activity2);

        return activities;
    }

    @Override
    public Activity findActivity(String activityId) {
        if("12".equals(activityId)){
            return null;
        }

        Activity activity1 = new Activity();
        activity1.setId("1234");
        activity1.setDescription("Swimming");
        activity1.setDuration(45);

        User user = new User();
        user.setId("123");
        user.setName("Ming");

        activity1.setUser(user);
        return activity1;
    }

    @Override
    public void create(Activity activity) {
        //todo should issue an insert statement to database.
    }

    @Override
    public void update(Activity activity) {
        //todo should issue an update statement to database.
    }
}
