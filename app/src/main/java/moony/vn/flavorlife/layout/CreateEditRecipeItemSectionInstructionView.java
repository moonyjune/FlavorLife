package moony.vn.flavorlife.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.Ingredient;
import moony.vn.flavorlife.entities.Step;

/**
 * Created by moony on 4/8/15.
 */
public class CreateEditRecipeItemSectionInstructionView extends LinearLayout {
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
    }

    public void display(Step step) {

    }

    public void setOnDeleteStep(OnClickListener onDeleteStep) {
        this.onDeleteStep = onDeleteStep;
        findViewById(R.id.delete_step).setOnClickListener(this.onDeleteStep);
    }
}
