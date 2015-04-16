package moony.vn.flavorlife.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
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
    }

    public void display(Step step) {
        mStepContent.setText(step.getContent());
    }
}
