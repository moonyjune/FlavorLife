package moony.vn.flavorlife.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import moony.vn.flavorlife.entities.SectionIngredient;
import moony.vn.flavorlife.layout.CreateEditRecipeSectionIngredientView;
import moony.vn.flavorlife.layout.DetailRecipeSectionIngredientView;

/**
 * Created by moony on 3/14/15.
 */
public class SectionIngredientAdapter extends ArrayAdapter<SectionIngredient> {
    private List<SectionIngredient> mListSectionIngredients;

    public SectionIngredientAdapter(Context context, int resource, List<SectionIngredient> objects) {
        super(context, resource, objects);
        mListSectionIngredients = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        SectionIngredient sectionIngredient = mListSectionIngredients.get(position);
        if (convertView == null) {
            convertView = new CreateEditRecipeSectionIngredientView(getContext());
        }
        ((CreateEditRecipeSectionIngredientView) convertView).setOnDeleteSection(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListSectionIngredients.remove(position);
                notifyDataSetChanged();
            }
        });
        ((CreateEditRecipeSectionIngredientView) convertView).display(sectionIngredient);
        return convertView;
    }

    @Override
    public int getCount() {
        return mListSectionIngredients.size();
    }

    public void addItem(SectionIngredient sectionIngredient){
        mListSectionIngredients.add(sectionIngredient);
        notifyDataSetChanged();
    }

}
