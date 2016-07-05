package com.example.xe63.com.xe63.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.xe63.com.xe63.R;
import com.example.xe63.com.xe63.Utilities.Defines;
import com.example.xe63.com.xe63.Utilities.Utilites;

public class SearchActiveBusScreen extends AppCompatActivity {

    private EditText                edtStartPlace, edtVehicleType, edtDestinationPlace;
    private TextInputLayout         edtStartPlaceHint, edtVehicleTypeHint, edtDestinationPlaceHint;
    private ImageView               imgSelectStartPlace, imgVehicleType, imgDestinationPlace;
    private Context                 mContext;
    private Button                  btnSearch;
    private CharSequence[]          provinces, vehicles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_active_bus_screen);
        mContext = this;
        initComponents();
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
        Toolbar toolbar             =       (Toolbar)           findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tìm xe hoạt động");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        provinces                   =       getResources().getStringArray(R.array.province_array);
        vehicles                    =       getResources().getStringArray(R.array.vehicle_array);


        imgSelectStartPlace.setOnClickListener(start_place_click_listener);
        edtStartPlace.setOnClickListener(start_place_click_listener);

        imgDestinationPlace.setOnClickListener(destination_place_click_listener);
        edtDestinationPlace.setOnClickListener(destination_place_click_listener);

        imgVehicleType.setOnClickListener(vehicle_type_click_listener);
        edtVehicleType.setOnClickListener(vehicle_type_click_listener);
        btnSearch.setOnClickListener(search_bus_listener);
    }

    private View.OnClickListener start_place_click_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Chọn địa điểm")
                    .setSingleChoiceItems(provinces,-1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            edtStartPlace.setText(provinces[which]);
                            dialog.dismiss();
                            edtStartPlaceHint.setErrorEnabled(false);
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            params.rightMargin  = (int) com.example.xe63.com.xe63.Utilities.Utilites.convertDpToPixel(5, mContext);
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
                    .setSingleChoiceItems(provinces,-1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            edtDestinationPlace.setText(provinces[which]);
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

    private View.OnClickListener search_bus_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String startPlace       = edtStartPlace.getText().toString();
            String destinationPlace = edtDestinationPlace.getText().toString();

            if (startPlace == null || startPlace.equals("")) {
                edtStartPlaceHint.setError("Bạn chưa nhập điểm khởi hành");
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                params.rightMargin  = (int) com.example.xe63.com.xe63.Utilities.Utilites.convertDpToPixel(5, mContext);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.topMargin = (int) com.example.xe63.com.xe63.Utilities.Utilites.convertDpToPixel(12, mContext);
                imgSelectStartPlace.setLayoutParams(params);
                return;
            }
            if (destinationPlace == null || destinationPlace.equals("")) {
                edtDestinationPlaceHint.setError("Bạn chưa nhập điểm đến");
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                params.rightMargin  = (int) com.example.xe63.com.xe63.Utilities.Utilites.convertDpToPixel(5, mContext);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.topMargin = (int) com.example.xe63.com.xe63.Utilities.Utilites.convertDpToPixel(12, mContext);
                imgDestinationPlace.setLayoutParams(params);
                return;
            }
            Intent intent = new Intent(mContext, ListVehicleActivity.class);
            intent.putExtra(Defines.PROVINCE_FROM_ACTION, startPlace);
            intent.putExtra(Defines.PROVINCE_TO_ACTION, destinationPlace);
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
}
