package com.aldoapps.communityadmin;

import android.app.Application;

import com.aldoapps.communityadmin.model.Community;
import com.aldoapps.communityadmin.model.CommunityAdmin;
import com.aldoapps.communityadmin.model.UserCommunity;
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
