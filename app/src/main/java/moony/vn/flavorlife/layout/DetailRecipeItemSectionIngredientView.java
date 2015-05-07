package moony.vn.flavorlife.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.Ingredient;

/**
 * Created by moony on 3/14/15.
 */
public class DetailRecipeItemSectionIngredientView extends LinearLayout {
    private TextView mName;
    private TextView mValue, mUnit;

    public DetailRecipeItemSectionIngredientView(Context context) {
        super(context);
        init();
    }

    public DetailRecipeItemSectionIngredientView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DetailRecipeItemSectionIngredientView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.detail_recipe_item_section_ingredient_detail, this);
        mName = (TextView) findViewById(R.id.ingredient_name);
        mUnit = (TextView) findViewById(R.id.ingredient_unit);
        mValue = (TextView) findViewById(R.id.ingredient_value);
    }

    public void display(Ingredient ingredient) {
        if (ingredient == null) return;
        mName.setText(ingredient.getName());
        mUnit.setText(ingredient.getUnit());
        if (ingredient.getValue() <= 0)
            mValue.setText(null);
        else
            mValue.setText(ingredient.getValue() + "");
    }
}
