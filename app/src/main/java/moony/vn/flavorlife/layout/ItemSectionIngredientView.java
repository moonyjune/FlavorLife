package moony.vn.flavorlife.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.Ingredient;

/**
 * Created by moony on 3/14/15.
 */
public class ItemSectionIngredientView extends LinearLayout {
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
    }

    public void display(Ingredient ingredient) {

    }
}
