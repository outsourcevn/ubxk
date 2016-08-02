package booking.online.bus.UI;

import android.app.ProgressDialog;
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

import booking.online.bus.R;
import booking.online.bus.Utilities.BaseService;
import booking.online.bus.Utilities.Defines;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtName, edtPass, edtPhone;
    private TextInputLayout newName, newPass, newPhone;
    private Button btnRegister;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initComponents();

    }

    private void initComponents() {
        Toolbar toolbar         = (Toolbar)             findViewById(R.id.toolbar);
        edtName                 = (EditText)            findViewById(R.id.edt_name);
        edtPass                 = (EditText)            findViewById(R.id.edt_pass);
        edtPhone                = (EditText)            findViewById(R.id.edt_phone);

        newName                 = (TextInputLayout)     findViewById(R.id.new_name);
        newPhone                = (TextInputLayout)     findViewById(R.id.new_phone);
        newPhone                = (TextInputLayout)     findViewById(R.id.new_pass);

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
                newName.setError("Hãy nhập tên tài khoản");
                requestFocus(edtName);
                return;
            }

            if (pass == null || pass.equals("")) {
                newPass.setError("Hãy nhập mật khẩu");
                requestFocus(edtPass);
                return;
            }

            if (phone == null || phone.equals("")) {
                newPass.setError("Hãy nhập số điện thoại");
                requestFocus(edtPass);
                return;
            }

            requestLogin(name, pass, phone);

        }
    };

    private void requestLogin(String name, String pass, String phone) {
        RequestParams params;
        params = new RequestParams();
        params.put("id", name);
        params.put("password", pass);
        params.put("phone", phone);
        Log.i("params deleteDelivery", params.toString());
        BaseService.getHttpClient().post(Defines.URL_REGISTER, params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {

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
