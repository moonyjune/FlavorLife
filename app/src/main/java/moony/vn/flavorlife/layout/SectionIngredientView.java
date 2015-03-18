package moony.vn.flavorlife.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ListView;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.adapters.ItemSectionIngredientAdapter;
import moony.vn.flavorlife.entities.Ingredient;
import moony.vn.flavorlife.entities.SectionIngredient;
import moony.vn.flavorlife.utils.ListViewUtils;

/**
 * Created by moony on 3/14/15.
 */
public class SectionIngredientView extends LinearLayout {

    public SectionIngredientView(Context context) {
        super(context);
        init();
    }

    public SectionIngredientView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SectionIngredientView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SectionIngredientView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.item_section_ingredient, this);
    }

    public void display(SectionIngredient sectionIngredient) {
    }

}
