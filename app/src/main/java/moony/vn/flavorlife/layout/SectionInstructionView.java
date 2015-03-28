package moony.vn.flavorlife.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.adapters.ItemSectionIngredientAdapter;
import moony.vn.flavorlife.entities.SectionIngredient;
import moony.vn.flavorlife.entities.SectionInstruction;
import moony.vn.flavorlife.utils.ListViewUtils;

/**
 * Created by moony on 3/14/15.
 */
public class SectionInstructionView extends LinearLayout {
    private TextView mSectionName;
    public SectionInstructionView(Context context) {
        super(context);
        init();
    }

    public SectionInstructionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SectionInstructionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SectionInstructionView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.item_section_instruction, this);
        mSectionName = (TextView) findViewById(R.id.section_name);
    }

    public void display(SectionInstruction sectionInstruction) {
        mSectionName.setText(sectionInstruction.getName());
    }

}
