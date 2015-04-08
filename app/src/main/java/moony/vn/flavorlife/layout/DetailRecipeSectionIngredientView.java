package moony.vn.flavorlife.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.SectionIngredient;

/**
 * Created by moony on 3/14/15.
 */
public class DetailRecipeSectionIngredientView extends LinearLayout {
    private TextView mSectionName;

    public DetailRecipeSectionIngredientView(Context context) {
        super(context);
        init();
    }

    public DetailRecipeSectionIngredientView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DetailRecipeSectionIngredientView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DetailRecipeSectionIngredientView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.detail_recipe_item_section_ingredient, this);
        mSectionName = (TextView) findViewById(R.id.section_name);
    }

    public void display(SectionIngredient sectionIngredient) {
        if (sectionIngredient == null) return;
        mSectionName.setText(sectionIngredient.getName());
    }

}
