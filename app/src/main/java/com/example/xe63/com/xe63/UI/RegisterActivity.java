package com.example.xe63.com.xe63.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.xe63.com.xe63.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtName, edtPass;
    private TextInputLayout newName, newPass;
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
        newName                 = (TextInputLayout)     findViewById(R.id.new_name);
        newPass                 = (TextInputLayout)     findViewById(R.id.new_pass);
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
            String name = edtName.getText().toString();
            String pass = edtPass.getText().toString();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
