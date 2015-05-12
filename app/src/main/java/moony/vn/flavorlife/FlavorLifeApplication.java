package moony.vn.flavorlife;

import android.app.Application;
import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.facebook.SessionDefaultAudience;
import com.ntq.imageloader.NImageLoader;
import com.ntq.imageloader.NImageLoaderImpl;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;

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
    private static final String APP_ID = "719478654805827";
    private static final String APP_NAMESPACE = "moony_flavorlife";
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

        //Config facebook
        Permission[] permissions = new Permission[]{
                Permission.PUBLIC_PROFILE,
                Permission.PUBLISH_ACTION
        };

        SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
                .setAppId(APP_ID)
                .setNamespace(APP_NAMESPACE)
                .setPermissions(permissions)
                .setDefaultAudience(SessionDefaultAudience.FRIENDS)
                .setAskForAllPermissionsAtOnce(false)
                .build();

        SimpleFacebook.setConfiguration(configuration);
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

    public void updateCheckUpdatedProfile(boolean check){
        mUser.setUpdatedProfile(check);
        storeUser();
    }

    public void updateEmail(String email){
        mUser.setEmail(email);
        storeUser();
    }

    public void updateUserName(String userName) {
        mUser.setUserName(userName);
        storeUser();
    }

    public void updateSocialNetworkImage(String image) {
        mUser.setSocialNetworkImage(image);
        storeUser();
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

    public boolean clearUser() {
        mUser.clear();
        return deleteFile("user");
    }

    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                    System.out.println("TAG = **************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
                }
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }
}
