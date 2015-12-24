package com.aldoapps.ojekfinderadmin.model;

/**
 * Created by user on 24/12/2015.
 */
public class ModelUserCommunity {
    private String objectId;
    private String userObjectId;
    private String communityObjectId;
    private String isActive;

    public ModelUserCommunity(){}

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUserObjectId() {
        return userObjectId;
    }

    public void setUserObjectId(String userObjectId) {
        this.userObjectId = userObjectId;
    }

    public String getCommunityObjectId() {
        return communityObjectId;
    }

    public void setCommunityObjectId(String communityObjectId) {
        this.communityObjectId = communityObjectId;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
