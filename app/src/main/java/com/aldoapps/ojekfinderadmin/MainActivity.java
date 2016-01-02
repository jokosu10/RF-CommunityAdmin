package com.aldoapps.ojekfinderadmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.aldoapps.ojekfinderadmin.model.Community;
import com.aldoapps.ojekfinderadmin.model.CommunityAdmin;
import com.aldoapps.ojekfinderadmin.model.Member;
import com.aldoapps.ojekfinderadmin.model.ModelUserCommunity;
import com.aldoapps.ojekfinderadmin.model.UserC;
import com.aldoapps.ojekfinderadmin.model.UserCommunity;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mListView;
    private ArrayList<Member> mMembers = new ArrayList<>();
    private ArrayList<ModelUserCommunity> mUserComms = new ArrayList<>();
    private MemberItemViewAdapter mAdapter;
    private SwipeRefreshLayout mRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences(
                Constants.KEY_SHARED_PREFS, MODE_PRIVATE
        );

        if(!sharedPreferences.getBoolean(Constants.KEY_HAS_LOGIN, false)){
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_main);
        mListView = (RecyclerView) findViewById(R.id.item_list);
        mRefresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadParseUsers();
                mRefresh.setRefreshing(false);
            }
        });
        mAdapter = new MemberItemViewAdapter(mMembers);
        mListView.setAdapter(mAdapter);

        loadParseUsers();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void loadParseUsers() {
        SharedPreferences preferences = getSharedPreferences(Constants.KEY_SHARED_PREFS,
                MODE_PRIVATE);
        String adminObjectId = preferences.getString(Constants.KEY_ADMIN_OBJECT_ID, "");
        ParseQuery<CommunityAdmin> adminQuery = CommunityAdmin.getQuery();
        adminQuery.include("communityAdminCommunity");
        adminQuery.whereEqualTo("objectId", adminObjectId);
        GetCallback<CommunityAdmin> getCallback = new GetCallback<CommunityAdmin>() {
            @Override
            public void done(CommunityAdmin communityAdmin, ParseException e) {
                if (e == null) {
                    loadUserCommunity(communityAdmin);
                } else {
                    Log.d(TAG, e.getMessage());
                }
            }
        };
        adminQuery.getFirstInBackground(getCallback);
    }

    private void loadUserCommunity(CommunityAdmin communityAdmin) {
        ParseQuery<UserCommunity> query = UserCommunity.getQuery();
        query.whereEqualTo("communityObjectId", communityAdmin
                .getCommunityAdminCommunity().getObjectId());
        FindCallback<UserCommunity> findCallback = new FindCallback<UserCommunity>() {
            @Override
            public void done(List<UserCommunity> list, ParseException e) {
                if (e == null) {
                    mUserComms.clear();
                    for (UserCommunity userCommunity : list) {
                        ModelUserCommunity model = new ModelUserCommunity();
                        model.setObjectId(userCommunity.getObjectId());
                        model.setUserObjectId(userCommunity.getUserObjectId());
                        model.setCommunityObjectId(userCommunity.getCommunityObjectId());
                        model.setIsActive(userCommunity.getIsActive());
                        mUserComms.add(model);
                    }
                    filterActualUsers();
                } else {
                    Log.d(TAG, e.getMessage());
                }
            }
        };
        query.findInBackground(findCallback);
    }

    private void filterActualUsers() {
        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        FindCallback<ParseUser> findCallback = new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> list, ParseException e) {
                mMembers.clear();
                for (ParseUser user : list) {
                    for (ModelUserCommunity model : mUserComms) {
                        if (model.getUserObjectId().equals(user.getObjectId())) {
                            Member member = new Member();
                            member.setObjectId(user.getObjectId());
                            member.setUserName(user.getUsername());
                            member.setStatus(model.getIsActive());
                            member.setDisplayName(user.getString("displayName"));
                            member.setPhoneNumber(user.getString("phoneNumber"));
                            if (user.getParseFile(UserC.AVATAR) != null) {
                                member.setAvatarUrl(user.getParseFile(UserC.AVATAR).getUrl());
                            }
                            mMembers.add(member);
                        }
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        };
        parseQuery.findInBackground(findCallback);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_member_unverified) {
//            // Handle the camera action
//        } else if (id == R.id.nav_member_verified) {
//
//        } else if (id == R.id.nav_member_deleted) {
//
//        } else if (id == R.id.nav_account_edit) {
//
//        } else if (id == R.id.nav_account_log_out) {
//            doLogOut();
//
//        }

        switch (id){
            case R.id.nav_account_edit:
                break;
            case R.id.nav_account_log_out:
                doLogOut();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void doLogOut() {
        SharedPreferences preferences = getSharedPreferences(Constants.KEY_SHARED_PREFS,
                MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Constants.KEY_HAS_LOGIN, false);
        editor.apply();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
