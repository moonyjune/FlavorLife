package moony.vn.flavorlife.gcm;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by moony on 4/13/15.
 */
public class NotificationService extends IntentService {
    public static final String NOTIFICATION = "moony.vn.flavorlife";
    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Intent intent1 = new Intent(NOTIFICATION);
        sendBroadcast(intent1);
    }
}
