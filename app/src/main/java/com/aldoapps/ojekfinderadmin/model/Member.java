package com.aldoapps.ojekfinderadmin.model;

import java.io.Serializable;

/**
 * Created by user on 10/12/2015.
 */
public class Member implements Serializable{
    private String objectId;
    private String userName;
    private String avatarUrl;
    private float rating;
    private String status;

    public Member() { }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatar) {
        this.avatarUrl = avatar;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
