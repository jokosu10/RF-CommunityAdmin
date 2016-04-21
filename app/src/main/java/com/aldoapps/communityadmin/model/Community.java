package com.aldoapps.communityadmin.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by user on 24/12/2015.
 */
@ParseClassName("Community")
public class Community extends ParseObject {
    private String name;

    public Community(){}

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }

    public static ParseQuery<Community> getQuery(){
        return ParseQuery.getQuery(Community.class);
    }
}
