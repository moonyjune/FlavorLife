package moony.vn.flavorlife.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.adapters.ItemSectionIngredientAdapter;
import moony.vn.flavorlife.entities.Ingredient;
import moony.vn.flavorlife.entities.SectionIngredient;
import moony.vn.flavorlife.utils.ListViewUtils;

/**
 * Created by moony on 4/8/15.
 */
public class CreateEditRecipeSectionIngredientView extends LinearLayout implements View.OnClickListener {
//    private ListView mListIngredients;
//    private ItemSectionIngredientAdapter mItemSectionIngredientAdapter;
    private OnClickListener onDeleteSection;

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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CreateEditRecipeSectionIngredientView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.create_edit_recipe_item_section_ingredient, this);
//        mListIngredients = (ListView) findViewById(R.id.list_ingredients);
//        findViewById(R.id.add_ingredient).setOnClickListener(this);
    }

    public void display(SectionIngredient sectionIngredient) {
        if (sectionIngredient == null) return;
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
}
