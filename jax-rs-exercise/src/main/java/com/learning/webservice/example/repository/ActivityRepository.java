package com.learning.webservice.example.repository;

import com.learning.webservice.example.model.Activity;

import java.util.List;

/**
 * Created by Ming.Li on 02/11/2015.
 */
public interface ActivityRepository {
    List<Activity> findAllActivities();

    Activity findActivity(String activityId);
}
