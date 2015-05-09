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
public class CreateEditRecipeSectionInstructionView extends LinearLayout implements View.OnClickListener, TextWatcher, View.OnFocusChangeListener {
    private OnClickListener onDeleteSection;
    private EditText mName;
    private SectionInstruction mSection;
    private long mTextLostFocusTimestamp;

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
        mName.setOnFocusChangeListener(this);
        reclaimFocus(mName, mTextLostFocusTimestamp);
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

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if ((view.getId() == R.id.instruction_section_name) && !hasFocus)
            mTextLostFocusTimestamp = System.currentTimeMillis();
    }

    private void reclaimFocus(View v, long timestamp) {
        if (timestamp == -1)
            return;
        if ((System.currentTimeMillis() - timestamp) < 250) {
            v.requestFocus();
        }
        if (v == mName) {
            mName.setSelection(mName.getText().length());
        }
    }
}
