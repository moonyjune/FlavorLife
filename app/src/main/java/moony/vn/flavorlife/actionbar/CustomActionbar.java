package moony.vn.flavorlife.actionbar;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;

import moony.vn.flavorlife.navigationmanager.NavigationManager;

public interface CustomActionbar {
    /**
     * Initialize Actionbar for Activity. Should call in
     * {@link Activity#onCreate(Bundle)} after setContentView(...).
     *
     * @param navigationmanager
     * @param activity
     */
    public abstract void initialize(NavigationManager navigationmanager,
                                    ActionBarActivity activity);

    /**
     * Sync state actionbar for specific fragment.
     *
     * @param activePage Fragment that current displayed.
     */
    public void syncActionBar(Fragment activePage);
    public void dispose();
    public void hide();
    public void show();
}
