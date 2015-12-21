package com.aldoapps.ojekfinderadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by user on 11/12/2015.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText mUsername;
    private EditText mPassword;
    private Button mLoginBtn;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.please_wait));
        mUsername = (EditText) findViewById(R.id.username_text);
        mPassword = (EditText) findViewById(R.id.password_text);
        mLoginBtn = (Button) findViewById(R.id.login_btn);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });

    }

    private void doLogin() {
        String userName = mUsername.getText().toString();
        String password = mPassword.getText().toString();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("CommunityAdmin");
        query.whereEqualTo("email", userName);
        query.whereEqualTo("password", password);
        query.whereEqualTo("isActive", "yes");
        mProgressDialog.show();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                mProgressDialog.dismiss();
                if (objects.size() == 1) {
                    Toast.makeText(LoginActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                    navigateToMainActivity();
                } else {
                    Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void navigateToMainActivity() {
        // before going to MainActivity we need to modify shared preference
        SharedPreferences preferences = getSharedPreferences(Constants.KEY_SHARED_PREFS,
                MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Constants.KEY_HAS_LOGIN, true);
        editor.apply();

        // go to MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(mProgressDialog != null){
            mProgressDialog.dismiss();
        }
    }
}
