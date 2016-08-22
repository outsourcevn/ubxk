package booking.online.bus.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ScrollView;

import booking.online.bus.Utilities.Utilites;

public class TermOfUseActivity extends AppCompatActivity {
    private ScrollView ScrollTermOfUse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(booking.online.bus.R.layout.activity_term_of_use);
        WebView web                 = (WebView)             findViewById(booking.online.bus.R.id.term_of_use);
        ScrollTermOfUse             = (ScrollView)          findViewById(booking.online.bus.R.id.scroll_term_of_use);
        Toolbar toolbar             =       (Toolbar)       findViewById(booking.online.bus.R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Giới thiệu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ScrollTermOfUse.setVerticalScrollBarEnabled(false);


        String htmlText = "<html><body style=\"text-align:justify\"> %s </body></Html>";
        web.loadData(String.format(htmlText, getString(booking.online.bus.R.string.term_of_use)),"text/html; charset=UTF-8", null);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Utilites.systemUiVisibility(this);
    }
}
