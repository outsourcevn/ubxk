package booking.online.bus.UI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import booking.online.bus.Models.OwnerCarObject;
import booking.online.bus.R;
import booking.online.bus.Utilities.BaseService;
import booking.online.bus.Utilities.Defines;
import booking.online.bus.Utilities.SharePreference;
import booking.online.bus.Utilities.Utilites;
import booking.online.bus.Widget.GPSTracker;

public class SearchActiveBusScreen extends AppCompatActivity  implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private EditText                edtStartPlace, edtVehicleType, edtDestinationPlace;
    private TextInputLayout         edtStartPlaceHint, edtVehicleTypeHint, edtDestinationPlaceHint;
    private ImageView               imgSelectStartPlace, imgVehicleType, imgDestinationPlace;
    private Context                 mContext;
    private Button                  btnSearch, btnBookTicket;
    private CharSequence[]          CharProvinceFrom,CharProvinceTo, vehicles;
    private ArrayList<String>       provinceFrom, provinceTo;
    //private GoogleApiClient         googleApiClient;
    private SharePreference         preference;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_active_bus_screen);
        mContext = this;
        preference = new SharePreference(this);
        initComponents();
        getProvinceFromServer();
    }

    private void getProvinceFromServer() {
        provinceFrom = new ArrayList<>();
        if (Utilites.isOnline(mContext)) {
            dialog = new ProgressDialog(this);
            dialog.setMessage("Đang tải dữ liệu");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
            RequestParams params;
            params = new RequestParams();
            params.put("type", 1);
            BaseService.getHttpClient().post(Defines.URL_PROVINCE,params, new AsyncHttpResponseHandler() {

                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                    // called when response HTTP status is "200 OK"
                    Log.i("JSON", new String(responseBody));
                    try {
                        JSONArray arrayresult = new JSONArray(new String(responseBody));
                        for (int i = 0; i < arrayresult.length(); i++) {
                            JSONObject result = arrayresult.getJSONObject(i);
                            String name = result.getString("name");
                            provinceFrom.add(name);
                        }
                        getProvinceTo();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                }

                @Override
                public void onRetry(int retryNo) {
                }
            });
        }else{
           /* Snackbar snackbar = Snackbar
                    .make(root, "Không có kết nối mạng!", Snackbar.LENGTH_LONG)
                    .setAction("Thử lại", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getListOwnerCar();
                        }
                    });

            // Changing message text color
            snackbar.setActionTextColor(Color.RED);

            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();*/
        }
    }

    private void getProvinceTo() {
        provinceTo = new ArrayList<>();
        if (Utilites.isOnline(mContext)) {
            RequestParams params;
            params = new RequestParams();
            params.put("type", 2);
            BaseService.getHttpClient().post(Defines.URL_PROVINCE,params, new AsyncHttpResponseHandler() {

                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                    // called when response HTTP status is "200 OK"
                    Log.i("JSON", new String(responseBody));
                    try {
                        JSONArray arrayresult = new JSONArray(new String(responseBody));
                        for (int i = 0; i < arrayresult.length(); i++) {
                            JSONObject result = arrayresult.getJSONObject(i);
                            String name = result.getString("name");
                            provinceTo.add(name);
                        }
                        dialog.dismiss();
                        createArrayProvince();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                }

                @Override
                public void onRetry(int retryNo) {
                }
            });
        }else{
           /* Snackbar snackbar = Snackbar
                    .make(root, "Không có kết nối mạng!", Snackbar.LENGTH_LONG)
                    .setAction("Thử lại", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getListOwnerCar();
                        }
                    });

            // Changing message text color
            snackbar.setActionTextColor(Color.RED);

            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();*/
        }
    }

    private void createArrayProvince() {
        // lấy các tỉnh thành đi
        CharProvinceFrom = new CharSequence[provinceFrom.size()];
        CharProvinceFrom[0]="Hà Nội";
        CharProvinceFrom[1]="Sài Gòn";
        CharProvinceFrom[2]="Đà Nẵng";
        CharProvinceFrom[3]="Hải Phòng";
        int index =4;
        for (int i=0 ; i < provinceFrom.size(); i++){
            if (!provinceFrom.get(i).equals("Hà Nội")&& !provinceFrom.get(i).equals("Sài Gòn")&& !provinceFrom.get(i).equals("Đà Nẵng")&& !provinceFrom.get(i).equals("Hải Phòng")) {
                CharProvinceFrom[index] = provinceFrom.get(i);
                index ++;
            }
        }
        // lấy các tỉnh thành đến
        CharProvinceTo = new CharSequence[provinceTo.size()];
        CharProvinceTo[0]="Hà Nội";
        CharProvinceTo[1]="Sài Gòn";
        CharProvinceTo[2]="Đà Nẵng";
        CharProvinceTo[3]="Hải Phòng";
        index =4;
        for (int i=0 ; i < provinceTo.size(); i++){
            if (!provinceTo.get(i).equals("Hà Nội")&& !provinceTo.get(i).equals("Sài Gòn")&& !provinceTo.get(i).equals("Đà Nẵng")&& !provinceTo.get(i).equals("Hải Phòng")) {
                CharProvinceTo[index] = provinceTo.get(i);
                index ++;
            }
        }
    }

    /*
  Initiate all component of activity
  */
    private void initComponents() {
        edtStartPlace               =       (EditText)          findViewById(R.id.edt_start_place);
        edtVehicleType              =       (EditText)          findViewById(R.id.edt_vehicle_type);
        edtDestinationPlace         =       (EditText)          findViewById(R.id.edt_destination_place);

        edtStartPlaceHint           =       (TextInputLayout)   findViewById(R.id.edt_start_place_hint);
        edtDestinationPlaceHint     =       (TextInputLayout)   findViewById(R.id.edt_destination_place_hint);
        edtVehicleTypeHint          =       (TextInputLayout)   findViewById(R.id.edt_vehicle_type_hint);

        imgSelectStartPlace         =       (ImageView)         findViewById(R.id.btn_spin_start_place);
        imgVehicleType              =       (ImageView)         findViewById(R.id.btn_spin_vehicle_type);
        imgDestinationPlace         =       (ImageView)         findViewById(R.id.btn_spin_destination_place);

        btnSearch                   =       (Button)            findViewById(R.id.btn_search);
        btnBookTicket               =       (Button)            findViewById(R.id.btn_book_ticket);
        Toolbar toolbar             =       (Toolbar)           findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tìm xe hoạt động");

        vehicles                    =       getResources().getStringArray(R.array.vehicle_array);


        imgSelectStartPlace.setOnClickListener(start_place_click_listener);
        edtStartPlace.setOnClickListener(start_place_click_listener);

        imgDestinationPlace.setOnClickListener(destination_place_click_listener);
        edtDestinationPlace.setOnClickListener(destination_place_click_listener);

        imgVehicleType.setOnClickListener(vehicle_type_click_listener);
        edtVehicleType.setOnClickListener(vehicle_type_click_listener);
        btnSearch.setOnClickListener(search_bus_listener);
        btnBookTicket.setOnClickListener(book_ticket_listener);
    }

    private View.OnClickListener start_place_click_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Chọn địa điểm")
                    .setSingleChoiceItems(CharProvinceFrom,-1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            edtStartPlace.setText(CharProvinceFrom[which]);
                            dialog.dismiss();
                            edtStartPlaceHint.setErrorEnabled(false);
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            params.rightMargin  = (int) Utilites.convertDpToPixel(5, mContext);
                            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                            params.addRule(RelativeLayout.CENTER_VERTICAL);
                            imgSelectStartPlace.setLayoutParams(params);
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    };


    private View.OnClickListener destination_place_click_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Chọn địa điểm")
                    .setSingleChoiceItems(CharProvinceTo,-1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            edtDestinationPlace.setText(CharProvinceTo[which]);
                            dialog.dismiss();
                            edtDestinationPlaceHint.setErrorEnabled(false);
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            params.rightMargin  = (int) Utilites.convertDpToPixel(5, mContext);
                            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                            params.addRule(RelativeLayout.CENTER_VERTICAL);
                            imgDestinationPlace.setLayoutParams(params);
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    };


    private View.OnClickListener vehicle_type_click_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Chọn loại xe")
                    .setSingleChoiceItems(vehicles,-1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            edtVehicleType.setText(vehicles[which]);
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    };

    public View.OnClickListener search_bus_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String startPlace       = edtStartPlace.getText().toString();
            String destinationPlace = edtDestinationPlace.getText().toString();
            String vehicleType = edtVehicleType != null?edtVehicleType.getText().toString():"";
            if (startPlace == null || startPlace.equals("")) {
                edtStartPlaceHint.setError("Bạn chưa nhập điểm khởi hành");
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                params.rightMargin  = (int) Utilites.convertDpToPixel(5, mContext);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.topMargin = (int) Utilites.convertDpToPixel(12, mContext);
                imgSelectStartPlace.setLayoutParams(params);
                return;
            }
            if (destinationPlace == null || destinationPlace.equals("")) {
                edtDestinationPlaceHint.setError("Bạn chưa nhập điểm đến");
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                params.rightMargin  = (int) Utilites.convertDpToPixel(5, mContext);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.topMargin = (int) Utilites.convertDpToPixel(12, mContext);
                imgDestinationPlace.setLayoutParams(params);
                return;
            }
            GPSTracker gps = new GPSTracker(mContext);
            // check if GPS enabled
            if (!gps.canGetLocation()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
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
            }else {
                Intent intent = new Intent(mContext, QuickListVehicleActivity.class);
                intent.putExtra(Defines.PROVINCE_FROM_ACTION, startPlace);
                intent.putExtra(Defines.PROVINCE_TO_ACTION, destinationPlace);
                intent.putExtra(Defines.VEHICLE_TYPE_ACTION, vehicleType);
                startActivity(intent);
            }
        }
    };
    private View.OnClickListener book_ticket_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String startPlace       = edtStartPlace.getText().toString();
            String destinationPlace = edtDestinationPlace.getText().toString();
            String vehicleType = edtVehicleType != null?edtVehicleType.getText().toString():"";
            if (startPlace == null || startPlace.equals("")) {
                edtStartPlaceHint.setError("Bạn chưa nhập điểm khởi hành");
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                params.rightMargin  = (int) Utilites.convertDpToPixel(5, mContext);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.topMargin = (int) Utilites.convertDpToPixel(12, mContext);
                imgSelectStartPlace.setLayoutParams(params);
                return;
            }
            if (destinationPlace == null || destinationPlace.equals("")) {
                edtDestinationPlaceHint.setError("Bạn chưa nhập điểm đến");
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                params.rightMargin  = (int) Utilites.convertDpToPixel(5, mContext);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.topMargin = (int) Utilites.convertDpToPixel(12, mContext);
                imgDestinationPlace.setLayoutParams(params);
                return;
            }
            Intent intent = new Intent(mContext, ListVehicleActivity.class);
            intent.putExtra(Defines.PROVINCE_FROM_ACTION, startPlace);
            intent.putExtra(Defines.PROVINCE_TO_ACTION, destinationPlace);
            intent.putExtra(Defines.VEHICLE_TYPE_ACTION, vehicleType);
            startActivity(intent);
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.intro_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == R.id.action_introduce) {
            Intent intent = new Intent(mContext, TermOfUseActivity.class);
            startActivity(intent);
        }else   if (item.getItemId() == R.id.action_contact_me) {
            Intent intent = new Intent(mContext, ContactActivty.class);
            startActivity(intent);
        }else   if (item.getItemId() == R.id.switch_user) {
            showDialogSwitchUser();
        }

        return super.onOptionsItemSelected(item);
    }
    private void showDialogSwitchUser() {
        android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(this)
                .setTitle("Thông báo")
                .setMessage("Bạn có muốn chọn lại vai trò của mình?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        preference.saveRole(0);
                        Intent intent = new Intent(mContext, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }
   /* private void settingRequest() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            /*//**************************
            builder.setAlwaysShow(true); //this is the key ingredient
            /*//**************************

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    final LocationSettingsStates state = result.getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // All location settings are satisfied. The client can initialize location
                            // requests here.
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the user
                            // a dialog.
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult((Activity) mContext, 1000);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            });
        }
    }*/

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
