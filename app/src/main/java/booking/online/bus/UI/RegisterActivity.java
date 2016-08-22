package booking.online.bus.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import booking.online.bus.Models.BusInfor;
import booking.online.bus.Models.OwnerCarObject;
import booking.online.bus.R;
import booking.online.bus.Utilities.BaseService;
import booking.online.bus.Utilities.Defines;
import booking.online.bus.Utilities.SharePreference;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtName, edtPass, edtPhone;
    private TextInputLayout newName, newPass, newPhone;
    private Button btnRegister;
    private ProgressDialog dialog;
    private OwnerCarObject ownerRegister;
    private SharePreference preference;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ownerRegister = (OwnerCarObject) extras.getSerializable(Defines.OWNER_ID_ACTION);
        }
        preference = new SharePreference(this);
        mContext = this;
        initComponents();

    }

    private void initComponents() {
        Toolbar toolbar         = (Toolbar)             findViewById(R.id.toolbar);
        edtName                 = (EditText)            findViewById(R.id.edt_name);
        edtPass                 = (EditText)            findViewById(R.id.edt_pass);
        edtPhone                = (EditText)            findViewById(R.id.edt_phone);

        newName                 = (TextInputLayout)     findViewById(R.id.new_name);
        newPhone                = (TextInputLayout)     findViewById(R.id.new_phone);
        newPass                = (TextInputLayout)     findViewById(R.id.new_pass);

        btnRegister             = (Button)              findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(register_click_listener);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.register));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }
    private View.OnClickListener register_click_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String name  = edtName.getText().toString();
            String pass  = edtPass.getText().toString();
            String phone = edtPhone.getText().toString();

            if (name == null || name.equals("")) {
                newName.setError("Hãy nhập biển số xe");
                requestFocus(edtName);
                return;
            }

            if (pass == null || pass.equals("")) {
                newPass.setError("Hãy nhập mật khẩu");
                requestFocus(edtPass);
                return;
            }

            if (phone == null || phone.equals("")) {
                newPhone.setError("Hãy nhập số điện thoại");
                requestFocus(edtPass);
                return;
            }

            requestRegister(name, pass, phone);

        }
    };

    private void requestRegister(final String name, String pass, final String phone) {
        RequestParams params;
        params = new RequestParams();
        params.put("id", ownerRegister.getId());
        params.put("password", pass);
        params.put("phone", phone);
        params.put("bienso", name);
        params.put("from", ownerRegister.getStartPlace());
        params.put("to", ownerRegister.getEndPlace());
        params.put("regId", preference.getToken());
        Log.i("params deleteDelivery", params.toString());
        dialog = new ProgressDialog(this);
        dialog.setMessage("Đang đăng ký. Vui lòng chờ...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        BaseService.getHttpClient().post(Defines.URL_REGISTER, params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                dialog.dismiss();
                Log.i("JSON", new String(responseBody));
                int result = Integer.valueOf(new String(responseBody));
                if (result != 0){
                    Toast.makeText(mContext, "Đăng ký thành công",Toast.LENGTH_SHORT).show();
                    preference.saveLogin();
                    preference.saveDriverPhone(phone);
                    preference.savePlaceFrom(ownerRegister.getProvinceFrom());
                    preference.savePlaceTo(ownerRegister.getProvinceTo());
                    preference.saveOwnerName(ownerRegister.getOwnerName());
                    preference.saveLicense(name);
                    preference.saveParkStart(ownerRegister.getStartPlace());
                    preference.saveParkEnd(ownerRegister.getEndPlace());
                    preference.saveDriverId(result);
                    Intent intent = new Intent(mContext, ListPassengerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

            }

            @Override
            public void onRetry(int retryNo) {

            }
        });
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
