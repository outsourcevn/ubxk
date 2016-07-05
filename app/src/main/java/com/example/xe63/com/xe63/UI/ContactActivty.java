package com.example.xe63.com.xe63.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ScrollView;

import com.example.xe63.com.xe63.R;

public class ContactActivty extends AppCompatActivity {
    private ScrollView scrollContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_activty);
        WebView web                 = (WebView)             findViewById(R.id.contact);
        scrollContact               = (ScrollView)          findViewById(R.id.scroll_contact);
        Toolbar toolbar             =       (Toolbar)       findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Liên hệ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        scrollContact.setVerticalScrollBarEnabled(false);


        String htmlText = "<html><body style=\"text-align:justify\"> %s </body></Html>";
        web.loadData(String.format(htmlText, getString(R.string.contact)),"text/html; charset=UTF-8", null);

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