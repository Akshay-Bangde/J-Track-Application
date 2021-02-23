package com.example.trackinghub_basic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.trackinghub_basic.Adapter.Vehical_List_Adapter;
import com.example.trackinghub_basic.Model.Vehical_List_Model;
import com.example.trackinghub_basic.R;
import com.example.trackinghub_basic.utils.Constants;
import com.example.trackinghub_basic.utils.NetworkConnectivity;
import com.example.trackinghub_basic.utils.PreferenceUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.trackinghub_basic.utils.Constants.BaseURL;

public class Running_Vehicle_List_Activity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    Vehical_List_Adapter vehical_list_adapter;
    ArrayList<Vehical_List_Model> mVehicalListModel;
    SearchView searchView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    public static final String Vehicle_List_URL = BaseURL+"get_assets_last_point/";
    ProgressBar progressBar;
    String result,message, User_Code;
    private Vehical_List_Adapter.ItemClickListner itemClickListner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running__vehicle__list);

        FindViewById();
        User_Code = PreferenceUtils.getMessage(this);

        boolean networkConectivity = NetworkConnectivity.isConnected(Running_Vehicle_List_Activity.this);
        if (networkConectivity) {
            Running_Vehicle_list(Vehicle_List_URL + User_Code);
        }
        else {
            NetworkConnectivity.networkConnetivityShowDialog(Running_Vehicle_List_Activity.this);
        }
    }


    public void FindViewById() {
        mRecyclerView = findViewById(R.id.recyclerView);
        searchView = (SearchView) findViewById(R.id.searchView);
        progressBar = findViewById(R.id.pb);


        mVehicalListModel = new ArrayList<Vehical_List_Model>();
        // vehical_list_adapter = new Vehical_List_Adapter(Vehicle_List_Activity.this,mVehicalListModel);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        vehical_list_adapter = new Vehical_List_Adapter(mVehicalListModel,itemClickListner);
        mRecyclerView.setLayoutManager(layoutManager);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                vehical_list_adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                vehical_list_adapter.getFilter().filter(newText);
                return false;
            }
        });
    }


    // get vehicle list view
    public void Running_Vehicle_list(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url)
                .get()
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Toast.makeText(Running_Vehicle_List_Activity.this, "Request failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String myResponse = response.body().string();
                Running_Vehicle_List_Activity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        progressBar.setVisibility(View.GONE);
                        // mSwipeRefreshLayout.setRefreshing(false);
                        //JSONObject forecast = null;
                        try {
                            //   forecast = new JSONObject( myResponse);
                            JSONObject json = new JSONObject(myResponse);
                            result = json.getString("result");
                            message = json.getString("msg");

                            if (result.equals("true")){
                                JSONArray jsonArray = json.getJSONArray("assets");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String latitude = object.getString("lati");
                                    String longitutde = object.getString("longi");
                                    String address = object.getString("address");
                                    String date = object.getString("Date");
                                    String speed = object.getString("speed");
                                    String ignition = object.getString("ignition");
                                    String current_event = object.getString("current_event");
                                    String assets_name = object.getString("assets_name");
                                    String km_reading = object.getString("km_reading");
                                    String device_id = object.getString("device_id");

                                    Vehical_List_Model vehical_list_model = new Vehical_List_Model();

                                    mVehicalListModel = new ArrayList<>();
                                    mVehicalListModel = Constants.getAssets();

                                    for (int j = 0; j < Constants.getAssets().size(); j++)
                                        try {
                                            if (!Constants.getAssets().get(j).getSpeed().equals("0") /*&& Constants.getAssets().get(j).getIgnition().equals("1")*/) {
                                                vehical_list_model.setSpeed(speed);
                                                vehical_list_model.setVehical_number(assets_name);
                                                vehical_list_model.setAddress(address);
                                                vehical_list_model.setDate(date);

                                                mVehicalListModel.add(vehical_list_model);
                                                mRecyclerView.setAdapter(vehical_list_adapter);
                                                vehical_list_adapter.notifyDataSetChanged();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }








                                }

                            }



                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


    }
}