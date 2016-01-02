package com.aldoapps.ojekfinderadmin;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aldoapps.ojekfinderadmin.model.Community;
import com.aldoapps.ojekfinderadmin.model.CommunityAdmin;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by user on 02/01/2016.
 */
public class EditCommunityActivity extends AppCompatActivity{

    private static final String TAG = EditCommunityActivity.class.getSimpleName();
    @Bind(R.id.community_name) EditText mCommunityName;
    @Bind(R.id.ok_btn) Button mButton;
    @Bind(R.id.toolbar) Toolbar mToolbar;

    ProgressDialog mProgressDialog;
    private CommunityAdmin mCommunityAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.please_wait));

        setContentView(R.layout.activity_edit_community);
        ButterKnife.bind(this);

        initToolbar();

        findExistingCommunity();

        mCommunityName.setText("");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCommunity();
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.edit_community);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    private void findExistingCommunity() {
        SharedPreferences preferences = getSharedPreferences(Constants.KEY_SHARED_PREFS,
                MODE_PRIVATE);
        String objectId = preferences.getString(Constants.KEY_ADMIN_OBJECT_ID, "");
        ParseQuery<CommunityAdmin> query = CommunityAdmin.getQuery();
        query.include("communityAdminCommunity");
        mProgressDialog.show();
        GetCallback<CommunityAdmin> getCallback = new GetCallback<CommunityAdmin>() {
            @Override
            public void done(CommunityAdmin communityAdmin, ParseException e) {
                mProgressDialog.dismiss();
                if(e == null){
                    mCommunityName.setText(communityAdmin
                            .getCommunityAdminCommunity().getName());
                    mCommunityAdmin = communityAdmin;
                }else{
                    Log.d(TAG, e.getMessage());
                }
            }
        };
        query.getInBackground(objectId, getCallback);
    }

    private void editCommunity() {
        ParseQuery<Community> query = Community.getQuery();
        mProgressDialog.show();
        final SaveCallback saveCallback = new SaveCallback() {
            @Override
            public void done(ParseException e) {
                mProgressDialog.dismiss();
                if(e == null){
                    finish();
                    Toast.makeText(EditCommunityActivity.this,
                            "Successfully saved community", Toast.LENGTH_SHORT).show();
                }else{
                    Log.d(TAG, e.getMessage());
                }
            }
        };

        GetCallback<Community> getCallback = new GetCallback<Community>() {
            @Override
            public void done(Community community, ParseException e) {
                mProgressDialog.dismiss();
                if(e == null){
                    community.setName(mCommunityName.getText().toString());
                    mProgressDialog.show();
                    community.saveEventually(saveCallback);
                }else{
                    Log.d(TAG, e.getMessage());
                }
            }
        };

        query.getInBackground(mCommunityAdmin.getCommunityAdminCommunity()
                .getObjectId(), getCallback);
    }
}
