package moony.vn.flavorlife.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.ntq.imageloader.NImageLoader;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.Follower;
import moony.vn.flavorlife.entities.Message;
import moony.vn.flavorlife.navigationmanager.NavigationManager;

/**
 * Created by moony on 3/11/15.
 */
public class ItemMessageView extends LinearLayout {
    public ItemMessageView(Context context) {
        super(context);
        init(context);
    }

    public ItemMessageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ItemMessageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ItemMessageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public ItemMessageView(Context context, NImageLoader imageLoader, NavigationManager navigationManager) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.item_message, this);
    }

    public void display(Message message) {

    }
}
