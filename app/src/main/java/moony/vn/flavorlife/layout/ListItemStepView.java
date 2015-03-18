package moony.vn.flavorlife.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.List;

import moony.vn.flavorlife.entities.Ingredient;
import moony.vn.flavorlife.entities.Step;

/**
 * Created by moony on 3/18/15.
 */
public class ListItemStepView extends LinearLayout {
    private List<Step> mStepList;

    public ListItemStepView(Context context) {
        super(context);
    }

    public ListItemStepView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListItemStepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ListItemStepView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private  void init(List<Step> mStepList){
        if( mStepList == null) return;
        setOrientation(VERTICAL);
        removeAllViews();
        for (int i = 0; i < mStepList.size(); i++) {
            ItemSectionInstructionView itemSectionInstructionView = new ItemSectionInstructionView(getContext());
            addView(itemSectionInstructionView);
        }
    }

    public void setStepList(List<Step> mStepList) {
        this.mStepList = mStepList;
        init(this.mStepList);
    }
}
