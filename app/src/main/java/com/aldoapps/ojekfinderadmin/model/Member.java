package com.aldoapps.ojekfinderadmin.model;

/**
 * Created by user on 10/12/2015.
 */
public class Member {
    public Member(String _id, String userName, String avatar, float rating) {
        this._id = _id;
        this.userName = userName;
        this.avatar = avatar;
        this.rating = rating;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    private String _id;
    private String userName;
    private String avatar;
    private float rating;
}
