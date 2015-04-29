package moony.vn.flavorlife.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.fragments.ComingSoonFragment;
import moony.vn.flavorlife.fragments.CreateRecipeFragment;
import moony.vn.flavorlife.fragments.FollowsFragment;
import moony.vn.flavorlife.fragments.HomeFragment;
import moony.vn.flavorlife.fragments.LoginFragment;
import moony.vn.flavorlife.fragments.NewRecipesFragment;

/**
 * Created by moony on 4/29/15.
 */
public class MainActivity extends BaseActivity {
    public static final int EXTRA_OPEN_LOGIN = 1;
    public static final int EXTRA_OPEN_NEWSRECIPE = 2;
    public static final int EXTRA_OPEN_FOLLOWS = 3;
    public static final int EXTRA_OPEN_CREATE_RECIPE = 4;
    public static final int EXTRA_OPEN_NOTIFICATION = 5;
    public static final int EXTRA_OPEN_HOME = 6;

    public static final String ACTION_OPEN = "open";
    public static final String ACTION_TAG = "tag";

    public static void startMainActivity(Context context, int extra) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(ACTION_OPEN, extra);
        intent.putExtra(KEY_CLEAR_ALL_STACK, true);
        context.startActivity(intent);
    }

    public static void startMainActivity(Context context, int extra, int tag) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(ACTION_OPEN, extra);
        intent.putExtra(ACTION_TAG, tag);
        intent.putExtra(KEY_CLEAR_ALL_STACK, true);
        context.startActivity(intent);
    }

//    public static void startLoginScreen(Context context) {
//        Intent intent = new Intent(context, MainActivity.class);
//        intent.putExtra(ACTION_OPEN, EXTRA_OPEN_LOGIN);
//        intent.putExtra(KEY_CLEAR_ALL_STACK, true);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTabWidget.setVisibility(View.GONE);
//        mTabWidget.unFocusAll();
        if (savedInstanceState == null)
            parseIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        parseIntent(intent);
    }

    private void parseIntent(Intent intent) {
        if (intent == null) return;
        int extra = intent.getIntExtra(ACTION_OPEN, -1);
        int tag = intent.getIntExtra(ACTION_TAG, -1);
        mTabWidget.setVisibility(View.VISIBLE);
        Fragment fragment = null;
        switch (extra) {
            case EXTRA_OPEN_LOGIN:
                replaceFragment(fragment = new LoginFragment());
                mTabWidget.setVisibility(View.GONE);
                break;
            case EXTRA_OPEN_NEWSRECIPE:
                replaceFragment(fragment = new NewRecipesFragment());
                break;
            case EXTRA_OPEN_FOLLOWS:
                replaceFragment(fragment = new FollowsFragment());
                break;
            case EXTRA_OPEN_CREATE_RECIPE:
                replaceFragment(fragment = new CreateRecipeFragment());
                break;
            case EXTRA_OPEN_NOTIFICATION:
                replaceFragment(fragment = new ComingSoonFragment());
                break;
            case EXTRA_OPEN_HOME:
                replaceFragment(fragment = new HomeFragment());
                break;
        }
        mActionbar.syncActionBar(fragment);
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, fragment).commit();
    }
}
