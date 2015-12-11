package com.aldoapps.ojekfinderadmin;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by user on 11/12/2015.
 */
public class CommunityAdminApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this);
    }
}
