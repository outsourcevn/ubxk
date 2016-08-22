package booking.online.bus.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import booking.online.bus.R;
import booking.online.bus.Utilities.Defines;
import booking.online.bus.Utilities.SharePreference;
import booking.online.bus.Utilities.Utilites;

public class MainActivity extends AppCompatActivity {
    private TextView txtIntroduce, txtContact;
    private Button btnPassenger, btnBusOwner;
    private SharePreference preference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
    }


    /*
    Initiate all component of activity
    */
    private void initComponents() {
        txtIntroduce            =       (TextView)      findViewById(R.id.txt_about_me);
        txtContact              =       (TextView)      findViewById(R.id.txt_contact);
        btnPassenger            =       (Button)        findViewById(R.id.btn_passenger);
        btnBusOwner             =       (Button)        findViewById(R.id.btn_bus_owner);


        //set text about me
        txtIntroduce.setText(Html.fromHtml(getString(R.string.about_me)));
        txtContact.setText(Html.fromHtml(getString(R.string.contact_me)));

        txtIntroduce.setOnClickListener(term_of_use_listener);
        txtContact.setOnClickListener(contact_me_listener);
        btnPassenger.setOnClickListener(passenger_click_listener);
        btnBusOwner.setOnClickListener(bus_owner_click_listener);

        FirebaseMessaging.getInstance().subscribeToTopic("test");
        FirebaseInstanceId.getInstance().getToken();
        Log.e("TAG", Defines.token);

    }


    /*
    Passenger click event
    */

    private View.OnClickListener passenger_click_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, SearchActiveBusScreen.class);
            startActivity(intent);
        }
    };

     /*
    Bus owner click event
    */

    private View.OnClickListener bus_owner_click_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    };


    private View.OnClickListener term_of_use_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, TermOfUseActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener contact_me_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, ContactActivty.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Utilites.systemUiVisibility(this);
    }
}
