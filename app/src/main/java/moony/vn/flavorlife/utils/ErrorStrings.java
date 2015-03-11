package moony.vn.flavorlife.utils;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import moony.vn.flavorlife.api.ReceivedServerError;
import moony.vn.flavorlife.R;

/**
 * @author TUNGDX
 */
public class ErrorStrings {
    public static String get(Context context, VolleyError volleyerror) {
        String s = context.getString(R.string.error_unknown);
        if (volleyerror instanceof AuthFailureError) {
            s = context.getString(R.string.error_authenticate);
        } else if (volleyerror instanceof ServerError) {
            s = context.getString(R.string.error_server);
        } else if (volleyerror instanceof TimeoutError) {
            s = context.getString(R.string.error_timeout);
        } else if (volleyerror instanceof NetworkError) {
            s = context.getString(R.string.error_no_connection);
        } else if (volleyerror instanceof ReceivedServerError) {
            s = context.getString(((ReceivedServerError) volleyerror)
                    .getMessageRes());
        }
        return s;
    }
}
