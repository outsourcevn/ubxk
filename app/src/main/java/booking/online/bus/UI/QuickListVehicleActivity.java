package booking.online.bus.UI;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import booking.online.bus.Controller.ActiveVehicleAdapter;
import booking.online.bus.Models.ActiveBusModel;

import booking.online.bus.R;
import booking.online.bus.Utilities.BaseService;
import booking.online.bus.Utilities.Defines;
import booking.online.bus.Utilities.Utilites;
import booking.online.bus.Widget.GPSTracker;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuickListVehicleActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private RecyclerView vehicleView;
    private ArrayList<ActiveBusModel> vehicles;
    private Context mContext;
    private ImageView imgLoading;
    private String provinceFrom , provinceTo, vehicleType;
    private TextView txtProvinceFrom, txtProvinceTo, txtNoResult;
    private double longitude, latitude;
    private SwipeRefreshLayout swipeToRefresh;
    private boolean refreshToggle = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_list_vehicle);
        mContext = this;
        initComponents();

    }


    private void initComponents() {
        vehicleView                 =   (RecyclerView)          findViewById(R.id.vehicle_view);
        Toolbar toolbar             =   (Toolbar)               findViewById(R.id.toolbar);
        txtProvinceFrom             =   (TextView)              findViewById(R.id.txt_province_from);
        txtProvinceTo               =   (TextView)              findViewById(R.id.txt_province_to);
        txtNoResult                 =   (TextView)              findViewById(R.id.txt_no_result);
        imgLoading                  =   (ImageView)             findViewById(R.id.img_loading);
        swipeToRefresh              =   (SwipeRefreshLayout)    findViewById(R.id.swipe_view);

        swipeToRefresh.setOnRefreshListener(this);

        swipeToRefresh.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeToRefresh.setRefreshing(true);
                                        requestToGetListVehicle();
                                    }
                                }
        );
        AnimationDrawable frameAnimation = (AnimationDrawable) imgLoading.getBackground();
        frameAnimation.start();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            provinceFrom    = extras.getString(Defines.PROVINCE_FROM_ACTION);
            provinceTo      = extras.getString(Defines.PROVINCE_TO_ACTION);
            vehicleType     = extras.getString(Defines.VEHICLE_TYPE_ACTION);
        }
        txtProvinceFrom.setText(provinceFrom);
        txtProvinceTo.setText(provinceTo);

        // set cardview
        vehicleView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        vehicleView.setLayoutManager(llm);
        if (Utilites.isOnline(mContext)) {
            getCurrentLocation();
            requestToGetListVehicle();
        }else
            showOffline();

        // set Actionbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Xe đang hoạt động");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void getCurrentLocation() {
        GPSTracker gps = new GPSTracker(mContext);
        // check if GPS enabled
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        }

    }
    private void showOffline() {
        txtNoResult.setVisibility(View.VISIBLE);
        imgLoading.setVisibility(View.GONE);
        txtNoResult.setText("Không có kết nối mạng");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void requestToGetListVehicle() {
        RequestParams params;
        params = new RequestParams();
        params.put("from", provinceFrom);
        params.put("to", provinceTo);
        params.put("type", vehicleType);
        params.put("lon", longitude);
        params.put("lat", latitude);
        Log.i("params deleteDelivery", params.toString());
        vehicles = new ArrayList<>();
        swipeToRefresh.setRefreshing(true);
        BaseService.getHttpClient().post(Defines.URL_LIST_ONL_VEHICLE, params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                // called when response HTTP status is "200 OK"
                Log.i("JSON", new String(responseBody));
                try {
                    JSONArray data = new JSONArray(new String(responseBody));
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonobject = data.getJSONObject(i);
                        parseJsonResult(jsonobject);
                    }
                    if(vehicles.size()>0) {
                        ActiveVehicleAdapter adapter = new ActiveVehicleAdapter(mContext, vehicles);
                        vehicleView.setAdapter(adapter);
                        swipeToRefresh.setRefreshing(false);
                    }else{
                        txtNoResult.setVisibility(View.VISIBLE);
                        txtNoResult.setText("Không có xe nào cho tuyến này");
                        swipeToRefresh.setRefreshing(false);
                    }
                    imgLoading.setVisibility(View.GONE);
                    swipeToRefresh.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                //Toast.makeText(MainActivity.this, getResources().getString(R.string.check_network), Toast.LENGTH_SHORT).show();
                imgLoading.setVisibility(View.GONE);
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
                //Toast.makeText(MainActivity.this, getResources().getString(R.string.check_network), Toast.LENGTH_SHORT).show();
                imgLoading.setVisibility(View.GONE);
            }
        });
    }

    private void parseJsonResult(JSONObject jsonobject) {
        try {
            String from         = jsonobject.getString("F2");
            String to           = jsonobject.getString("F3");
            String type         = jsonobject.getString("F4");

            String name         = jsonobject.getString("F13");
            String telephone    = jsonobject.getString("phone_driver");
            String datetime     = jsonobject.getString("datetime");
            double distance     = jsonobject.getDouble("D");
            String start        = jsonobject.getString("start");
            String end2         = jsonobject.getString("end2");
            ActiveBusModel busInfor = new ActiveBusModel(name,from,to,type,distance,telephone,datetime,start,end2);
            vehicles.add(busInfor);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onRefresh() {
        requestToGetListVehicle();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
