package com.aldoapps.communityadmin.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by user on 24/12/2015.
 */
@ParseClassName("CommunityAdmin")
public class CommunityAdmin extends ParseObject {
    private String name;
    private String password;
    private String isActive;
    private Community communityAdminCommunity;

    public CommunityAdmin(){}

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }

    public String getPassword() {
        return getString("password");
    }

    public void setPassword(String password) {
        put("password", password);
    }

    public String getIsActive() {
        return getString(isActive);
    }

    public void setIsActive(String isActive) {
        put("isActive", isActive);
    }

    public Community getCommunityAdminCommunity() {
        return (Community) getParseObject("communityAdminCommunity");
    }

    public void setCommunityAdminCommunity(Community communityAdminCommunity) {
        put("communityAdminCommunity", communityAdminCommunity);
    }

    public static ParseQuery<CommunityAdmin> getQuery(){
        return ParseQuery.getQuery(CommunityAdmin.class);
    }
}
