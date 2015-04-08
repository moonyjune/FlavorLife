package moony.vn.flavorlife.activities;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.ntq.activities.NActivity;
import com.ntq.api.model.OnDataChangedListener;
import com.ntq.imageloader.NImageLoader;
import com.ntq.utils.OSUtils;

import moony.vn.flavorlife.FlavorLifeApplication;
import moony.vn.flavorlife.R;
import moony.vn.flavorlife.actionbar.CustomActionBarFactory;
import moony.vn.flavorlife.actionbar.CustomActionbar;
import moony.vn.flavorlife.analytics.AppAnalytics;
import moony.vn.flavorlife.api.Api;
import moony.vn.flavorlife.navigationmanager.NavigationManager;
import moony.vn.flavorlife.utils.ErrorStrings;
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
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!OSUtils.hasHoneycomb()) {
            supportRequestWindowFeature(Window.FEATURE_ACTION_BAR);
        }
        setContentView(R.layout.activity_base);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
//        setUpSlidingMenu();
        initView();
        mNavigationManager = new NavigationManager(this);
        if (savedInstanceState != null) {
            mNavigationManager.deserialize(savedInstanceState);
        }
        mActionbar = CustomActionBarFactory.getInstance(this);
        mActionbar.initialize(mNavigationManager, this);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_layout);
        if (fragment == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, getRootFragment()).commit();
        }
    }

    public abstract Fragment getRootFragment();

    @Override
    protected void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
//        mMenu.showContent();
    }

    @Override
    protected void onStop() {
        super.onStop();
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }

//    public SlidingMenu getMenu() {
//        return mMenu;
//    }

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

//    private void setUpSlidingMenu() {
//        mMenu = new SlidingMenu(this);
//        mMenu.setMode(SlidingMenu.RIGHT);
//        mMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
//        mMenu.setShadowWidthRes(R.dimen.shadow_width);
//        mMenu.setShadowDrawable(R.drawable.shadow);
//        mMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
//        mMenu.setFadeDegree(0.35f);
//        mMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
//        mMenu.setSecondaryMenu(R.layout.fragment_app_menu);
//        mMenu.setSlidingEnabled(false);
//    }

    private void initView() {
        mTabWidget = (TabWidget) findViewById(R.id.tab_indicator);
        mTabWidget.setTabMainChangeListener(this);
    }

    @Override
    public void onNewRecipesTabSelected() {
        mNavigationManager.showNewRecipes();
    }

    @Override
    public void onFollowsTabSelected() {
        mNavigationManager.showFollows();
    }

    @Override
    public void onHomeTabSelected() {
        mNavigationManager.showHome();
    }

    @Override
    public void onMessageTabSelected() {
        mNavigationManager.showMessages();
    }

    @Override
    public void onCreateRecipeTabSelected() {
        mNavigationManager.showCreateNewRecipe();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
//        mMenu.showContent();
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
        if (this instanceof NewRecipesActivity) {
            mTabWidget.focusNewRecipesTab();
        } else if (this instanceof FollowsActivity) {
            mTabWidget.focusFollowsTab();
        } else if (this instanceof HomeActivity) {
            mTabWidget.focusHomeTab();
        } else if (this instanceof MessagesActivity) {
            mTabWidget.focusMessagesTab();
        } else if (this instanceof CreateNewRecipeActivity) {
            mTabWidget.focusCreateRecipeTab();
        }
    }

    @Override
    public void onClick(View v) {
    }

    private void showDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(this, null, getString(R.string.waiting), true, false);
        } else if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    private void dismissDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNavigationManager.terminate();
        mNavigationManager = null;
        mActionbar.dispose();
        mActionbar = null;
    }
}
