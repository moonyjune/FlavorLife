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
public class DetailRecipeListIngredientsView extends LinearLayout {
    private List<SectionIngredient> mListSectionIngredient;

    public DetailRecipeListIngredientsView(Context context) {
        super(context);
    }

    public DetailRecipeListIngredientsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DetailRecipeListIngredientsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(List<SectionIngredient> mListSectionIngredient) {
        if (mListSectionIngredient == null) return;
        removeAllViews();
        setOrientation(VERTICAL);
        for (int i = 0; i < mListSectionIngredient.size(); i++) {
            DetailRecipeSectionIngredientView detailRecipeSectionIngredientView = new DetailRecipeSectionIngredientView(getContext());
            detailRecipeSectionIngredientView.display(mListSectionIngredient.get(i));
            addView(detailRecipeSectionIngredientView);

            DetailRecipeListItemIngredientsView detailRecipeListItemIngredientsView = new DetailRecipeListItemIngredientsView(getContext());
            detailRecipeListItemIngredientsView.setIngredientList(mListSectionIngredient.get(i).getListIngredients());
            addView(detailRecipeListItemIngredientsView);
        }

    }

    public void setListSectionIngredient(List<SectionIngredient> mListSectionIngredient) {
        this.mListSectionIngredient = mListSectionIngredient;
        init(this.mListSectionIngredient);
    }
}
