package com.example.trackinghub_basic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.trackinghub_basic.R;
import com.example.trackinghub_basic.utils.NetworkConnectivity;
import com.example.trackinghub_basic.utils.PreferenceUtils;

public class Profile_Activity extends AppCompatActivity {

    public Button mLogoutBtn;
    public ImageButton mBackBtn;
    public TextView mNametxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        FindViewById();
        ClickOnListner();


    }


    // Code for Find View By Id
    private void FindViewById() {
        mLogoutBtn = findViewById(R.id.btn_logout);
        mBackBtn = findViewById(R.id.backBtn);
        mNametxt = findViewById(R.id.name);
        mNametxt.setText(PreferenceUtils.getName(this));
    }

    //Code For conformation Logout customised alert dialog Box
    private void ClickOnListner() {

        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Profile_Activity.this);
                dialog.setContentView(R.layout.log_out_alert_dialog_box);
                TextView mesg = dialog.findViewById(R.id.message);
                mesg.setText("Do you want to logout ?");
                Button nobtn = dialog.findViewById(R.id.text_no);
                Button yesbtn = dialog.findViewById(R.id.text_yes);
                dialog.setCancelable(false);

                nobtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                yesbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Profile_Activity.this, Login_Activity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        PreferenceUtils.saveUserName("", Profile_Activity.this);
                        PreferenceUtils.savePassword("", Profile_Activity.this);
                        PreferenceUtils.saveName("", Profile_Activity.this);
                        PreferenceUtils.saveMessage("", Profile_Activity.this);

                        startActivity(intent);
                        finish();
                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });


        // Code For Back Button
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}