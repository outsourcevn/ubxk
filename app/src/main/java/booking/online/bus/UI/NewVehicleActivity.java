package booking.online.bus.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import booking.online.bus.Utilities.Utilites;

public class NewVehicleActivity extends AppCompatActivity {
    private AutoCompleteTextView txtProvinceFrom, txtProvinceTo, txtPlaceFrom, txtPlaceTo, txtName;
    private ArrayList<String> aTypes, placeFrom, placeTo, aTimes, aReceive, aVehicleType, aName;
    private FrameLayout layoutProvinceFrom, layoutProvinceTo, layoutPlaceFrom, layoutPlaceTo, layoutName, layoutPhone, layoutPrice,layoutUtilities,layoutType, layoutTime, layoutReceive,layoutVehicleType;
    private ImageView imgType, imgTime, imgReceive, imgVehicleType;
    private EditText edtPhone, edtPrice, edtUtilities;
    private TextView txtType,txtTime, txtReceive, txtTypeVehicle;
    private TextView errProvinceFrom, errProvinceTo, errPlaceFrom, errPlaceTo, errName, errPhone, errPrice,errUtilities,errType, errTime, errReceive,errVehicleType;
    private ProgressDialog dialog;
    private Toolbar toolbar;
    private Context mContext;
    private Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_vehicle);
        mContext = this;
        getAllDataFromServer();
    }



    private void initComponents() {

        layoutProvinceFrom  = (FrameLayout)             findViewById(R.id.layout_from_province);
        layoutProvinceTo    = (FrameLayout)             findViewById(R.id.layout_to_province);
        layoutPlaceFrom     = (FrameLayout)             findViewById(R.id.layout_place_from);
        layoutPlaceTo       = (FrameLayout)             findViewById(R.id.layout_to_place);
        layoutName          = (FrameLayout)             findViewById(R.id.layout_name);
        layoutPhone         = (FrameLayout)             findViewById(R.id.layout_phone);
        layoutPrice         = (FrameLayout)             findViewById(R.id.layout_price);
        layoutUtilities     = (FrameLayout)             findViewById(R.id.layout_utilities);
        layoutType          = (FrameLayout)             findViewById(R.id.layout_type);
        layoutTime          = (FrameLayout)             findViewById(R.id.layout_time);
        layoutReceive       = (FrameLayout)             findViewById(R.id.layout_receive);
        layoutVehicleType   = (FrameLayout)             findViewById(R.id.layout_vehicle_type);


        imgType             = (ImageView)               findViewById(R.id.btn_get_type);
        imgTime             = (ImageView)               findViewById(R.id.btn_get_time);
        imgReceive          = (ImageView)               findViewById(R.id.btn_get_receive);
        imgVehicleType      = (ImageView)               findViewById(R.id.btn_get_vehicle_type);

        edtPhone            = (EditText)                findViewById(R.id.edt_phone);
        edtPrice            = (EditText)                findViewById(R.id.edt_price);
        edtUtilities        = (EditText)                findViewById(R.id.edt_utilities);


        txtType             = (TextView)                findViewById(R.id.txt_type);
        txtTime             = (TextView)                findViewById(R.id.edt_time);
        txtReceive          = (TextView)                findViewById(R.id.edt_receive);
        txtTypeVehicle      = (TextView)                findViewById(R.id.edt_vehicle_type);

        txtProvinceFrom     = (AutoCompleteTextView)    findViewById(R.id.txt_from_province);
        txtProvinceTo       = (AutoCompleteTextView)    findViewById(R.id.txt_to_province);
        txtPlaceFrom        = (AutoCompleteTextView)    findViewById(R.id.txt_from_place);
        txtPlaceTo          = (AutoCompleteTextView)    findViewById(R.id.txt_to_place);
        txtName             = (AutoCompleteTextView)    findViewById(R.id.edt_name);

        errProvinceFrom     = (TextView)                findViewById(R.id.txt_from_province_error);
        errProvinceTo       = (TextView)                findViewById(R.id.txt_to_province_error);
        errType             = (TextView)                findViewById(R.id.txt_type_error);
        errPlaceFrom        = (TextView)                findViewById(R.id.txt_from_place_error);
        errPlaceTo          = (TextView)                findViewById(R.id.txt_to_place_error);
        errName             = (TextView)                findViewById(R.id.txt_name_error);
        errPhone            = (TextView)                findViewById(R.id.txt_phone_error);
        errPrice            = (TextView)                findViewById(R.id.txt_price_error);
        errUtilities        = (TextView)                findViewById(R.id.txt_utilities_error);
        errVehicleType      = (TextView)                findViewById(R.id.txt_vehicle_type_error);
        errTime             = (TextView)                findViewById(R.id.txt_time_error);
        errReceive          = (TextView)                findViewById(R.id.txt_receive_error);

        btnRegister         = (Button)                  findViewById(R.id.btn_register);
        toolbar             = (Toolbar)                 findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tạo nhà xe mới");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ArrayAdapter<String> adapterProvinceFrom = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, Defines.provinceFrom);
        txtProvinceFrom.setAdapter(adapterProvinceFrom);
        txtProvinceFrom.setThreshold(1);

        ArrayAdapter<String> adapterProvinceTo = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, Defines.provinceFrom);
        txtProvinceTo.setAdapter(adapterProvinceTo);
        txtProvinceTo.setThreshold(1);

        ArrayAdapter<String> adapterPlaceFrom = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, placeFrom);
        txtPlaceFrom.setAdapter(adapterPlaceFrom);
        txtPlaceFrom.setThreshold(1);

        ArrayAdapter<String> adapterPlaceTo = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, placeTo);
        txtPlaceTo.setAdapter(adapterPlaceTo);
        txtPlaceTo.setThreshold(1);

        ArrayAdapter<String> adapterName = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, aName);
        txtName.setAdapter(adapterName);
        txtName.setThreshold(1);

        layoutType.setOnClickListener(click_to_type_listener);
        layoutTime.setOnClickListener(click_to_time_listener);
        layoutReceive.setOnClickListener(click_to_receive_listener);
        layoutVehicleType.setOnClickListener(click_to_vehicle_type_listener);
        layoutProvinceFrom.setOnClickListener(click_province_from_listener);
        layoutProvinceTo.setOnClickListener(click_province_to_listener);
        layoutPlaceFrom.setOnClickListener(click_place_from_listener);
        layoutPlaceTo.setOnClickListener(click_place_to_listener);
        layoutName.setOnClickListener(click_name_listener);
        layoutPhone.setOnClickListener(click_phone_listener);
        layoutPrice.setOnClickListener(click_price_listener);
        layoutUtilities.setOnClickListener(click_utilities_listener);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAllError();
                if (!checkParamsNull()) {
                    registerNewVehicle();
                }
            }
        });
    }

    private void hideAllError() {
        errProvinceFrom.setVisibility(View.INVISIBLE);
        errProvinceTo.setVisibility(View.INVISIBLE);
        errType.setVisibility(View.INVISIBLE);
        errPlaceFrom.setVisibility(View.INVISIBLE);
        errPlaceTo.setVisibility(View.INVISIBLE);
        errTime.setVisibility(View.INVISIBLE);
        errReceive.setVisibility(View.INVISIBLE);
        errVehicleType.setVisibility(View.INVISIBLE);
        errName.setVisibility(View.INVISIBLE);
        errPhone.setVisibility(View.INVISIBLE);
        errPrice.setVisibility(View.INVISIBLE);
        errUtilities.setVisibility(View.INVISIBLE);
    }

    private boolean checkParamsNull() {
        if (txtProvinceFrom.getText().toString().equals("")|| txtProvinceFrom.getText().toString() == null){
            requestFocus(txtProvinceFrom);
            errProvinceFrom.setVisibility(View.VISIBLE);
            return true;
        }
        if (txtProvinceTo.getText().toString().equals("")|| txtProvinceTo.getText().toString() == null){
            requestFocus(txtProvinceTo);
            errProvinceTo.setVisibility(View.VISIBLE);
            return true;
        }
        if (txtType.getText().toString().equals("")|| txtType.getText().toString() == null){
            requestFocus(txtType);
            errType.setVisibility(View.VISIBLE);
            return true;
        }
        if (txtPlaceFrom.getText().toString().equals("")|| txtPlaceFrom.getText().toString() == null){
            requestFocus(txtPlaceFrom);
            errPlaceFrom.setVisibility(View.VISIBLE);
            return true;
        }

        if (txtPlaceTo.getText().toString().equals("")|| txtPlaceTo.getText().toString() == null){
            requestFocus(txtPlaceTo);
            errPlaceTo.setVisibility(View.VISIBLE);
            return true;
        }

        if (txtTime.getText().toString().equals("")|| txtTime.getText().toString() == null){
            requestFocus(txtTime);
            errTime.setVisibility(View.VISIBLE);
            return true;
        }

        if (txtReceive.getText().toString().equals("")|| txtReceive.getText().toString() == null){
            requestFocus(txtReceive);
            errReceive.setVisibility(View.VISIBLE);
            return true;
        }

        if (txtTypeVehicle.getText().toString().equals("")|| txtTypeVehicle.getText().toString() == null){
            requestFocus(txtTypeVehicle);
            errVehicleType.setVisibility(View.VISIBLE);
            return true;
        }
        if (txtName.getText().toString().equals("")|| txtName.getText().toString() == null){
            requestFocus(txtName);
            errName.setVisibility(View.VISIBLE);
            return true;
        }
        if (edtPhone.getText().toString().equals("")|| edtPhone.getText().toString() == null){
            requestFocus(edtPhone);
            errPhone.setVisibility(View.VISIBLE);
            return true;
        }
        if (edtPrice.getText().toString().equals("")|| edtPrice.getText().toString() == null){
            requestFocus(edtPrice);
            errPrice.setVisibility(View.VISIBLE);
            return true;
        }
        if (edtUtilities.getText().toString().equals("")|| edtUtilities.getText().toString() == null){
            requestFocus(edtUtilities);
            errUtilities.setVisibility(View.VISIBLE);
            return true;
        }
        return false;
    }

    private void registerNewVehicle() {
        RequestParams params;
        params = new RequestParams();
        params.put("f2", txtProvinceFrom.getText().toString());
        params.put("f3",  txtProvinceTo.getText().toString());
        params.put("f4", txtType.getText().toString());
        params.put("f7", txtPlaceFrom.getText().toString());
        params.put("f8", txtPlaceTo.getText().toString());
        params.put("f9", txtTime.getText().toString());
        params.put("f10", txtReceive.getText().toString());
        params.put("f11", txtTypeVehicle.getText().toString());
        params.put("f13", txtName.getText().toString());
        params.put("f14", edtPhone.getText().toString());
        params.put("f17", edtPrice.getText().toString());
        params.put("f18", edtUtilities.getText().toString());
        Log.i("params deleteDelivery", params.toString());
        dialog = new ProgressDialog(this);
        dialog.setMessage("Đang tải dữ liệu");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        BaseService.getHttpClient().post(Defines.URL_REGISTER_VEHICLE, params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                // called when response HTTP status is "200 OK"
                Log.i("JSON", new String(responseBody));
                //parseJsonResult(new String(responseBody));
                int result = Integer.valueOf(new String(responseBody));
                if (result != 0) {
                    Toast.makeText(mContext, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }else
                    Toast.makeText(mContext, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                dialog.dismiss();


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


    private View.OnClickListener click_to_type_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Chọn loại hình xe")
                    .setSingleChoiceItems(aTypes.toArray(new String[aTypes.size()]),-1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            txtType.setText(aTypes.get(which));
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    };

    private View.OnClickListener click_to_vehicle_type_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Chọn loại xe")
                    .setSingleChoiceItems(aVehicleType.toArray(new String[aVehicleType.size()]),-1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            txtTypeVehicle.setText(aVehicleType.get(which));
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    };

    private View.OnClickListener click_to_time_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Chọn thời gian")
                    .setSingleChoiceItems(aTimes.toArray(new String[aTimes.size()]),-1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            txtTime.setText(aTimes.get(which));
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    };

    private View.OnClickListener click_to_receive_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Chọn đón trả")
                    .setSingleChoiceItems(aReceive.toArray(new String[aReceive.size()]),-1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            txtReceive.setText(aReceive.get(which));
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    };

    private void requestFocus(View view) {

        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }
    private View.OnClickListener click_province_from_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            requestFocus(txtProvinceFrom);
        }
    };


    private View.OnClickListener click_province_to_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            requestFocus(txtProvinceTo);
        }
    };

    private View.OnClickListener click_place_from_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            requestFocus(txtPlaceFrom);
        }
    };

    private View.OnClickListener click_place_to_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            requestFocus(txtPlaceTo);
        }
    };

    private View.OnClickListener click_name_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            requestFocus(txtName);
        }
    };

    private View.OnClickListener click_phone_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            requestFocus(edtPhone);
        }
    };
    private View.OnClickListener click_price_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            requestFocus(edtPrice);
        }
    };
    private View.OnClickListener click_utilities_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            requestFocus(edtUtilities);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
