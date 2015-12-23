package com.aldoapps.ojekfinderadmin;

import android.app.Application;

import com.aldoapps.ojekfinderadmin.model.Community;
import com.aldoapps.ojekfinderadmin.model.CommunityAdmin;
import com.aldoapps.ojekfinderadmin.model.UserCommunity;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by user on 11/12/2015.
 */
public class CommunityAdminApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(CommunityAdmin.class);
        ParseObject.registerSubclass(Community.class);
        ParseObject.registerSubclass(UserCommunity.class);
        Parse.initialize(this);
    }
}
