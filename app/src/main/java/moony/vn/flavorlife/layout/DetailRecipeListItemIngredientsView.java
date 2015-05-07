package moony.vn.flavorlife.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.List;

import moony.vn.flavorlife.entities.Ingredient;

/**
 * Created by moony on 3/18/15.
 */
public class DetailRecipeListItemIngredientsView extends LinearLayout {
    private List<Ingredient> mIngredientList;

    public DetailRecipeListItemIngredientsView(Context context) {
        super(context);
    }

    public DetailRecipeListItemIngredientsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DetailRecipeListItemIngredientsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private  void init(List<Ingredient> mIngredientList){
        if( mIngredientList == null) return;
        setOrientation(VERTICAL);
        removeAllViews();
        for (int i = 0; i < mIngredientList.size(); i++) {
            DetailRecipeItemSectionIngredientView detailRecipeItemSectionIngredientView = new DetailRecipeItemSectionIngredientView(getContext());
            detailRecipeItemSectionIngredientView.display(mIngredientList.get(i));
            addView(detailRecipeItemSectionIngredientView);
        }
    }

    public void setIngredientList(List<Ingredient> mIngredientList) {
        this.mIngredientList = mIngredientList;
        init(this.mIngredientList);
    }
}