//==================================================================================================================================
    /*
    * Get type from server
     */
    private void getAllDataFromServer() {
        aTypes = new ArrayList<>();
        RequestParams params;
        params = new RequestParams();
        params.put("field","f4");
        dialog = new ProgressDialog(this);
        dialog.setMessage("Đang tải dữ liệu");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        BaseService.getHttpClient().post(Defines.URL_NEW_VEHICLE,params,  new AsyncHttpResponseHandler() {

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
                        aTypes.add(name);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getStartPlace();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
            }

            @Override
            public void onRetry(int retryNo) {
            }
        });
    }
    /*
        * Get start place from server
         */
    private void getStartPlace() {
        placeFrom = new ArrayList<>();
        RequestParams params;
        params = new RequestParams();
        params.put("field","f7");
        BaseService.getHttpClient().post(Defines.URL_NEW_VEHICLE,params,  new AsyncHttpResponseHandler() {

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
                        placeFrom.add(name);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getDesPlace();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
            }

            @Override
            public void onRetry(int retryNo) {
            }
        });
    }

    /*
        * Get destination place from server
         */
    private void getDesPlace() {
        placeTo = new ArrayList<>();
        RequestParams params;
        params = new RequestParams();
        params.put("field","f8");
        BaseService.getHttpClient().post(Defines.URL_NEW_VEHICLE,params,  new AsyncHttpResponseHandler() {

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
                        placeTo.add(name);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getTime();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
            }

            @Override
            public void onRetry(int retryNo) {
            }
        });
    }
    /*
       * Get time from server
        */
    private void getTime() {
        aTimes = new ArrayList<>();
        RequestParams params;
        params = new RequestParams();
        params.put("field","f9");
        BaseService.getHttpClient().post(Defines.URL_NEW_VEHICLE,params,  new AsyncHttpResponseHandler() {

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
                        aTimes.add(name);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getReceive();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
            }

            @Override
            public void onRetry(int retryNo) {
            }
        });
    }

    /*
       * Get Receive point server
        */
    private void getReceive() {
        aReceive = new ArrayList<>();
        RequestParams params;
        params = new RequestParams();
        params.put("field","f10");
        BaseService.getHttpClient().post(Defines.URL_NEW_VEHICLE,params,  new AsyncHttpResponseHandler() {

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
                        aReceive.add(name);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getVehicleType();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
            }

            @Override
            public void onRetry(int retryNo) {
            }
        });
    }

    /*
       * Get Vehicle Type from server
        */
    private void getVehicleType() {
        aVehicleType = new ArrayList<>();
        RequestParams params;
        params = new RequestParams();
        params.put("field","f11");
        BaseService.getHttpClient().post(Defines.URL_NEW_VEHICLE,params,  new AsyncHttpResponseHandler() {

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
                        aVehicleType.add(name);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getVehicleName();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
            }

            @Override
            public void onRetry(int retryNo) {
            }
        });
    }
    /*
      * Get Vehicle name from server
       */
    private void getVehicleName() {
        aName = new ArrayList<>();
        RequestParams params;
        params = new RequestParams();
        params.put("field","f13");
        BaseService.getHttpClient().post(Defines.URL_NEW_VEHICLE,params,  new AsyncHttpResponseHandler() {

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
                        aName.add(name);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
                initComponents();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
            }

            @Override
            public void onRetry(int retryNo) {
            }
        });
    }
}
