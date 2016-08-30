package booking.online.bus.UI;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import booking.online.bus.Controller.OwnerAdapter;
import booking.online.bus.Models.OwnerCarObject;
import booking.online.bus.R;
import booking.online.bus.Utilities.BaseService;
import booking.online.bus.Utilities.Defines;
import booking.online.bus.Utilities.SharePreference;
import booking.online.bus.Utilities.Utilites;

public class RegisterOwnerActivity extends AppCompatActivity {

    private EditText edtStartRegister, edtOwnerRegister, edtDestinationRegister;
    private TextInputLayout edtStartRegisterHint, edtOwnerRegisterHint, edtDestinationRegisterHint;
    private ImageView imgOwnerRegister;
    private LinearLayout layoutRoute;
    RelativeLayout root;
    private Context mContext;
    private Button btnLetsRegister;
    private ArrayList<OwnerCarObject> ownerCars ,ownerCarsFilter;
    private ProgressDialog dialog;
    private OwnerCarObject selected;
    private SharePreference preference;
    private ArrayList<String> province;
    private boolean isFilter = false;
    private String proFrom="", proTo="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_owner);
        mContext = this;
        preference = new SharePreference(this);
        initComponents();
    }

    /*
  Initiate all component of activity
  */
    private void initComponents() {
        edtStartRegister                    =       (EditText)          findViewById(R.id.edt_from_register);
        edtOwnerRegister                    =       (EditText)          findViewById(R.id.edt_owner_register);
        edtDestinationRegister              =       (EditText)          findViewById(R.id.edt_destination_register);

        edtStartRegisterHint                =       (TextInputLayout)   findViewById(R.id.edt_from_register_hint);
        edtOwnerRegisterHint                =       (TextInputLayout)   findViewById(R.id.edt_owner_register_hint);
        edtDestinationRegisterHint          =       (TextInputLayout)   findViewById(R.id.edt_destination_register_hint);

        imgOwnerRegister                    =       (ImageView)         findViewById(R.id.btn_spin_owner_register);

        layoutRoute                         =       (LinearLayout)      findViewById(R.id.layout_route);
        btnLetsRegister                     =       (Button)            findViewById(R.id.btn_let_register);
        Toolbar toolbar                     =       (Toolbar)           findViewById(R.id.toolbar);
        root                                =       (RelativeLayout)    findViewById(R.id.root);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Đăng ký xe hoạt động");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        edtOwnerRegister.setOnClickListener(register_owner_click_listener);
        imgOwnerRegister.setOnClickListener(register_owner_click_listener);

        btnLetsRegister.setOnClickListener(search_bus_listener);

        getListOwnerCar();


    }

    private void getListOwnerCar() {
        ownerCars = new ArrayList<>();
        if (Utilites.isOnline(mContext)) {
            dialog = new ProgressDialog(this);
            dialog.setMessage("Đang tải dữ liệu");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
            BaseService.getHttpClient().get(Defines.URL_LIST_OWNER, new AsyncHttpResponseHandler() {

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
                            int id = result.getInt("id");
                            String name = result.getString("F13");
                            String start = result.getString("F7");
                            String end = result.getString("F8");
                            String type = result.getString("F4");
                            String provinceFrom = result.getString("F2");
                            String provinceTo = result.getString("F3");
                            OwnerCarObject object = new OwnerCarObject(id, name, start, end,type,provinceFrom, provinceTo);
                            ownerCars.add(object);
                        }

                        // get all province
                        province = new ArrayList<>();
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
                                        province.add(name);
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
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                    Snackbar snackbar = Snackbar
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
                    snackbar.show();
                }

                @Override
                public void onRetry(int retryNo) {
                }
            });
        }else{
            Snackbar snackbar = Snackbar
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
            snackbar.show();
        }
    }

    private void showSelectedDialog() {
        final Dialog dialog = new Dialog(mContext,android.R.style.Theme_Light);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.select_owner);

        final ListView              listview        = (ListView)                    dialog.findViewById(R.id.listView);
        final ImageView             imgFilter       = (ImageView)                   dialog.findViewById(R.id.img_filer);
        final LinearLayout          layoutFilter    = (LinearLayout)                dialog.findViewById(R.id.layout_filter);
        final LinearLayout          layoutOption    = (LinearLayout)                dialog.findViewById(R.id.layout_option);
        final Spinner               spFrom          = (Spinner)                     dialog.findViewById(R.id.spinner_from);
        final Spinner               spTo            = (Spinner)                     dialog.findViewById(R.id.spinner_to);
        final FloatingActionButton  imgAddVehicle   = (FloatingActionButton)        dialog.findViewById(R.id.img_new_vehicle);

        imgAddVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NewVehicleActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutFilter.performClick();
            }
        });
        layoutFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFilter){
                    layoutOption.setVisibility(View.VISIBLE);
                    imgFilter.setImageResource(R.mipmap.province_cancel_filler);
                    isFilter = true;
                    spFrom.setSelection(0);
                    spTo.setSelection(0);
                }else{
                    layoutOption.setVisibility(View.GONE);
                    imgFilter.setImageResource(R.mipmap.province_filler);
                    isFilter = false;
                    spFrom.setSelection(0);
                    spTo.setSelection(0);
                }
                OwnerAdapter adapter = new OwnerAdapter(mContext,ownerCars);
                listview.setAdapter(adapter);
                adapter.setOnItemClickListener(new OwnerAdapter.onClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        edtOwnerRegister.setText(ownerCars.get(position).getOwnerName());
                        edtStartRegister.setText(ownerCars.get(position).getStartPlace());
                        edtDestinationRegister.setText(ownerCars.get(position).getEndPlace());
                        preference.saveType(ownerCars.get(position).getType());
                        dialog.dismiss();
                        layoutRoute.setVisibility(View.VISIBLE);
                        selected = ownerCars.get(position);
                    }
                });

            }
        });
        ArrayAdapter<String> adapterFrom = new ArrayAdapter<>(mContext, R.layout.custom_spiner, Defines.provinceFrom);
        adapterFrom.setDropDownViewResource(R.layout.simple_spnner);
        spFrom.setAdapter(adapterFrom);
        spFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ownerCarsFilter = new ArrayList<>();
                ArrayList<OwnerCarObject> temp = new ArrayList<>();
                if (position == 0) {
                    proFrom = "";
                    for (OwnerCarObject owner : ownerCars)
                        temp.add(owner);
                }else {
                    proFrom = Defines.provinceFrom.get(position);
                    for (OwnerCarObject owner : ownerCars)
                        if (owner.getProvinceFrom().equals(proFrom))
                            temp.add(owner);
                }
                if (!proTo.equals("")) {
                    for (OwnerCarObject owner : temp)
                        if (owner.getProvinceTo().equals(proTo))
                            ownerCarsFilter.add(owner);
                }else
                    ownerCarsFilter = temp;
                OwnerAdapter adapter = new OwnerAdapter(mContext,ownerCarsFilter);
                listview.setAdapter(adapter);
                adapter.setOnItemClickListener(new OwnerAdapter.onClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        edtOwnerRegister.setText(ownerCarsFilter.get(position).getOwnerName());
                        edtStartRegister.setText(ownerCarsFilter.get(position).getStartPlace());
                        edtDestinationRegister.setText(ownerCarsFilter.get(position).getEndPlace());
                        preference.saveType(ownerCarsFilter.get(position).getType());
                        dialog.dismiss();
                        layoutRoute.setVisibility(View.VISIBLE);
                        selected = ownerCarsFilter.get(position);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapterTo = new ArrayAdapter<>(mContext, R.layout.custom_spiner, Defines.provinceTo);
        adapterTo.setDropDownViewResource(R.layout.simple_spnner);
        spTo.setAdapter(adapterTo);
        spTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ownerCarsFilter = new ArrayList<>();
                ArrayList<OwnerCarObject> temp = new ArrayList<>();
                if (position == 0) {
                    proTo = "";
                    for (OwnerCarObject owner : ownerCars)
                            temp.add(owner);
                }else {
                    proTo = Defines.provinceTo.get(position);
                    for (OwnerCarObject owner : ownerCars)
                        if (owner.getProvinceTo().equals(proTo))
                            temp.add(owner);
                }

                if (!proFrom.equals("")) {
                    for (OwnerCarObject owner : temp)
                        if (owner.getProvinceFrom().equals(proFrom))
                            ownerCarsFilter.add(owner);
                }else
                    ownerCarsFilter = temp;
                OwnerAdapter adapter = new OwnerAdapter(mContext,ownerCarsFilter);
                listview.setAdapter(adapter);
                adapter.setOnItemClickListener(new OwnerAdapter.onClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        edtOwnerRegister.setText(ownerCarsFilter.get(position).getOwnerName());
                        edtStartRegister.setText(ownerCarsFilter.get(position).getStartPlace());
                        edtDestinationRegister.setText(ownerCarsFilter.get(position).getEndPlace());
                        preference.saveType(ownerCarsFilter.get(position).getType());
                        dialog.dismiss();
                        layoutRoute.setVisibility(View.VISIBLE);
                        selected = ownerCarsFilter.get(position);
                        isFilter = false;
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        OwnerAdapter adapter = new OwnerAdapter(mContext,ownerCars);
        listview.setAdapter(adapter);
        adapter.setOnItemClickListener(new OwnerAdapter.onClickListener() {
            @Override
            public void onItemClick(int position) {
                edtOwnerRegister.setText(ownerCars.get(position).getOwnerName());
                edtStartRegister.setText(ownerCars.get(position).getStartPlace());
                edtDestinationRegister.setText(ownerCars.get(position).getEndPlace());
                preference.saveType(ownerCars.get(position).getType());
                dialog.dismiss();
                layoutRoute.setVisibility(View.VISIBLE);
                selected = ownerCars.get(position);
                isFilter = false;
            }
        });
        dialog.show();
        dialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                isFilter = false;
                dialog.dismiss();
                return true;
            }

        });
    }

    private void LetsFilter(String fromPlace, String endPlace) {


    }


    private View.OnClickListener register_owner_click_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ownerCars.size()>0)
                showSelectedDialog();
            else{
                Snackbar snackbar = Snackbar
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
                snackbar.show();
            }

        }
    };
    private View.OnClickListener search_bus_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String startPlace       = edtStartRegister.getText().toString();
            String destinationPlace = edtDestinationRegister.getText().toString();
            String ownerRegister    = edtOwnerRegister.getText().toString();
            if (ownerRegister == null || ownerRegister.equals("")) {
                edtOwnerRegisterHint.setError("Bạn chưa nhập chủ nhà xe");
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                params.rightMargin  = (int) Utilites.convertDpToPixel(5, mContext);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.topMargin = (int) Utilites.convertDpToPixel(12, mContext);
                imgOwnerRegister.setLayoutParams(params);
                return;
            }

            if (startPlace == null || startPlace.equals("")) {
                edtStartRegisterHint.setError("Bạn chưa nhập điểm khởi hành");
                return;
            }
            if (destinationPlace == null || destinationPlace.equals("")) {
                edtDestinationRegisterHint.setError("Bạn chưa nhập điểm đến");
                return;
            }
            Intent intent = new Intent(mContext, RegisterActivity.class);
            //intent.putExtra(Defines.PROVINCE_FROM_ACTION, startPlace);
            //intent.putExtra(Defines.PROVINCE_TO_ACTION, destinationPlace);
            intent.putExtra(Defines.OWNER_ID_ACTION, selected);
            startActivity(intent);
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
    private void createArrayProvince() {
        // lấy các tỉnh thành đi
        Defines.provinceFrom = new ArrayList<>();
        Defines.provinceTo   = new ArrayList<>();
        Defines.provinceFrom.add("Tất cả");
        Defines.provinceFrom.add("Hà Nội");
        Defines.provinceFrom.add("Sài Gòn");
        Defines.provinceFrom.add("Đà Nẵng");
        Defines.provinceFrom.add("Hải Phòng");

        for (int i=0 ; i < province.size(); i++){
            if (!province.get(i).equals("Hà Nội")&& !province.get(i).equals("Sài Gòn")&& !province.get(i).equals("Đà Nẵng")&& !province.get(i).equals("Hải Phòng")) {
                Defines.provinceFrom.add(province.get(i));
            }
        }

        Defines.provinceTo.add("Tất cả");
        Defines.provinceTo.add("Hà Nội");
        Defines.provinceTo.add("Sài Gòn");
        Defines.provinceTo.add("Đà Nẵng");
        Defines.provinceTo.add("Hải Phòng");

        for (int i=0 ; i < province.size(); i++){
            if (!province.get(i).equals("Hà Nội")&& !province.get(i).equals("Sài Gòn")&& !province.get(i).equals("Đà Nẵng")&& !province.get(i).equals("Hải Phòng")) {
                Defines.provinceTo.add(province.get(i));
            }
        }
    }
}