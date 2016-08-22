package booking.online.bus.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import booking.online.bus.Controller.VehicleAdapter;
import booking.online.bus.R;
import booking.online.bus.Utilities.BaseService;
import booking.online.bus.Utilities.Defines;
import booking.online.bus.Utilities.SharePreference;
import booking.online.bus.Utilities.Utilites;

public class LoginActivity extends AppCompatActivity {
    private EditText edtName, edtPass;
    private TextInputLayout newName, newPass;
    private Button btnLogin;
    private ProgressDialog dialog;
    private TextView txtRegister;
    private RelativeLayout root;
    private SharePreference preference;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(booking.online.bus.R.layout.activity_login);
        preference = new SharePreference(this);
        mContext = this;
        initComponents();

    }

    private void initComponents() {
        Toolbar toolbar         = (Toolbar)             findViewById(R.id.toolbar);
        edtName                 = (EditText)            findViewById(R.id.edt_name);
        edtPass                 = (EditText)            findViewById(R.id.edt_pass);
        newName                 = (TextInputLayout)     findViewById(R.id.new_name);
        newPass                 = (TextInputLayout)     findViewById(R.id.new_pass);
        btnLogin                = (Button)              findViewById(R.id.btn_login);
        txtRegister             = (TextView)            findViewById(R.id.txt_register);
        root                    = (RelativeLayout)      findViewById(R.id.root);
        txtRegister.setText(R.string.register_notice);
        txtRegister.setOnClickListener(register_click_listener);

        btnLogin.setOnClickListener(login_click_listener);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Đăng nhập");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }
    private View.OnClickListener login_click_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            letsLogin();
        }
    };

    private void letsLogin() {
        String name = edtName.getText().toString();
        String pass = edtPass.getText().toString();

        if (name == null || name.equals("")) {
            newName.setError("Hãy nhập biển số");
            requestFocus(edtName);
            return;
        }

        if (pass == null || pass.equals("")) {
            newPass.setError("Hãy nhập mật khẩu");
            requestFocus(edtPass);
            return;
        }
        if (Utilites.isOnline(mContext))
            requestLogin(name, pass);
        else{
            Snackbar snackbar = Snackbar
                    .make(root, "Không có kết nối mạng!", Snackbar.LENGTH_LONG)
                    .setAction("Thử lại", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            letsLogin();
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

    private void requestLogin(final String name, final String pass) {

        RequestParams params;
        params = new RequestParams();
        params.put("bienso", name);
        params.put("password", pass);
        params.put("regId", preference.getToken());
        Log.i("params deleteDelivery", params.toString());
        dialog = new ProgressDialog(this);
        dialog.setMessage("Đang đăng nhập. Vui lòng chờ...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        BaseService.getHttpClient().get(Defines.URL_LOGIN, params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                Log.i("JSON", new String(responseBody));
                dialog.dismiss();
                try {
                    JSONArray data = new JSONArray(new String(responseBody));
                    if(data.length()< 1){
                        Snackbar snackbar = Snackbar
                                .make(root, "Đăng nhập thất bại!", Snackbar.LENGTH_LONG)
                                .setAction("Thử lại", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        requestLogin(name, pass);
                                    }
                                });

                        // Changing message text color
                        snackbar.setActionTextColor(Color.RED);

                        // Changing action button text color
                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.YELLOW);
                        snackbar.show();
                        return;
                    }

                    JSONObject result = data.getJSONObject(0);
                    preference.saveDriverPhone(result.getString("phone"));
                    preference.saveLicense(result.getString("bienso"));
                    preference.savePlaceFrom(result.getString("from"));
                    preference.savePlaceTo(result.getString("to"));
                    preference.saveLogin();
                    Intent intent = new Intent(mContext, ListPassengerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
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
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private View.OnClickListener register_click_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, RegisterOwnerActivity.class);
            startActivity(intent);
            finish();
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
