package moony.vn.flavorlife.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import moony.vn.flavorlife.R;

/**
 * Created by moony on 3/1/15.
 */
public class CreateRecipeView extends LinearLayout {
    private Context mContext;

    public CreateRecipeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CreateRecipeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CreateRecipeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public CreateRecipeView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        inflate(context, R.layout.tab_create_recipe, this);
    }

}
