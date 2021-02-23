package com.example.trackinghub_basic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.trackinghub_basic.R;
import com.example.trackinghub_basic.utils.PreferenceUtils;

public class Splash_Activity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;
    public static String mUsername, mName, mPassword, mCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler();


    }

    // Code For Splash screen and shared preference code for login
    public void Handler() {
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after 2 seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                mUsername = PreferenceUtils.getUserName(Splash_Activity.this);
                mName = PreferenceUtils.getName(Splash_Activity.this);
                mPassword = PreferenceUtils.getPassword(Splash_Activity.this);
                mCode = PreferenceUtils.getMessage(Splash_Activity.this);

                if (mUsername == null || mUsername.equals("") && mName == null || mName.equals("")
                        && mPassword == null || mPassword.equals("")
                        && mCode == null || mCode.equals("")) {
                    Intent SignIN = new Intent(Splash_Activity.this, Login_Activity.class);
                    SignIN.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(SignIN);
                    finish();

                } else {
                    Intent intent = new Intent(Splash_Activity.this, Home_Activity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}