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
import moony.vn.flavorlife.entities.SectionIngredient;

/**
 * Created by moony on 4/8/15.
 */
public class CreateEditRecipeItemSectionIngredientView extends LinearLayout {
    private OnClickListener onDeleteIngredient;
    private EditText mName, mValue, mUnit;
    public CreateEditRecipeItemSectionIngredientView(Context context) {
        super(context);
        init();
    }

    public CreateEditRecipeItemSectionIngredientView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CreateEditRecipeItemSectionIngredientView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        inflate(getContext(), R.layout.create_edit_recipe_item_section_ingredient_detail, this);
        mName = (EditText) findViewById(R.id.ingredient_name);
        mValue = (EditText) findViewById(R.id.ingredient_value);
        mUnit = (EditText) findViewById(R.id.ingredient_unit);
    }

    public void display(Ingredient ingredient) {
        if(ingredient == null) return;
        mName.setText(ingredient.getName());
        mValue.setText(ingredient.getValue()+"");
        mUnit.setText(ingredient.getUnit());
    }

    public void setOnDeleteIngredient(OnClickListener onDeleteIngredient) {
        this.onDeleteIngredient = onDeleteIngredient;
        findViewById(R.id.delete_ingredient).setOnClickListener(this.onDeleteIngredient);
    }
}
