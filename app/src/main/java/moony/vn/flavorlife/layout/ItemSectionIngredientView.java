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
public class ItemSectionIngredientView extends LinearLayout {
    private TextView mIngredientName;
    private EditText mIngredientValue, mIngredientUnit;

    public ItemSectionIngredientView(Context context) {
        super(context);
        init();
    }

    public ItemSectionIngredientView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemSectionIngredientView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ItemSectionIngredientView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.item_section_ingredient_detail, this);
        mIngredientName = (TextView) findViewById(R.id.view_ingredient_name);
        mIngredientValue = (EditText) findViewById(R.id.view_ingredient_value);
        mIngredientUnit = (EditText) findViewById(R.id.view_ingredient_unit);
    }

    public void display(Ingredient ingredient) {
        if (ingredient == null) return;
        mIngredientName.setText(ingredient.getName()+"");
        mIngredientValue.setText(String.valueOf(ingredient.getValue()));
        mIngredientUnit.setText(ingredient.getUnit());
    }
}
