package booking.online.bus.Receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import booking.online.bus.Utilities.BaseService;
import booking.online.bus.Utilities.Defines;
import booking.online.bus.database.LocalVideoDataSource;

/**
 * Created by DatNT on 8/10/2016.
 */
public class NotificationDenyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        final int notificationId = intent.getIntExtra("notificationId", 0);
        int taixeId = intent.getIntExtra("taixeId", 0);
        int type = intent.getIntExtra("type", 0);
        final String uiid = intent.getStringExtra("uiid");
        // if you want cancel notification
        RequestParams params;
        params = new RequestParams();
        params.put("uiid", uiid);
        params.put("id", taixeId);
        params.put("type", type);
        Log.i("params deleteDelivery", params.toString());
        BaseService.getHttpClient().post(Defines.URL_ACCEPT, params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                // called when response HTTP status is "200 OK"
                Log.i("JSON", new String(responseBody));
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                manager.cancel(notificationId);
                LocalVideoDataSource database = new LocalVideoDataSource(context);
                database.deletePassenger(uiid);
                Intent intent = new Intent("tokenReceiver");
                final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(context);
                intent.putExtra("deny","deny");
                broadcastManager.sendBroadcast(intent);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("JSON", new String(responseBody));
            }

            @Override
            public void onRetry(int retryNo) {
            }
        });

    }
}
