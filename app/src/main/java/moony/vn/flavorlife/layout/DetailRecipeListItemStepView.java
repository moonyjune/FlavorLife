package moony.vn.flavorlife.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.List;

import moony.vn.flavorlife.entities.Step;

/**
 * Created by moony on 3/18/15.
 */
public class DetailRecipeListItemStepView extends LinearLayout {
    private List<Step> mStepList;

    public DetailRecipeListItemStepView(Context context) {
        super(context);
    }

    public DetailRecipeListItemStepView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DetailRecipeListItemStepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(List<Step> mStepList) {
        if (mStepList == null) return;
        setOrientation(VERTICAL);
        removeAllViews();
        for (int i = 0; i < mStepList.size(); i++) {
            DetailRecipeItemSectionInstructionView detailRecipeItemSectionInstructionView = new DetailRecipeItemSectionInstructionView(getContext());
            detailRecipeItemSectionInstructionView.display(mStepList.get(i));
            addView(detailRecipeItemSectionInstructionView);
        }
    }

    public void setStepList(List<Step> mStepList) {
        this.mStepList = mStepList;
        init(this.mStepList);
    }
}
