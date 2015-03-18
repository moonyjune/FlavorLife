package moony.vn.flavorlife.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.List;

import moony.vn.flavorlife.entities.SectionIngredient;
import moony.vn.flavorlife.entities.SectionInstruction;

/**
 * Created by moony on 3/18/15.
 */
public class ListInstructionsView extends LinearLayout {
    private List<SectionInstruction> mListSectionInstruction;

    public ListInstructionsView(Context context) {
        super(context);
    }

    public ListInstructionsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListInstructionsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ListInstructionsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(List<SectionInstruction> mListSectionInstruction) {
        if (mListSectionInstruction == null) return;
        removeAllViews();
        setOrientation(VERTICAL);
        for (int i = 0; i < mListSectionInstruction.size(); i++) {
            SectionInstructionView sectionIngredientView = new SectionInstructionView(getContext());
            addView(sectionIngredientView);
            ListItemStepView listItemStep = new ListItemStepView(getContext());
            listItemStep.setStepList(mListSectionInstruction.get(i).getListSteps());
            addView(listItemStep);
        }

    }

    public void setListSectionIngredient(List<SectionInstruction> mListSectionInstruction) {
        this.mListSectionInstruction = mListSectionInstruction;
        init(this.mListSectionInstruction);
    }
}
