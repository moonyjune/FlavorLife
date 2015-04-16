package moony.vn.flavorlife.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.ntq.imageloader.NImageLoader;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.Cookbook;
import moony.vn.flavorlife.navigationmanager.NavigationManager;

/**
 * Created by moony on 3/11/15.
 */
public class CookbookView extends LinearLayout {
    public CookbookView(Context context) {
        super(context);
        init(context);
    }

    public CookbookView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CookbookView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public CookbookView(Context context, NImageLoader imageLoader, NavigationManager navigationManager) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.item_cookbook, this);
    }

    public void display(Cookbook cookbook) {

    }
}
