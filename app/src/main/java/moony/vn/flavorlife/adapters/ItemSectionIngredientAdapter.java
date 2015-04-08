package moony.vn.flavorlife.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import moony.vn.flavorlife.entities.Ingredient;
import moony.vn.flavorlife.layout.CreateEditRecipeItemSectionIngredientView;
import moony.vn.flavorlife.layout.CreateEditRecipeSectionIngredientView;
import moony.vn.flavorlife.layout.DetailRecipeItemSectionIngredientView;

/**
 * Created by moony on 3/14/15.
 */
public class ItemSectionIngredientAdapter extends ArrayAdapter<Ingredient> {
    private List<Ingredient> mListIngredients;

    public ItemSectionIngredientAdapter(Context context, int resource, List<Ingredient> objects) {
        super(context, resource, objects);
        mListIngredients = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Ingredient ingredient = mListIngredients.get(position);
        if (convertView == null) {
            convertView = new CreateEditRecipeItemSectionIngredientView(getContext());
        }
        ((CreateEditRecipeItemSectionIngredientView) convertView).setOnDeleteIngredient(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListIngredients.remove(position);
                notifyDataSetChanged();
            }
        });
        ((CreateEditRecipeItemSectionIngredientView) convertView).display(ingredient);
        return convertView;
    }

    @Override
    public int getCount() {
        return mListIngredients.size();
    }

    public void addItem(Ingredient ingredient) {
        mListIngredients.add(ingredient);
        notifyDataSetChanged();
    }
}
