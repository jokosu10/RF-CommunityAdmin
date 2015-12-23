package com.aldoapps.ojekfinderadmin.model;

import android.text.TextUtils;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by user on 24/12/2015.
 */
@ParseClassName("UserCommunity")
public class UserCommunity extends ParseObject {
    private String userObjectId;
    private String communityObjectId;
    private String isActive;

    public UserCommunity(){}

    public String getUserObjectId() {
        return getString("userObjectId");
    }

    public void setUserObjectId(String userObjectId) {
        put("userObjectId", userObjectId);
    }

    public String getCommunityObjectId() {
        return getString("communityObjectId");
    }

    public void setCommunityObjectId(String communityObjectId) {
        put("communityObjectId", communityObjectId);
    }

    public String getIsActive() {
        return getString("isActive");
    }

    public void setIsActive(String isActive) {
        put("isActive", isActive);
    }

    public boolean isCurrentlyActive(){
        return (getIsActive().equalsIgnoreCase("yes"));
    }

    public boolean haveCommunity(){
        return (!TextUtils.isEmpty(getCommunityObjectId()));
    }

    public void setIsActiveToNo() {
        setIsActive("no");
    }

    public void setIsActiveToYes() {
        setIsActive("yes");
    }

    public static ParseQuery<UserCommunity> getQuery(){
        return ParseQuery.getQuery(UserCommunity.class);
    }
}

