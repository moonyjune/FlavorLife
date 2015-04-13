package moony.vn.flavorlife.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ntq.api.model.OnDataChangedListener;
import com.ntq.imageloader.NImageLoader;

import moony.vn.flavorlife.FlavorLifeApplication;
import moony.vn.flavorlife.R;
import moony.vn.flavorlife.api.model.DfeFollow;
import moony.vn.flavorlife.entities.Cookbook;
import moony.vn.flavorlife.entities.Follow;
import moony.vn.flavorlife.navigationmanager.NavigationManager;

/**
 * Created by moony on 3/11/15.
 */
public class ItemFollowView extends LinearLayout implements View.OnClickListener, OnDataChangedListener, Response.ErrorListener {
    private TextView mName, mNumberBook, mNumberRecipe, mNumberFollower;
    private DfeFollow mDfeFollow;
    private Follow mFollow;

    public ItemFollowView(Context context) {
        super(context);
        init(context);
    }

    public ItemFollowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ItemFollowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ItemFollowView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public ItemFollowView(Context context, NImageLoader imageLoader, NavigationManager navigationManager) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.item_follow, this);
        mName = (TextView) findViewById(R.id.user_name);
        mNumberBook = (TextView) findViewById(R.id.user_num_book);
        mNumberRecipe = (TextView) findViewById(R.id.user_num_recipe);
        mNumberFollower = (TextView) findViewById(R.id.user_num_follower);
        findViewById(R.id.follow).setOnClickListener(this);
    }

    public void display(Follow follow) {
        if(follow == null) return;
        mFollow = follow;
        mName.setText(follow.getEmail());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.follow:
                requestFollow();
                break;
        }
    }

    private void requestFollow(){
        if(mDfeFollow == null) {
            mDfeFollow = new DfeFollow(FlavorLifeApplication.get().getDfeApi());
            mDfeFollow.addDataChangedListener(this);
            mDfeFollow.addErrorListener(this);
        }
        mDfeFollow.makeRequest(mFollow.getId());
    }

    @Override
    public void onDataChanged() {
        System.out.println("Mj : onDataChanged");
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.println("Mj : onErrorResponse");
    }
}
