package moony.vn.flavorlife.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.Ingredient;
import moony.vn.flavorlife.entities.SectionIngredient;

/**
 * Created by moony on 4/8/15.
 */
public class CreateEditRecipeItemSectionIngredientView extends LinearLayout {
    private OnClickListener onDeleteIngredient;
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CreateEditRecipeItemSectionIngredientView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.create_edit_recipe_item_section_ingredient_detail, this);
    }

    public void display(Ingredient ingredient) {
        
    }

    public void setOnDeleteIngredient(OnClickListener onDeleteIngredient) {
        this.onDeleteIngredient = onDeleteIngredient;
        findViewById(R.id.delete_ingredient).setOnClickListener(this.onDeleteIngredient);
    }
}
