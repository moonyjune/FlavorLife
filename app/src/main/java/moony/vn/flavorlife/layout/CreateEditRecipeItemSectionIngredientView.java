package moony.vn.flavorlife.layout;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.Ingredient;

/**
 * Created by moony on 4/8/15.
 */
public class CreateEditRecipeItemSectionIngredientView extends LinearLayout {
    private OnClickListener onDeleteIngredient;
    private EditText mName, mValue, mUnit;
    private Ingredient mIngredient;

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
        mName.addTextChangedListener(onNameChanged);
        mValue.addTextChangedListener(onValueChanged);
        mUnit.addTextChangedListener(onUnitChanged);
    }

    public void display(Ingredient ingredient) {
        if (ingredient == null) return;
        mIngredient = ingredient;
        mName.setText(ingredient.getName());
        if (ingredient.getValue() <= 0) {
            mValue.setText(null);
        } else {
            mValue.setText(String.valueOf(ingredient.getValue()));
        }
        mUnit.setText(ingredient.getUnit());
    }

    public void setOnDeleteIngredient(OnClickListener onDeleteIngredient) {
        this.onDeleteIngredient = onDeleteIngredient;
        findViewById(R.id.delete_ingredient).setOnClickListener(this.onDeleteIngredient);
    }

    private TextWatcher onNameChanged = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            mIngredient.setName(mName.getText().toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private TextWatcher onValueChanged = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (mValue.getText() != null && !mValue.getText().toString().isEmpty())
                mIngredient.setValue(Float.valueOf(mValue.getText().toString()));
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private TextWatcher onUnitChanged = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            mIngredient.setUnit(mUnit.getText().toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
