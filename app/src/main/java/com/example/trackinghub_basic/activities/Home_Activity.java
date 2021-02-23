package com.example.trackinghub_basic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.trackinghub_basic.R;
import com.example.trackinghub_basic.utils.PreferenceUtils;

import java.util.Date;

public class Home_Activity extends AppCompatActivity {
    public ImageButton mBackBtn;
    public ImageView mProfileImage;
    public TextView mMessage, mUsername;
    public LinearLayout mTotal, mRunning, mParked, mOutOfNetwork;
    public long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FindViewById();
        ClickOnListner();
        getTimeFromAndroid();


    }


    //Find View By id
    private void FindViewById() {
        mBackBtn = findViewById(R.id.backBtn);
        mProfileImage = findViewById(R.id.profile_imageView);
        mMessage = findViewById(R.id.message);
        mUsername = findViewById(R.id.uName);
        mTotal = findViewById(R.id.Total);
        mRunning = findViewById(R.id.Runnning);
        mParked = findViewById(R.id.Parked);
        mOutOfNetwork = findViewById(R.id.Out_Of_Network);


    }

    //Login Button Click Listner
    private void ClickOnListner() {
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Activity.this, Profile_Activity.class);
                startActivity(intent);

            }
        });

        mTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Activity.this, Vehicle_List_Activity.class);
                startActivity(intent);
            }
        });

        mRunning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Activity.this, Running_Vehicle_List_Activity.class);
                startActivity(intent);
            }
        });
        mParked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  All_Vehical_List();
            }
        });
        mOutOfNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  All_Vehical_List();
            }
        });
    }

    // Code For Good Morning Message
    private void getTimeFromAndroid() {
        Date dt = new Date();
        int hours = dt.getHours();
        int min = dt.getMinutes();
        mUsername.setText(PreferenceUtils.getName(this));

        if (hours >= 1 && hours <= 12) {
            mMessage.setText("Good Morning,");
        } else if (hours >= 12 && hours <= 17) {
            mMessage.setText("Good Afternoon,");
        } else if (hours >= 17 && hours <= 21) {
            mMessage.setText("Good Evening,");
        } else if (hours >= 21 || hours <= 24) {
            mMessage.setText("Good Night,");
        }
    }


    // Code For splash screen for 2 seconds
    @Override
    public void onBackPressed() {

        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), getResources().getString(R.string.Press_once_again_to_exit), Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }
}