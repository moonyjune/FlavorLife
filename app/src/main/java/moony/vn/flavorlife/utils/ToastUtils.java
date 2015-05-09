package moony.vn.flavorlife.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by moony on 5/9/15.
 */
public class ToastUtils {
    public static void showToastShort(Context context, String mess) {
        Toast.makeText(context, mess, Toast.LENGTH_SHORT).show();
    }

    public static void showToastLong(Context context, String mess) {
        Toast.makeText(context, mess, Toast.LENGTH_LONG).show();
    }
}
