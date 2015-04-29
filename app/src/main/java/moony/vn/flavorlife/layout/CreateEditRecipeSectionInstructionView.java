package moony.vn.flavorlife.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.SectionInstruction;

/**
 * Created by moony on 4/8/15.
 */
public class CreateEditRecipeSectionInstructionView extends LinearLayout implements View.OnClickListener {
    private OnClickListener onDeleteSection;
    private EditText mName;

    public CreateEditRecipeSectionInstructionView(Context context) {
        super(context);
        init();
    }

    public CreateEditRecipeSectionInstructionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CreateEditRecipeSectionInstructionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.create_edit_recipe_item_section_instruction, this);
        mName = (EditText) findViewById(R.id.instruction_section_name);
    }

    public void display(SectionInstruction sectionInstruction) {
        if (sectionInstruction == null) return;
        mName.setText(sectionInstruction.getName());
    }

    @Override
    public void onClick(View view) {
    }

    public void setOnDeleteSection(OnClickListener onDeleteSection) {
        this.onDeleteSection = onDeleteSection;
        findViewById(R.id.delete_section_instruction).setOnClickListener(this.onDeleteSection);
    }
}
