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

/**
 * Created by moony on 3/14/15.
 */
public class DetailRecipeItemSectionIngredientView extends LinearLayout {
    private TextView mIngredientInformation;
    private EditText mIngredientValue, mIngredientUnit;

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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DetailRecipeItemSectionIngredientView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.detail_recipe_item_section_ingredient_detail, this);
        mIngredientInformation = (TextView) findViewById(R.id.ingredient_information);
    }

    public void display(Ingredient ingredient) {
        if (ingredient == null) return;
        mIngredientInformation.setText(ingredient.getValue() + " " + ingredient.getUnit() + " " + ingredient.getName());
    }
}
