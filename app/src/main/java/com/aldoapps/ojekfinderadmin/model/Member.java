package com.aldoapps.ojekfinderadmin.model;

/**
 * Created by user on 10/12/2015.
 */
public class Member {
    public Member(String _id, String userName, String avatar, float rating, String status) {
        this._id = _id;
        this.userName = userName;
        this.avatarUrl = avatar;
        this.rating = rating;
        this.status = status;
    }

    public Member() {

    }

    public String get_id() {

        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

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

    private String _id;
    private String userName;
    private String avatarUrl;
    private float rating;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
