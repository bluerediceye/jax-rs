package com.learning.webservice.example.repository.impl;

import com.learning.webservice.example.model.Activity;
import com.learning.webservice.example.repository.ActivityRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ming.Li on 02/11/2015.
 */
public class ActivityRepositoryStub implements ActivityRepository {

    @Override
    public List<Activity> findAllActivity(){
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
}
