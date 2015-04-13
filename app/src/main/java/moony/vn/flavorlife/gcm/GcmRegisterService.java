package moony.vn.flavorlife.gcm;

import java.io.IOException;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.ntq.api.model.OnDataChangedListener;

import moony.vn.flavorlife.Config;
import moony.vn.flavorlife.FlavorLifeApplication;
import moony.vn.flavorlife.api.model.DfeRegisterGcm;

public class GcmRegisterService extends IntentService {

    public GcmRegisterService() {
        super("RegisterRegIdService");
    }

    public static void startRegister(Context context) {
        if (TextUtils.isEmpty(GcmUtils
                .getRegistrationId(context.getApplicationContext()))) {
            Intent intent = new Intent(context, GcmRegisterService.class);
            context.startService(intent);
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        if (TextUtils.isEmpty(GcmUtils
//                .getRegistrationId(getApplicationContext()))) {
        try {
            // (1)Register in GCM service of Google.
            GoogleCloudMessaging gcm = GoogleCloudMessaging
                    .getInstance(getApplicationContext());
            String regid = gcm.register(Config.SENDER_ID);
            // TODO (2)send regId to backend.
//                requestGcm(regid);
            // (3) If success, persist the regID - no need to register
            // again.
            GcmUtils.storeRegistrationId(getApplicationContext(), regid);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        }
    }

}
