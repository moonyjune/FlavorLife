package moony.vn.flavorlife.gcm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import moony.vn.flavorlife.utils.AppPrefers;

public class GcmIntentService extends IntentService {
    public static final String NOTIFICATION = "moony.vn.flavorlife";
    private NotificationManager mNotificationManager;
    public static final int NOTIFICATION_ID = 1;
    public static final String ACTION_SEND_NOTIFICATION = "ACTION_SEND_NOTIFICATION";

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);
        if (extras == null) return;
        if (!extras.isEmpty()) { // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that
			 * GCM will be extended in the future with new message types, just
			 * ignore any message types you're not interested in, or that you
			 * don't recognize.
			 */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
                    .equals(messageType)) {
                // error
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
                    .equals(messageType)) {
                // deleted message on server
                // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
                    .equals(messageType)) {
                // Post notification of received message.
//                System.out.println(extras.get("title") + " - " + intent.toString());
//                sendBroadcast(intent);

                sendNotification(extras);
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    public void sendNotification(Bundle bundle) {
//        String msg = bundle.getString("message");
//        mNotificationManager = (NotificationManager)
//                this.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        Intent startActivityIntent = new Intent(this, BaseActivity.class);
//        Bundle articleBundle = new Bundle();
//        startActivityIntent.putExtras(articleBundle);
//        startActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//                startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
//
//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.ic_launcher)
//                        .setContentTitle(getResources().getString(R.string.app_name))
//                        .setStyle(new NotificationCompat.BigTextStyle()
//                                .bigText(msg))
//                        .setContentText(msg).setAutoCancel(true);
//
//        mBuilder.setContentIntent(contentIntent);
//        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        int numberNotifications = AppPrefers.getInstance().getNumberNotifications();
        AppPrefers.getInstance().saveNumberNotifications(numberNotifications + 1);
        sendNotificationNumber();
    }

    private void sendNotificationNumber() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        intent.putExtra("data", bundle);
        intent.setAction(ACTION_SEND_NOTIFICATION);
        sendBroadcast(intent);
    }


}