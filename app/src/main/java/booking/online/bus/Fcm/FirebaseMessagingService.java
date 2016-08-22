package booking.online.bus.Fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.messaging.RemoteMessage;

import booking.online.bus.Models.PassengerModel;
import booking.online.bus.R;
import booking.online.bus.database.LocalVideoDataSource;
import booking.online.bus.database.LocalVideoDatabase;

/**
 * Created by filipp on 5/23/2016.
 */
public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService{
    private LocalVideoDataSource database;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String[] arrayMess = remoteMessage.getData().get("message").split(",");
        if (arrayMess.length >2) {
            savePassengerToDB(remoteMessage.getData().get("message"));
            showNotification(remoteMessage.getData().get("message"));
            final Intent intent = new Intent("tokenReceiver");
            // You can also include some extra data.
            final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
            intent.putExtra("message",remoteMessage.getData().get("message"));
            broadcastManager.sendBroadcast(intent);

        }else{
            responseForPassenger(remoteMessage.getData().get("message"));
        }

    }

    private void responseForPassenger(String message) {
        String[] arrayMess = message.split(",");
        if (Integer.valueOf(arrayMess[1]) == 1){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setAutoCancel(true)
                    .setContentTitle("Xe63 thông báo đặt vé")
                    .setContentText("Bạn đã đặt vé thành công")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setVibrate(new long[] {1, 1, 1});
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            manager.notify(0,builder.build());
        }else{
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setAutoCancel(true)
                    .setContentTitle("Xe63 thông báo đặt vé")
                    .setContentText("Đặt vé thất bại")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setVibrate(new long[] {1, 1, 1});
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            manager.notify(0,builder.build());
        }
    }

    private void savePassengerToDB(String message) {
        String[] arrayMess = message.split(",");
        PassengerModel passenger = new PassengerModel(arrayMess[3],arrayMess[1],arrayMess[0],arrayMess[2],Integer.valueOf(arrayMess[4]),false);
        database = new LocalVideoDataSource(this);
        database.createPassenger(passenger);

    }

    private void showNotification(String message) {

        Intent intent = new Intent(this,FirebaseMessagingService.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        String[] arrayMess = message.split(",");
        String[] events = new String[3];
        events[0] = "Tên khách hàng: "+ arrayMess[1];
        events[1] = "Số điện thoại: "+arrayMess[0];
        events[2] = "Chú ý : "+arrayMess[2];
        int id = Integer.valueOf(arrayMess[3].substring(7,arrayMess[3].length()));
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        //Broatcast accept
        Intent accept = new Intent("com.example.cancel");
        accept.putExtra("notificationId",id);
        accept.putExtra("taixeId",Integer.valueOf(arrayMess[4]));
        accept.putExtra("uiid",arrayMess[3]);
        accept.putExtra("type",1);
        PendingIntent acceptIntent = PendingIntent.getBroadcast(this, 0, accept,PendingIntent.FLAG_CANCEL_CURRENT);


        // Broatcast deny
        Intent deny = new Intent("com.notification.deny");
        deny.putExtra("notificationId",id);
        deny.putExtra("taixeId",Integer.valueOf(arrayMess[4]));
        deny.putExtra("uiid",arrayMess[3]);
        deny.putExtra("type",0);
        PendingIntent denyIntent = PendingIntent.getBroadcast(this, 0, deny,PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("Xe63 thông báo đặt vé")
                .setContentText("Hãy xử lý những thông báo trước")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[] {1, 1, 1})
                .addAction(R.mipmap.accept, "Chấp nhận", acceptIntent)
                .addAction(R.mipmap.cancel, "Từ chối", denyIntent)
                .setContentIntent(pendingIntent);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        // Sets a title for the Inbox style big view
        inboxStyle.setBigContentTitle("Xe63 thông báo đặt vé");
        // Moves events into the big view
        for (int i = 0; i < events.length; i++) {

            inboxStyle.addLine(events[i]);
        }
        // Moves the big view style object into the notification object.
        builder.setStyle(inboxStyle);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(id,builder.build());
    }


}
