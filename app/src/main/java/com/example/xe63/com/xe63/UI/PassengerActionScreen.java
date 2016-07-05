package com.example.xe63.com.xe63.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.xe63.com.xe63.R;

public class PassengerActionScreen extends AppCompatActivity {

    private Button btnSearchBus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_action_screen);
        initComponents();
    }

    /*
   Initiate all component of activity
   */
    private void initComponents() {
        btnSearchBus        =       (Button)        findViewById(R.id.btn_find_bus);

        btnSearchBus.setOnClickListener(search_bus_listener);
    }

    private View.OnClickListener search_bus_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(PassengerActionScreen.this, SearchActiveBusScreen.class);
            startActivity(intent);
        }
    };
}
