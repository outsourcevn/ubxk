package com.example.xe63.com.xe63.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.xe63.com.xe63.R;

public class TermOfUseActivity extends AppCompatActivity {
    private ScrollView ScrollTermOfUse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_of_use);
        WebView web                 = (WebView)             findViewById(R.id.term_of_use);
        ScrollTermOfUse             = (ScrollView)          findViewById(R.id.scroll_term_of_use);
        Toolbar toolbar             =       (Toolbar)       findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Giới thiệu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ScrollTermOfUse.setVerticalScrollBarEnabled(false);


        String htmlText = "<html><body style=\"text-align:justify\"> %s </body></Html>";
        web.loadData(String.format(htmlText, getString(R.string.term_of_use)),"text/html; charset=UTF-8", null);

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
