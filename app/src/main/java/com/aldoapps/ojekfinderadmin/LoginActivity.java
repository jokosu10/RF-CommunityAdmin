package com.aldoapps.ojekfinderadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aldoapps.ojekfinderadmin.model.Community;
import com.aldoapps.ojekfinderadmin.model.CommunityAdmin;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by user on 11/12/2015.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
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

        ParseQuery<CommunityAdmin> query = CommunityAdmin.getQuery();
        query.whereEqualTo("email", userName);
        query.whereEqualTo("password", password);
        query.whereEqualTo("isActive", "yes");
        mProgressDialog.show();
        GetCallback<CommunityAdmin> getCallback = new GetCallback<CommunityAdmin>() {
            @Override
            public void done(CommunityAdmin communityAdmin, ParseException e) {
                mProgressDialog.dismiss();
                if (e == null) {
                    Toast.makeText(LoginActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                    saveCommunityAdminToLocalStorage(communityAdmin);
                } else {
                    Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, e.getMessage());
                }
            }
        };
        query.getFirstInBackground(getCallback);
    }

    private void saveCommunityAdminToLocalStorage(CommunityAdmin communityAdmin) {
        SharedPreferences preferences = getSharedPreferences(Constants.KEY_SHARED_PREFS,
                MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.KEY_ADMIN_OBJECT_ID, communityAdmin.getObjectId());
        editor.putBoolean(Constants.KEY_HAS_LOGIN, true);
        editor.apply();

        navigateToMainActivity();
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
