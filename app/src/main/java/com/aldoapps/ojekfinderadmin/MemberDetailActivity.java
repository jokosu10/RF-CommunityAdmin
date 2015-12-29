package com.aldoapps.ojekfinderadmin;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aldoapps.ojekfinderadmin.model.Member;
import com.aldoapps.ojekfinderadmin.model.ModelUserCommunity;
import com.aldoapps.ojekfinderadmin.model.UserCommunity;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

/**
 * Created by user on 09/12/2015.
 */
public class MemberDetailActivity extends AppCompatActivity{

    public static final String KEY_MEMBER = "key_member";
    private static final String TAG = MemberDetailActivity.class.getSimpleName();
    public Member mMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_member_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            mMember = (Member) extras.getSerializable(KEY_MEMBER);
            toolbar.setTitle(mMember.getUserName());
        }
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activateOrDeactivateUser();
            }
        });

        // show up button to navigate back to MainActivity
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void activateOrDeactivateUser() {
        ParseQuery<UserCommunity> query = UserCommunity.getQuery();
        query.whereEqualTo("userObjectId", mMember.getObjectId());
        GetCallback<UserCommunity> getCallback = new GetCallback<UserCommunity>() {
            @Override
            public void done(UserCommunity userCommunity, ParseException e) {
                if(e == null){
                    if(userCommunity != null){
                        updateUserCommunity(userCommunity);
                    }
                }else{
                    Log.d(TAG, e.getMessage());
                }
            }
        };
        query.getFirstInBackground(getCallback);
    }

    private void updateUserCommunity(UserCommunity userCommunity) {
        if(mMember.getStatus().equalsIgnoreCase("yes")){
            userCommunity.setIsActiveToNo();
        }else{
            userCommunity.setIsActiveToYes();
        }
        SaveCallback saveCallback = new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Toast.makeText(getApplicationContext(),
                            "Successfully updated user status!",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Log.d(TAG, e.getMessage());
                }
            }
        };
        userCommunity.saveEventually(saveCallback);
    }
}
