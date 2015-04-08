package moony.vn.flavorlife.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.List;

import moony.vn.flavorlife.entities.SectionInstruction;

/**
 * Created by moony on 3/18/15.
 */
public class DetailRecipeListInstructionsView extends LinearLayout {
    private List<SectionInstruction> mListSectionInstruction;

    public DetailRecipeListInstructionsView(Context context) {
        super(context);
    }

    public DetailRecipeListInstructionsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DetailRecipeListInstructionsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DetailRecipeListInstructionsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(List<SectionInstruction> mListSectionInstruction) {
        if (mListSectionInstruction == null) return;
        removeAllViews();
        setOrientation(VERTICAL);
        for (int i = 0; i < mListSectionInstruction.size(); i++) {
            DetailRecipeSectionInstructionView detailRecipeSectionInstructionView = new DetailRecipeSectionInstructionView(getContext());
            addView(detailRecipeSectionInstructionView);

            DetailRecipeListItemStepView detailRecipeListItemStepView = new DetailRecipeListItemStepView(getContext());
            detailRecipeListItemStepView.setStepList(mListSectionInstruction.get(i).getListSteps());
            addView(detailRecipeListItemStepView);
        }

    }

    public void setListSectionIngredient(List<SectionInstruction> mListSectionInstruction) {
        this.mListSectionInstruction = mListSectionInstruction;
        init(this.mListSectionInstruction);
    }
}
