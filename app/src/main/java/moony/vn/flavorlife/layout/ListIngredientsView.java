package moony.vn.flavorlife.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.List;

import moony.vn.flavorlife.entities.SectionIngredient;

/**
 * Created by moony on 3/18/15.
 */
public class ListIngredientsView extends LinearLayout {
    private List<SectionIngredient> mListSectionIngredient;

    public ListIngredientsView(Context context) {
        super(context);
    }

    public ListIngredientsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListIngredientsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ListIngredientsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(List<SectionIngredient> mListSectionIngredient) {
        if (mListSectionIngredient == null) return;
        removeAllViews();
        setOrientation(VERTICAL);
        for (int i = 0; i < mListSectionIngredient.size(); i++) {
            SectionIngredientView sectionIngredientView = new SectionIngredientView(getContext());
            addView(sectionIngredientView);
            ListItemIngredientsView listItemIngredients = new ListItemIngredientsView(getContext());
            listItemIngredients.setIngredientList(mListSectionIngredient.get(i).getListIngredients());
            addView(listItemIngredients);
        }

    }

    public void setListSectionIngredient(List<SectionIngredient> mListSectionIngredient) {
        this.mListSectionIngredient = mListSectionIngredient;
        init(this.mListSectionIngredient);
    }
}
