package moony.vn.flavorlife.layout;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.Ingredient;

/**
 * Created by moony on 4/8/15.
 */
public class CreateEditRecipeItemSectionIngredientView extends LinearLayout implements View.OnFocusChangeListener {
    private OnClickListener onDeleteIngredient;
    private EditText mName, mValue, mUnit;
    private Ingredient mIngredient;
    private long mTextLostFocusTimestamp;
    private long mTextLostFocusTimestamp1;
    private long mTextLostFocusTimestamp2;

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

//        EditText newText = (EditText) v.findViewById(R.id.email);
//        if (mName != null)
//            newText.setText(mName.getText());
//        mName = newText;
        mName.setOnFocusChangeListener(this);
        mValue.setOnFocusChangeListener(this);
        mUnit.setOnFocusChangeListener(this);
        reclaimFocus(mName, mTextLostFocusTimestamp);
        reclaimFocus(mValue, mTextLostFocusTimestamp1);
        reclaimFocus(mUnit, mTextLostFocusTimestamp2);

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
            if (mValue.getText() != null && !mValue.getText().toString().isEmpty()) {
                mIngredient.setValue(Float.valueOf(mValue.getText().toString()));
            } else {
                mIngredient.setValue(0);
            }
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

    private void reclaimFocus(View v, long timestamp) {
        if (timestamp == -1)
            return;
        if ((System.currentTimeMillis() - timestamp) < 250) {
            v.requestFocus();
        }
        if (v == mName) {
            mName.setSelection(mName.getText().length());
        } else if (v == mValue) {
            mValue.setSelection(mValue.getText().length());
        } else if (v == mUnit) {
            mUnit.setSelection(mUnit.getText().length());
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if ((view.getId() == R.id.ingredient_name) && !hasFocus)
            mTextLostFocusTimestamp = System.currentTimeMillis();
        if ((view.getId() == R.id.ingredient_value) && !hasFocus)
            mTextLostFocusTimestamp1 = System.currentTimeMillis();
        if ((view.getId() == R.id.ingredient_unit) && !hasFocus)
            mTextLostFocusTimestamp2 = System.currentTimeMillis();
    }
}
