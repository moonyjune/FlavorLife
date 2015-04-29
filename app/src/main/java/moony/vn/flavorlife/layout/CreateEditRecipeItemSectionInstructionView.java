package moony.vn.flavorlife.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.Ingredient;
import moony.vn.flavorlife.entities.Step;

/**
 * Created by moony on 4/8/15.
 */
public class CreateEditRecipeItemSectionInstructionView extends LinearLayout {
    private TextView mNumber;
    private EditText mContent;
    private OnClickListener onDeleteStep;

    public CreateEditRecipeItemSectionInstructionView(Context context) {
        super(context);
        init();
    }

    public CreateEditRecipeItemSectionInstructionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CreateEditRecipeItemSectionInstructionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        inflate(getContext(), R.layout.create_edit_recipe_item_section_instruction_detail, this);
        mNumber = (TextView) findViewById(R.id.step_number);
        mContent = (EditText) findViewById(R.id.step_content);
    }

    public void display(Step step) {
        if (step == null) return;
        mNumber.setText(String.valueOf(step.getNumberStep()));
        mContent.setText(step.getContent());
    }

    public void setOnDeleteStep(OnClickListener onDeleteStep) {
        this.onDeleteStep = onDeleteStep;
        findViewById(R.id.delete_step).setOnClickListener(this.onDeleteStep);
    }
}
