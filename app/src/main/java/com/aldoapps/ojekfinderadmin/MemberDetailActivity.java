package com.aldoapps.ojekfinderadmin;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by user on 09/12/2015.
 */
public class MemberDetailActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_member_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            toolbar.setTitle(extras.getString("asdf"));
        }
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // show up button to navigate back to MainActivity
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }
}
