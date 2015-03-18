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
public class ListItemIngredientsView extends LinearLayout {
    private List<Ingredient> mIngredientList;

    public ListItemIngredientsView(Context context) {
        super(context);
    }

    public ListItemIngredientsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListItemIngredientsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ListItemIngredientsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private  void init(List<Ingredient> mIngredientList){
        if( mIngredientList == null) return;
        setOrientation(VERTICAL);
        removeAllViews();
        for (int i = 0; i < mIngredientList.size(); i++) {
            ItemSectionIngredientView itemSectionIngredientView = new ItemSectionIngredientView(getContext());
            addView(itemSectionIngredientView);
        }
    }

    public void setIngredientList(List<Ingredient> mIngredientList) {
        this.mIngredientList = mIngredientList;
        init(this.mIngredientList);
    }
}
