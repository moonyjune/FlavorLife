package moony.vn.flavorlife.layout;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.SectionIngredient;

/**
 * Created by moony on 4/8/15.
 */
public class CreateEditRecipeSectionIngredientView extends LinearLayout implements View.OnClickListener, TextWatcher {
//    private ListView mListIngredients;
//    private ItemSectionIngredientAdapter mItemSectionIngredientAdapter;
    private EditText mName;
    private OnClickListener onDeleteSection;
    private SectionIngredient mSection;

    public CreateEditRecipeSectionIngredientView(Context context) {
        super(context);
        init();
    }

    public CreateEditRecipeSectionIngredientView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CreateEditRecipeSectionIngredientView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.create_edit_recipe_item_section_ingredient, this);
        mName = (EditText) findViewById(R.id.create_ingredient_section_name);
        mName.addTextChangedListener(this);
//        mListIngredients = (ListView) findViewById(R.id.list_ingredients);
//        findViewById(R.id.add_ingredient).setOnClickListener(this);
    }

    public void display(SectionIngredient sectionIngredient) {
        if (sectionIngredient == null) return;
        mSection = sectionIngredient;
        mName.setText(sectionIngredient.getName());
//        mItemSectionIngredientAdapter = new ItemSectionIngredientAdapter(getContext(), 0, sectionIngredient.getListIngredients());
//        mListIngredients.setAdapter(mItemSectionIngredientAdapter);
//        ListViewUtils.setListViewHeightBasedOnChildren(mListIngredients);
    }

    @Override
    public void onClick(View view) {
//        mItemSectionIngredientAdapter.addItem(new Ingredient());
//        ListViewUtils.setListViewHeightBasedOnChildren(mListIngredients);
    }

    public void setOnDeleteSection(OnClickListener onDeleteSection) {
        this.onDeleteSection = onDeleteSection;
        findViewById(R.id.delete_section_ingredient).setOnClickListener(this.onDeleteSection);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
//        mSection.setName(mName.getText().toString());
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        mSection.setName(mName.getText().toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {
//        mSection.setName(mName.getText().toString());
    }
}
