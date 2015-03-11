package moony.vn.flavorlife.analytics;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import moony.vn.flavorlife.R;

public class AppAnalytics {
    private static AppAnalytics mAppAnalytic;
    private Tracker mTracker;
    private Context mContext;

    public static AppAnalytics getInstance(Context context) {
        if (mAppAnalytic == null) {
            mAppAnalytic = new AppAnalytics(context);
        }
        return mAppAnalytic;
    }

    public AppAnalytics(Context context) {
        GoogleAnalytics analytics = GoogleAnalytics.getInstance(context);
        mTracker = analytics.newTracker(
                R.xml.google_analytics);
        mContext = context.getApplicationContext();
    }

    private String getString(int res) {
        return mContext.getString(res);
    }

    public void sendScreenName(String screenName) {
        mTracker.setScreenName(screenName);
        mTracker.send(new HitBuilders.AppViewBuilder().build());
    }

    private void sendEvent(String category, String action, String label) {
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .setLabel(label)
                .build());
    }
}
