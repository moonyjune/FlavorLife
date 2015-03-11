package moony.vn.flavorlife.gcm;

import java.io.IOException;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import moony.vn.flavorlife.Config;

public class GcmRegisterService extends IntentService {

    public GcmRegisterService() {
        super("RegisterRegIdService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (TextUtils.isEmpty(GcmUtils
                .getRegistrationId(getApplicationContext()))) {
            try {
                // (1)Register in GCM service of Google.
                GoogleCloudMessaging gcm = GoogleCloudMessaging
                        .getInstance(getApplicationContext());
                String regid = gcm.register(Config.SENDER_ID);
                // TODO (2)send regId to backend.

                // (3) If success, persist the regID - no need to register
                // again.
                GcmUtils.storeRegistrationId(getApplicationContext(), regid);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
