package booking.online.bus.UI;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import booking.online.bus.R;
import booking.online.bus.Utilities.Defines;
import booking.online.bus.Utilities.Utilites;

public class RegisterOwnerActivity extends AppCompatActivity {

    private EditText edtStartRegister, edtOwnerRegister, edtDestinationRegister;
    private TextInputLayout edtStartRegisterHint, edtOwnerRegisterHint, edtDestinationRegisterHint;
    private ImageView imgSelectStartRegister, imgOwnerRegister, imgDestinationRegister;
    private LinearLayout layoutRoute;
    private Context mContext;
    private Button btnLetsRegister;
    private CharSequence[]          provinces, vehicles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_owner);
        mContext = this;
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

        imgSelectStartRegister              =       (ImageView)         findViewById(R.id.btn_spin_from_register);
        imgOwnerRegister                    =       (ImageView)         findViewById(R.id.btn_spin_owner_register);
        imgDestinationRegister              =       (ImageView)         findViewById(R.id.btn_spin_destination_register);

        layoutRoute                         =       (LinearLayout)      findViewById(R.id.layout_route);
        btnLetsRegister                     =       (Button)            findViewById(R.id.btn_let_register);
        Toolbar toolbar                     =       (Toolbar)           findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Đăng ký xe hoạt động");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        provinces                   =       getResources().getStringArray(R.array.province_array);
        vehicles                    =       getResources().getStringArray(R.array.vehicle_array);


        imgSelectStartRegister.setOnClickListener(start_place_click_listener);
        edtStartRegister.setOnClickListener(start_place_click_listener);

        edtOwnerRegister.setOnClickListener(register_owner_click_listener);
        imgOwnerRegister.setOnClickListener(register_owner_click_listener);

        edtDestinationRegister.setOnClickListener(destination_register_click_listener);
        imgDestinationRegister.setOnClickListener(destination_register_click_listener);
        btnLetsRegister.setOnClickListener(search_bus_listener);
    }

    private View.OnClickListener start_place_click_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Chọn địa điểm")
                    .setSingleChoiceItems(provinces,-1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            edtStartRegister.setText(provinces[which]);
                            dialog.dismiss();
                            edtStartRegisterHint.setErrorEnabled(false);
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            params.rightMargin  = (int) Utilites.convertDpToPixel(5, mContext);
                            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                            params.addRule(RelativeLayout.CENTER_VERTICAL);
                            imgSelectStartRegister.setLayoutParams(params);
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    };


    private View.OnClickListener register_owner_click_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Chọn nhà xe")
                    .setSingleChoiceItems(provinces,-1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            edtOwnerRegister.setText(provinces[which]);
                            dialog.dismiss();
                            edtOwnerRegisterHint.setErrorEnabled(false);
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            params.rightMargin  = (int) Utilites.convertDpToPixel(5, mContext);
                            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                            params.addRule(RelativeLayout.CENTER_VERTICAL);
                            imgOwnerRegister.setLayoutParams(params);

                            layoutRoute.setVisibility(View.VISIBLE);
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    };


    private View.OnClickListener destination_register_click_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Chọn địa điểm")
                    .setSingleChoiceItems(provinces,-1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            edtDestinationRegister.setText(provinces[which]);
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
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                params.rightMargin  = (int) Utilites.convertDpToPixel(5, mContext);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.topMargin = (int) Utilites.convertDpToPixel(12, mContext);
                imgSelectStartRegister.setLayoutParams(params);
                return;
            }
            if (destinationPlace == null || destinationPlace.equals("")) {
                edtDestinationRegisterHint.setError("Bạn chưa nhập điểm đến");
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                params.rightMargin  = (int) Utilites.convertDpToPixel(5, mContext);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.topMargin = (int) Utilites.convertDpToPixel(12, mContext);
                imgDestinationRegister.setLayoutParams(params);
                return;
            }
            Intent intent = new Intent(mContext, RegisterActivity.class);
            //intent.putExtra(Defines.PROVINCE_FROM_ACTION, startPlace);
            //intent.putExtra(Defines.PROVINCE_TO_ACTION, destinationPlace);
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