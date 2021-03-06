package moony.vn.flavorlife.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.Ingredient;
import moony.vn.flavorlife.entities.Step;

/**
 * Created by moony on 4/8/15.
 */
public class CreateEditRecipeItemSectionInstructionView extends LinearLayout implements TextWatcher, View.OnFocusChangeListener {
    private TextView mNumber;
    private EditText mContent;
    private OnClickListener onDeleteStep;
    private Step mStep;
    private long mTextLostFocusTimestamp;

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
        mStep = step;
        mNumber.setText(String.valueOf(step.getNumberStep()));
        mContent.setText(step.getContent());
        mContent.addTextChangedListener(this);
        mContent.setOnFocusChangeListener(this);
        reclaimFocus(mContent, mTextLostFocusTimestamp);
    }

    public void setOnDeleteStep(OnClickListener onDeleteStep) {
        this.onDeleteStep = onDeleteStep;
        findViewById(R.id.delete_step).setOnClickListener(this.onDeleteStep);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        mStep.setContent(mContent.getText().toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if ((view.getId() == R.id.step_content) && !hasFocus)
            mTextLostFocusTimestamp = System.currentTimeMillis();
    }

    private void reclaimFocus(View v, long timestamp) {
        if (timestamp == -1)
            return;
        if ((System.currentTimeMillis() - timestamp) < 250) {
            v.requestFocus();
        }
        if (v == mContent) {
            mContent.setSelection(mContent.getText().length());
        }
    }
}
