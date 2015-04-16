package moony.vn.flavorlife;

import android.app.Application;
import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.ntq.imageloader.NImageLoader;
import com.ntq.imageloader.NImageLoaderImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import moony.vn.flavorlife.analytics.AppAnalytics;
import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.api.ApiImpl;
import moony.vn.flavorlife.api.ApiProvider;
import moony.vn.flavorlife.entities.User;
import moony.vn.flavorlife.utils.AppPrefers;

public class FlavorLifeApplication extends Application implements ApiProvider {
    private static FlavorLifeApplication mFlavorLifeApplication;
    private User mUser;
    private RequestQueue mRequestQueue;
    private Cache mCache;
    private NImageLoader mNtqImageLoader;
    private AppAnalytics mAppAnalytic;

    @Override
    public void onCreate() {
        super.onCreate();
        mFlavorLifeApplication = this;
        mCache = new DiskBasedCache(getCacheDir("caches"));
        mRequestQueue = new RequestQueue(mCache, createNetwork());
        mNtqImageLoader = new NImageLoaderImpl(getApplicationContext());
        mRequestQueue.start();
        mAppAnalytic = AppAnalytics.getInstance(this);
        mUser = restoreUser();
        if (mUser == null) {
            mUser = new User();
            mUser.setId(1);
            mUser.setState(User.State.LOGGED_OUT);
            storeUser();
        }
    }

    private static Network createNetwork() {
        return new BasicNetwork(new HurlStack());
    }

    public static FlavorLifeApplication get() {
        return mFlavorLifeApplication;
    }

    private static File getCacheDir(String name) {
        File file = new File(mFlavorLifeApplication.getCacheDir(), name);
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

    public void storeUser() {
        try {
            FileOutputStream fos = openFileOutput("user", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(mUser);
            os.close();
        } catch (IOException e) {

        }
    }

    public void updateIdUser(int userId) {
        mUser.setId(userId);
        storeUser();
    }

    public void updateStateUser(boolean isLoggedIn) {
        if (isLoggedIn) {
            mUser.setState(User.State.LOGGED_IN);
            AppPrefers.getInstance().saveUserState(1);
        } else {
            AppPrefers.getInstance().saveUserState(2);
            mUser.setState(User.State.LOGGED_OUT);
        }
        storeUser();
    }

    public User restoreUser() {
        try {
            FileInputStream fis = openFileInput("user");
            ObjectInputStream is = new ObjectInputStream(fis);
            User user = (User) is.readObject();
            int state = AppPrefers.getInstance().getUserState();
            user.setState(state);
            is.close();
            return user;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User mUser) {
        this.mUser = mUser;
    }
}
