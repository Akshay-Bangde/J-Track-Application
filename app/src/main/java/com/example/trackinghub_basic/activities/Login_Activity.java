package com.example.trackinghub_basic.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trackinghub_basic.R;
import com.example.trackinghub_basic.utils.NetworkConnectivity;
import com.example.trackinghub_basic.utils.PreferenceUtils;
import com.google.android.gms.location.FusedLocationProviderClient;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.trackinghub_basic.utils.Constants.BaseURL;

public class Login_Activity extends AppCompatActivity implements TextWatcher, CompoundButton.OnCheckedChangeListener {

    public EditText mUserName, mPassword;
    public Button mLoginBtn;
    public ProgressBar progressBar;
    public CheckBox mRememberMeCheckBox;
    public SharedPreferences mSharedPreferences;
    public SharedPreferences.Editor mEditor;
    private static final String PREF_NAME = "prefx";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    public static String jFname;
    public static String jLname;
    public static String result;
    public String Uname, Pass, Message;
    final String Login_URL = BaseURL + "login/";

    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FindViewById();
        RememberMe();
        ClickOnListner();

    }


    // FindViewById for login Layout
    public void FindViewById() {
        mUserName = findViewById(R.id.userName);
        mPassword = findViewById(R.id.password);
        mLoginBtn = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.pb);
    }


    //Validation for all edit Text
    public Boolean Validation() {
        String name = mUserName.getText().toString();
        String pass = mPassword.getText().toString();

        Boolean flag = true;
        if (name.isEmpty() && pass.isEmpty()) {
            flag = false;

            Toast.makeText(Login_Activity.this, R.string.plese_enter_the_credential, Toast.LENGTH_SHORT).show();

        }

        return flag;
    }

    //Coding for remember_me checkbox

    // Code for Remember Me checkbox
    public void RememberMe() {
        mSharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mRememberMeCheckBox = findViewById(R.id.remember_me_checkBox);

        if (mSharedPreferences.getBoolean(KEY_REMEMBER, false))
            mRememberMeCheckBox.setChecked(true);
        else mRememberMeCheckBox.setChecked(false);

        mUserName.setText(mSharedPreferences.getString(KEY_USERNAME, ""));
        mPassword.setText(mSharedPreferences.getString(KEY_PASSWORD, ""));

        mUserName.addTextChangedListener(Login_Activity.this);
        mPassword.addTextChangedListener(Login_Activity.this);
        mRememberMeCheckBox.setOnCheckedChangeListener(Login_Activity.this);
    }


    // Coding For Login Button Click Listner
    public void ClickOnListner() {
        mLoginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean networkConectivity = NetworkConnectivity.isConnected(Login_Activity.this);
                if (networkConectivity) {
                    if (Validation()) {
                        progressBar.setVisibility(View.VISIBLE);
                        mLoginBtn.setVisibility(View.GONE);
                        String username = mUserName.getText().toString();
                        String password = mPassword.getText().toString();

                        PostHandler handler = new PostHandler(username, password);

                        String result = null;

                        try {
                            result = handler.execute(Login_URL).get();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }


                    }
                } else {
                    NetworkConnectivity.networkConnetivityShowDialog(Login_Activity.this);

                }

            }


        });
    }

    // Code for Login API Call
    public class PostHandler extends AsyncTask<String, Void, String> {
        OkHttpClient client = new OkHttpClient();
        String username, password;

        public PostHandler(String user, String password) {
            this.username = user;
            this.password = password;
        }

        @Override
        protected String doInBackground(String... params) {
            RequestBody formBody = new FormBody.Builder()
                    .add("username", username)
                    .add("password", password)
                    .build();

            Request request = new Request.Builder()
                    .url(Login_URL)
                    .post(formBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    final String myResponse = response.body().string();
                    Login_Activity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject forecast = null;
                            try {
                                forecast = new JSONObject(myResponse);
                                progressBar.setVisibility(View.GONE);
                                mLoginBtn.setVisibility(View.VISIBLE);


                                JSONObject json = new JSONObject(myResponse);
                                // JSONObject jsonObjectuser = forecast.getJSONObject("");
                                result = json.getString("result");
                                jFname = json.getString("first_name");
                                jLname = json.getString("last_name");

                                Message = forecast.getString("msg");

                                if (result == "true") {
                                    Intent intent = new Intent(Login_Activity.this, Home_Activity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                    //Save user info in shared perference
                                    String Name = jFname + jLname;
                                    Uname = mUserName.getText().toString();
                                    Pass = mPassword.getText().toString();


                                    PreferenceUtils.saveUserName(Uname, Login_Activity.this);
                                    PreferenceUtils.savePassword(Pass, Login_Activity.this);
                                    PreferenceUtils.saveName(Name, Login_Activity.this);
                                    PreferenceUtils.saveMessage(Message, Login_Activity.this);

                                    //intent.putExtra("firstName",jFname);
                                    // intent.putExtra("lastName",jLname);
                                    // intent.putExtra("message",Message);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(Login_Activity.this, "Please enter the valid credentials.", Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }


                    });


                }
            });

            return null;
        }

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (mRememberMeCheckBox.isChecked()) {
            mEditor.putString(KEY_USERNAME, mUserName.getText().toString().trim());
            mEditor.putString(KEY_PASSWORD, mPassword.getText().toString().trim());
            mEditor.putBoolean(KEY_REMEMBER, true);
            mEditor.apply();
        } else {
            mEditor.putBoolean(KEY_REMEMBER, false);
            mEditor.remove(KEY_PASSWORD);
            mEditor.remove(KEY_USERNAME);
            mEditor.apply();
        }

    }

}