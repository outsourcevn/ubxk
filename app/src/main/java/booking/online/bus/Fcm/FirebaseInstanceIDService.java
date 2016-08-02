package booking.online.bus.Fcm;

import android.app.Activity;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import booking.online.bus.Utilities.Defines;
import booking.online.bus.Utilities.SharePreference;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    private String token;
    @Override
    public void onTokenRefresh() {

        token = FirebaseInstanceId.getInstance().getToken();
        Defines.token = token;
        SharePreference preference = new SharePreference(this);

        preference.saveToken(Defines.token);
        registerToken(token);
    }

    private void registerToken(String token) {

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("Token",token)
                .build();

        Request request = new Request.Builder()
                .url("http://127.0.0.1/fcm/register.php")
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
