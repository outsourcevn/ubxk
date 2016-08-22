package booking.online.bus.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import booking.online.bus.Models.BusInfor;
import booking.online.bus.R;
import booking.online.bus.Utilities.BaseService;
import booking.online.bus.Utilities.Defines;
import booking.online.bus.Utilities.SharePreference;
import booking.online.bus.Utilities.Utilites;

public class ConfirmTicketActivity extends AppCompatActivity {

    private EditText edtName, edtPhone, edtNote;
    private TextInputLayout edtNameHint, edtPhoneHint, edtNoteHint;
    private ImageView imgGetPhone;
    private Context mContext;
    private Button btnComplete;
    private BusInfor busBook;
    private SharePreference preference;
    private CheckBox cbSave;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_ticket);
        mContext = this;
        initComponents();
    }

    private void initComponents() {
        edtName             =   (EditText)          findViewById(R.id.edt_confirm_name);
        edtPhone            =   (EditText)          findViewById(R.id.edt_confirm_phone);
        edtNote             =   (EditText)          findViewById(R.id.edt_confirm_note);

        edtNameHint         =   (TextInputLayout)   findViewById(R.id.edt_confirm_name_hint);
        edtPhoneHint        =   (TextInputLayout)   findViewById(R.id.edt_confirm_phone_hint);
        edtNoteHint         =   (TextInputLayout)   findViewById(R.id.edt_confirm_note_hint);

        imgGetPhone         =   (ImageView)         findViewById(R.id.btn_get_phone);

        btnComplete         =   (Button)            findViewById(R.id.btn_book_now);
        cbSave              =   (CheckBox)          findViewById(R.id.cb_save_infor);
        toolbar             = (Toolbar)             findViewById(R.id.toolbar);
        // set Actionbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Xác nhận ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            busBook = (BusInfor) extras.getSerializable(Defines.VEHICLE_PASS_ACTION);
        }
        preference = new SharePreference(this);
        imgGetPhone.setOnClickListener(get_current_phone_listener);
        btnComplete.setOnClickListener(complete_book_ticket_listener);

        if (!preference.getName().equals(""))
            edtName.setText(preference.getName());

        if (!preference.getPhone().equals(""))
            edtPhone.setText(preference.getPhone());
    }


    private View.OnClickListener get_current_phone_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TelephonyManager tMgr = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            String mPhoneNumber = tMgr.getLine1Number();
            if (mPhoneNumber == null || mPhoneNumber.equals("")){
                Utilites.showDialog(mContext,"Thông báo","Không lấy được số điện thoại. Mời bạn nhâp");
            }
            else
                edtPhone.setText(mPhoneNumber);

        }
    };
    private View.OnClickListener complete_book_ticket_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!CheckNullComponents())
                bookTicket();
        }
    };

    private boolean CheckNullComponents() {
        String name  = edtName.getText().toString();
        String phone  = edtPhone.getText().toString();

        if (name == null || name.equals("")) {
            edtNameHint.setError("Hãy nhập tên cuả bạn");
            requestFocus(edtName);
            return true;
        }

        if (phone == null || phone.equals("")) {
            edtPhoneHint.setError("Hãy nhập số điện thoại");
            requestFocus(edtPhone);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            params.rightMargin  = (int) Utilites.convertDpToPixel(5, mContext);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.topMargin = (int) Utilites.convertDpToPixel(12, mContext);
            imgGetPhone.setLayoutParams(params);
            return true;
        }
        return false;
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private void bookTicket() {
        saveInforIfChoosen();
        RequestParams params;
        params = new RequestParams();
        params.put("phone", edtPhone.getText().toString());
        params.put("name", edtName.getText().toString());
        params.put("note", edtNote.getText().toString());
        params.put("uiid", Utilites.getTimeStamp());
        params.put("idtaixe", busBook.getId());
        params.put("regid", preference.getToken());
        Log.i("params deleteDelivery", params.toString());
        BaseService.getHttpClient().post(Defines.URL_BOOK_TICKET, params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                // called when response HTTP status is "200 OK"
                Log.i("JSON", new String(responseBody));
                parseJsonResult(new String(responseBody));



            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
            }

            @Override
            public void onRetry(int retryNo) {
            }
        });
    }

    private void saveInforIfChoosen() {
        if (cbSave.isChecked())
            preference.saveName(edtName.getText().toString());
            preference.savePhone(edtPhone.getText().toString());
    }

    private void parseJsonResult(String response) {
        try {
            JSONObject content = new JSONObject(response);
            int result = content.getInt("success");
            if (result > 0)
            {
                new AlertDialog.Builder(mContext)
                        .setTitle("Thông báo")
                        .setMessage("Đặt vé thành công. Đợi chủ xe xác nhận")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })

                        .show();
            }else{
                Utilites.showDialog(mContext,"Thông báo","Đặt vé thất bại");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
}
