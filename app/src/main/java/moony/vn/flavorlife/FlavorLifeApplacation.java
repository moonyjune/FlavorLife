package moony.vn.flavorlife;

import java.io.File;

import android.app.Application;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.api.ApiImpl;
import moony.vn.flavorlife.api.ApiProvider;
import moony.vn.flavorlife.analytics.AppAnalytics;

import com.ntq.imageloader.NImageLoader;
import com.ntq.imageloader.NImageLoaderImpl;

public class FlavorLifeApplacation extends Application implements ApiProvider {
    private static FlavorLifeApplacation mFlavorLifeApplacation;
    private RequestQueue mRequestQueue;
    private Cache mCache;
    private NImageLoader mNtqImageLoader;
    private AppAnalytics mAppAnalytic;

    @Override
    public void onCreate() {
        super.onCreate();
        mFlavorLifeApplacation = this;
        mCache = new DiskBasedCache(getCacheDir("caches"));
        mRequestQueue = new RequestQueue(mCache, createNetwork());
        mNtqImageLoader = new NImageLoaderImpl(getApplicationContext());
        mRequestQueue.start();
        mAppAnalytic = AppAnalytics.getInstance(this);
    }

    private static Network createNetwork() {
        return new BasicNetwork(new HurlStack());
    }

    public static FlavorLifeApplacation get() {
        return mFlavorLifeApplacation;
    }

    private static File getCacheDir(String name) {
        File file = new File(mFlavorLifeApplacation.getCacheDir(), name);
        file.mkdirs();
        return file;
    }

    public NImageLoader getImageLoader() {
        return mNtqImageLoader;
    }

    @Override
    public Api getDfeApi() {
        return new ApiImpl(mRequestQueue);
    }

    public AppAnalytics getAnalytic() {
        return mAppAnalytic;
    }
}
