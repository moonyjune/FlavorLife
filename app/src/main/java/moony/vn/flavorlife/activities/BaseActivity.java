package moony.vn.flavorlife.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;

import com.ntq.activities.NActivity;
import com.ntq.imageloader.NImageLoader;
import com.ntq.utils.OSUtils;
import com.sromku.simple.fb.SimpleFacebook;

import moony.vn.flavorlife.FlavorLifeApplication;
import moony.vn.flavorlife.R;
import moony.vn.flavorlife.actionbar.CustomActionBarFactory;
import moony.vn.flavorlife.actionbar.CustomActionbar;
import moony.vn.flavorlife.analytics.AppAnalytics;
import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.fragments.CreateRecipeFragment;
import moony.vn.flavorlife.navigationmanager.NavigationManager;
import moony.vn.flavorlife.utils.AppPrefers;
import moony.vn.flavorlife.utils.DialogUtils;
import moony.vn.flavorlife.widget.TabWidget;

/**
 * Created by moony on 3/11/15.
 */
public abstract class BaseActivity extends NActivity implements TabWidget.OnTabChangeListener, View.OnClickListener {
    public static final String KEY_CLEAR_ALL_STACK = "clear_all_stack";
    //    private SlidingMenu mMenu;
    protected NavigationManager mNavigationManager;
    protected CustomActionbar mActionbar;
    protected TabWidget mTabWidget;
    private SimpleFacebook mSimpleFacebook;

    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle data = intent.getExtras();
            if (data != null) {
                mTabWidget.setNumNotification(AppPrefers.getInstance().getNumberNotifications());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.FLTheme);
        super.onCreate(savedInstanceState);
        mSimpleFacebook = SimpleFacebook.getInstance(this);
        if (!OSUtils.hasHoneycomb()) {
            supportRequestWindowFeature(Window.FEATURE_ACTION_BAR);
        }
        setContentView(R.layout.activity_base);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        initView();
        mNavigationManager = new NavigationManager(this);
        if (savedInstanceState != null) {
            mNavigationManager.deserialize(savedInstanceState);
        }
        mActionbar = CustomActionBarFactory.getInstance(this);
        mActionbar.initialize(mNavigationManager, this);

    }

    public Fragment getRootFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.content_layout);
    }

    ;

    @Override
    protected void onStart() {
        super.onStart();
//        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }

    @Override
    public NavigationManager getNavigationManager() {
        return mNavigationManager;
    }

    @Override
    public CustomActionbar getActionbar() {
        return mActionbar;
    }

    @Override
    public NImageLoader getImageLoader() {
        return FlavorLifeApplication.get().getImageLoader();
    }

    @Override
    public Api getDfeApi() {
        return FlavorLifeApplication.get().getDfeApi();
    }

    @Override
    public AppAnalytics getAnalytics() {
        return FlavorLifeApplication.get().getAnalytic();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mNavigationManager.serialize(outState);
    }

    private void initView() {
        mTabWidget = (TabWidget) findViewById(R.id.tab_indicator);
        mTabWidget.setTabMainChangeListener(this);
        mTabWidget.setNumNotification(AppPrefers.getInstance().getNumberNotifications());
    }

    @Override
    public void onNewRecipesTabSelected() {
        final Fragment activePage = mNavigationManager.getActivePage();
        if (activePage instanceof CreateRecipeFragment) {
            if (((CreateRecipeFragment) activePage).hasData()) {
                DialogUtils.getInstance().showDialogMessage(BaseActivity.this, "Are u sure that u wanna destroyed recipe u are creating ?", new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (BaseActivity.this instanceof CreateNewRecipeActivity)
                            BaseActivity.this.finish();
                        mNavigationManager.showNewRecipes();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogUtils.getInstance().dismissDialog();
                    }
                });
            } else {
                if (BaseActivity.this instanceof CreateNewRecipeActivity)
                    BaseActivity.this.finish();
                mNavigationManager.showNewRecipes();
            }
        } else {
            mNavigationManager.showNewRecipes();
        }
    }

    @Override
    public void onFollowsTabSelected() {
        final Fragment activePage = mNavigationManager.getActivePage();
        if (activePage instanceof CreateRecipeFragment) {
            if (((CreateRecipeFragment) activePage).hasData()) {
                DialogUtils.getInstance().showDialogMessage(BaseActivity.this, "Are u sure that u wanna destroyed recipe u are creating ?", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (BaseActivity.this instanceof CreateNewRecipeActivity)
                            BaseActivity.this.finish();
                        mNavigationManager.showFollows();
                    }
                }, null);
            } else {
                if (BaseActivity.this instanceof CreateNewRecipeActivity)
                    BaseActivity.this.finish();
                mNavigationManager.showFollows();
            }
        } else {
            mNavigationManager.showFollows();
        }
    }

    @Override
    public void onHomeTabSelected() {
        final Fragment activePage = mNavigationManager.getActivePage();
        if (activePage instanceof CreateRecipeFragment) {
            if (((CreateRecipeFragment) activePage).hasData()) {
                //TODO sua message tuong ung
                DialogUtils.getInstance().showDialogMessage(BaseActivity.this, "Are u sure that u wanna destroyed recipe u are creating ?", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (BaseActivity.this instanceof CreateNewRecipeActivity)
                            BaseActivity.this.finish();
                        mNavigationManager.showHome();
                    }
                }, null);
            } else {
                if (BaseActivity.this instanceof CreateNewRecipeActivity)
                    BaseActivity.this.finish();
                mNavigationManager.showHome();
            }
        } else {
            mNavigationManager.showHome();
        }
    }

    @Override
    public void onNotificationTabSelected() {
        final Fragment activePage = mNavigationManager.getActivePage();
        if (activePage instanceof CreateRecipeFragment) {
            if (((CreateRecipeFragment) activePage).hasData()) {
                DialogUtils.getInstance().showDialogMessage(BaseActivity.this, "Are u sure that u wanna destroyed recipe u are creating ?", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (BaseActivity.this instanceof CreateNewRecipeActivity)
                            BaseActivity.this.finish();
                        mNavigationManager.showNotification();
                    }
                }, null);
            } else {
                if (BaseActivity.this instanceof CreateNewRecipeActivity)
                    BaseActivity.this.finish();
                mNavigationManager.showNotification();
            }
        } else {
            mNavigationManager.showNotification();
        }
    }

    @Override
    public void onCreateRecipeTabSelected() {
        mNavigationManager.showCreateNewRecipe();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        Bundle extras = intent.getExtras();
        if (extras != null) {
            boolean clearAll = extras.getBoolean(KEY_CLEAR_ALL_STACK, false);
            if (clearAll)
                mNavigationManager.clearStack(null);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSimpleFacebook = SimpleFacebook.getInstance(this);
        if (this instanceof NewRecipesActivity) {
            mTabWidget.focusNewRecipesTab();
        } else if (this instanceof FollowsActivity) {
            mTabWidget.focusFollowsTab();
        } else if (this instanceof HomeActivity) {
            mTabWidget.focusHomeTab();
        } else if (this instanceof NotificationActivity) {
            mTabWidget.focusMessagesTab();
        } else if (this instanceof CreateNewRecipeActivity) {
            mTabWidget.focusCreateRecipeTab();
        }
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNavigationManager.terminate();
        mNavigationManager = null;
        mActionbar.dispose();
        mActionbar = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mSimpleFacebook.onActivityResult(this, requestCode, resultCode, data);
    }
}
