package com.aldoapps.ojekfinderadmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.aldoapps.ojekfinderadmin.model.Member;
import com.aldoapps.ojekfinderadmin.model.UserC;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mListView;
    private ArrayList<Member> mMembers = new ArrayList<>();
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
        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> list, ParseException e) {
                mMembers.clear();
                for(ParseUser user : list){
                    Member member = new Member();
                    member.set_id(user.getObjectId());
                    member.setUserName(user.getUsername());
                    member.setStatus(user.getString(UserC.ACTIVATION_STATUS));
                    if(user.getParseFile(UserC.AVATAR) != null){
                        member.setAvatarUrl(user.getParseFile(UserC.AVATAR).getUrl());
                    }
                    mMembers.add(member);
                }

                mAdapter.notifyDataSetChanged();
            }
        });

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

        if (id == R.id.nav_member_unverified) {
            // Handle the camera action
        } else if (id == R.id.nav_member_verified) {

        } else if (id == R.id.nav_member_deleted) {

        } else if (id == R.id.nav_account_edit) {

        } else if (id == R.id.nav_account_log_out) {
            doLogOut();

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
