package moony.vn.flavorlife.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.Step;

/**
 * Created by moony on 3/14/15.
 */
public class DetailRecipeItemSectionInstructionView extends LinearLayout {
    private TextView mStepContent;

    public DetailRecipeItemSectionInstructionView(Context context) {
        super(context);
        init();
    }

    public DetailRecipeItemSectionInstructionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DetailRecipeItemSectionInstructionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.detail_recipe_item_section_instruction_detail, this);
        mStepContent = (TextView) findViewById(R.id.step_content);
        display(null);
    }

    public void display(Step step) {
        if (step == null) return;
        SpannableString spannableString = null;
        if (step.getNumberStep() < 10) {
            spannableString = new SpannableString("0" + step.getNumberStep() + " " + step.getContent());
        } else {
            spannableString = new SpannableString(step.getNumberStep() + " " + step.getContent());
        }
        spannableString.setSpan(new TextAppearanceSpan(getContext(), R.style.StepNumber), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new TextAppearanceSpan(getContext(), R.style.StepContent), 2, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mStepContent.setText(spannableString, TextView.BufferType.SPANNABLE);
    }
}
