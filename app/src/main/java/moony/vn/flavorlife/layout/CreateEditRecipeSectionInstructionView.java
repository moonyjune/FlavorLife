package moony.vn.flavorlife.layout;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.SectionInstruction;

/**
 * Created by moony on 4/8/15.
 */
public class CreateEditRecipeSectionInstructionView extends LinearLayout implements View.OnClickListener, TextWatcher {
    private OnClickListener onDeleteSection;
    private EditText mName;
    private SectionInstruction mSection;

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
        mName.addTextChangedListener(this);
    }

    public void display(SectionInstruction sectionInstruction) {
        if (sectionInstruction == null) return;
        mSection = sectionInstruction;
        mName.setText(sectionInstruction.getName());
    }

    @Override
    public void onClick(View view) {
    }

    public void setOnDeleteSection(OnClickListener onDeleteSection) {
        this.onDeleteSection = onDeleteSection;
        findViewById(R.id.delete_section_instruction).setOnClickListener(this.onDeleteSection);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        mSection.setName(mName.getText().toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
