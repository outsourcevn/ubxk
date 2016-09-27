package booking.online.bus.UI;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import booking.online.bus.Controller.VehicleAdapter;
import booking.online.bus.Models.BusInfor;
import booking.online.bus.R;
import booking.online.bus.Utilities.BaseService;
import booking.online.bus.Utilities.Defines;
import booking.online.bus.Utilities.SharePreference;
import booking.online.bus.Utilities.Utilites;

public class BookTicketActivity extends AppCompatActivity {
    private BusInfor busBook;
    private TextView txtAboutBus, txtOwnerBus, txtToPlace, txtFromPlace, txtTicketPrice, txtNote;
    private LinearLayout layoutTelephone;
    private Context mContext;
    private String provinceFrom , provinceTo;
    private Button btnBookNow;
    private String regId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket);
        mContext = this;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            busBook = (BusInfor) extras.getSerializable(Defines.VEHICLE_PASS_ACTION);
            provinceFrom    = extras.getString(Defines.PROVINCE_FROM_ACTION);
            provinceTo      = extras.getString(Defines.PROVINCE_TO_ACTION);
        }
        Utilites.systemUiVisibility(this);
        initComponents();
    }

    private void initComponents() {
        txtAboutBus             = (TextView)        findViewById(R.id.txt_about_drive);
        txtOwnerBus             = (TextView)        findViewById(R.id.txt_owner_bus);
        txtToPlace              = (TextView)        findViewById(R.id.txt_to_place);
        txtFromPlace            = (TextView)        findViewById(R.id.txt_from_place);
        txtTicketPrice          = (TextView)        findViewById(R.id.txt_ticket_price);
        txtNote                 = (TextView)        findViewById(R.id.txt_note);
        btnBookNow              = (Button)          findViewById(R.id.btn_book_now);
        Toolbar toolbar         = (Toolbar)         findViewById(R.id.toolbar);
        layoutTelephone         = (LinearLayout)    findViewById(R.id.layout_phone);
        // set Actionbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Đặt vé");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (busBook!= null) {
            txtAboutBus.setText("Chuyến đi " + provinceFrom + " - " + provinceTo);
            txtOwnerBus.setText(busBook.getCarOwner());
            txtFromPlace.setText(busBook.getFromPlace());
            txtToPlace.setText(busBook.getToPlace());
            txtNote.setText(busBook.getNote());
            //txtTelephone.setText(busBook.getTelephone());
            txtTicketPrice.setText(Utilites.convertCurrency(busBook.getPrice()));
            //txtTelephone.setOnClickListener(call_drive_listener);
            btnBookNow.setOnClickListener(book_now_listener);
            setTelePhone();
        }



    }

    private void setTelePhone() {
        String[] arrayPhone = busBook.getTelephone().split(";");
        for (int i = 0 ; i < arrayPhone.length; i++){
            LinearLayout layoutPhone = new LinearLayout(this);
            layoutPhone.setBackgroundColor(getResources().getColor(R.color.red));
            layoutPhone.setPadding((int)Utilites.convertDpToPixel(20,mContext),(int)Utilites.convertDpToPixel(10,mContext),(int)Utilites.convertDpToPixel(20,mContext),(int)Utilites.convertDpToPixel(10,mContext));

            TextView txtPhone = new TextView(this);
            txtPhone.setText(arrayPhone[i]);
            layoutPhone.addView(txtPhone);
            txtPhone.setTextColor(getResources().getColor(R.color.yellow));
            layoutTelephone.addView(layoutPhone);
            if (i< arrayPhone.length-1) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.rightMargin = (int) Utilites.convertDpToPixel(20, mContext);
                params.gravity = Gravity.CENTER;
                layoutPhone.setLayoutParams(params);
            }

            setClickPhone(layoutPhone,txtPhone);
        }
    }

    private void setClickPhone(LinearLayout layoutPhone, final TextView txtPhone) {
        layoutPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:" + txtPhone.getText()));
                if (Build.VERSION.SDK_INT >= 22) {
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.CALL_PHONE}, Defines.MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                        return;
                    }
                }
                mContext.startActivity(intent);
            }
        });
    }

    private View.OnClickListener book_now_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, ConfirmTicketActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putSerializable(Defines.VEHICLE_PASS_ACTION, busBook);
            intent.putExtras(mBundle);
            startActivity(intent);
        }
    };



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
