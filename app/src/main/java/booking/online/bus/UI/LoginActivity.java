package booking.online.bus.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private EditText edtName, edtPass;
    private TextInputLayout newName, newPass;
    private Button btnLogin;
    private ProgressDialog dialog;
    private TextView txtRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(booking.online.bus.R.layout.activity_login);
        initComponents();

    }

    private void initComponents() {
        Toolbar toolbar         = (Toolbar)             findViewById(booking.online.bus.R.id.toolbar);
        edtName                 = (EditText)            findViewById(booking.online.bus.R.id.edt_name);
        edtPass                 = (EditText)            findViewById(booking.online.bus.R.id.edt_pass);
        newName                 = (TextInputLayout)     findViewById(booking.online.bus.R.id.new_name);
        newPass                 = (TextInputLayout)     findViewById(booking.online.bus.R.id.new_pass);
        btnLogin                = (Button)              findViewById(booking.online.bus.R.id.btn_login);
        txtRegister             = (TextView)            findViewById(booking.online.bus.R.id.txt_register);
        txtRegister.setText(booking.online.bus.R.string.register_notice);
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
            requestLogin(name, pass);

        }
    };

    private void requestLogin(String name, String pass) {
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
