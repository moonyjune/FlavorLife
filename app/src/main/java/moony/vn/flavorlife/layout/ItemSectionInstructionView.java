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
public class ItemSectionInstructionView extends LinearLayout {
    private TextView mStepName;
    private EditText mStepContent;
    public ItemSectionInstructionView(Context context) {
        super(context);
        init();
    }

    public ItemSectionInstructionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemSectionInstructionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ItemSectionInstructionView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.item_section_instruction_detail, this);
        mStepName = (TextView) findViewById(R.id.step_name);
        mStepContent = (EditText) findViewById(R.id.step_content);
    }

    public void display(Step step) {
        mStepName.setText(step.getName());
        mStepContent.setText(step.getContent());
    }
}
