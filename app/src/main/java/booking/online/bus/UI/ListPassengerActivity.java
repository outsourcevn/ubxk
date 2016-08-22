package booking.online.bus.UI;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.joda.time.Instant;
import org.joda.time.Interval;

import java.util.ArrayList;

import booking.online.bus.Controller.PassengerAdapter;
import booking.online.bus.Models.PassengerModel;
import booking.online.bus.R;
import booking.online.bus.Receiver.NotificationAcceptReceiver;
import booking.online.bus.Utilities.BaseService;
import booking.online.bus.Utilities.Defines;
import booking.online.bus.Utilities.SharePreference;
import booking.online.bus.Utilities.Utilites;
import booking.online.bus.Widget.GPSTracker;
import booking.online.bus.database.LocalVideoDataSource;
import se.emilsjolander.stickylistheaders.ExpandableStickyListHeadersListView;

public class ListPassengerActivity extends AppCompatActivity {

    private double longitude , latitude;
    private SharePreference preference;
    private LocalVideoDataSource database;
    private ArrayList<PassengerModel> passengers;
    private Context mContext;
    private ExpandableStickyListHeadersListView passengerView;
    private LinearLayout layoutPassenger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_passenger);
        mContext = this;
        preference = new SharePreference(this);
        database = new LocalVideoDataSource(this);

        passengerView   = (ExpandableStickyListHeadersListView) findViewById(R.id.passenger_view);
        layoutPassenger = (LinearLayout)    findViewById(R.id.layout_no_passenger);
        Toolbar toolbar = (Toolbar)           findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Danh sách hành khách");

        passengers = database.getAllPassenger();
        if (passengers.size()<=0){
            layoutPassenger.setVisibility(View.VISIBLE);
        }else {
            PassengerAdapter adapter = new PassengerAdapter(mContext, passengers, database);
            passengerView.setAdapter(adapter);
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, new IntentFilter("tokenReceiver"));
    }
    BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            if(message != null)
            {
                passengers = database.getAllPassenger();
                if (passengers.size()<=0) {
                    layoutPassenger.setVisibility(View.VISIBLE);
                }else {
                    layoutPassenger.setVisibility(View.GONE);
                    PassengerAdapter adapter = new PassengerAdapter(mContext, passengers, database);
                    passengerView.setAdapter(adapter);
                }
            }
            String accept = intent.getStringExtra("accept");
            String deny = intent.getStringExtra("deny");
            if (accept!=null || deny!=null){
                if (passengers.size()<=0) {
                    layoutPassenger.setVisibility(View.VISIBLE);
                }else {
                    layoutPassenger.setVisibility(View.GONE);
                    passengers = database.getAllPassenger();
                    PassengerAdapter adapter = new PassengerAdapter(mContext, passengers, database);
                    passengerView.setAdapter(adapter);
                }
            }

        }
    };
    private void getCurrentLocation() {
        GPSTracker gps = new GPSTracker(this);
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Thread t = new Thread(new locate());
            t.start();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Thông báo");  // GPS not found
            builder.setMessage("Chức năng này cần lấy vị trí hiện tại của bạn.Bạn có muốn bật định vị?"); // Want to enable?
            builder.setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
    }

    private void sendLocationToServer() {
        RequestParams params;
        params = new RequestParams();
        params.put("from", preference.getPlaceFrom());
        params.put("to", preference.getPlaceTo());
        params.put("type", preference.getType());
        params.put("name", preference.getOwnerName());
        params.put("phone", preference.getDriverPhone());
        params.put("lon", longitude);
        params.put("lat", latitude);
        params.put("bienso", preference.getLicense());
        params.put("start", preference.getParkStart());
        params.put("end", preference.getParkEnd());
        params.put("idtaixe", preference.getDriverId());
        Log.i("params deleteDelivery", params.toString());
        BaseService.getHttpClient().post(Defines.URL_LOCATE, params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                // called when response HTTP status is "200 OK"
                Log.i("JSON", new String(responseBody));
                //parseJsonResult(new String(responseBody));



            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("JSON", new String(responseBody));
            }

            @Override
            public void onRetry(int retryNo) {
            }
        });
    }

    private class locate implements Runnable {
        public void run() {
            try {
                while (true) {
                    Log.e("TAG","loop");
                    sendLocationToServer();
                    Thread.sleep(Defines.LOOP_TIME);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCurrentLocation();
    }
}

