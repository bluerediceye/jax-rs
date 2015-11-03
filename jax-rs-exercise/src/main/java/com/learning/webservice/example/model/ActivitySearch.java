package com.learning.webservice.example.model;

import java.util.List;

/**
 * Created by Ming.Li on 03/11/2015.
 *
 * @author Ming.Li
 */
public class ActivitySearch {

    private ActivitySearchType searchType;

    private int durationFrom;

    private int durationTo;

    private List<String> descriptions;

    public int getDurationFrom() {
        return durationFrom;
    }

    public void setDurationFrom(int durationFrom) {
        this.durationFrom = durationFrom;
    }

    public int getDurationTo() {
        return durationTo;
    }

    public void setDurationTo(int durationTo) {
        this.durationTo = durationTo;
    }

    public List<String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<String> descriptions) {
        this.descriptions = descriptions;
    }

    public ActivitySearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(ActivitySearchType searchType) {
        this.searchType = searchType;
    }

    @Override
    public String toString() {
        return "ActivitySearch{" +
                "searchType=" + searchType +
                ", durationFrom=" + durationFrom +
                ", durationTo=" + durationTo +
                ", descriptions=" + descriptions +
                '}';
    }
}
