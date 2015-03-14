package moony.vn.flavorlife.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;

import java.util.List;

import moony.vn.flavorlife.entities.Ingredient;
import moony.vn.flavorlife.entities.SectionIngredient;
import moony.vn.flavorlife.layout.ItemSectionIngredientView;
import moony.vn.flavorlife.layout.SectionIngredientView;

/**
 * Created by moony on 3/14/15.
 */
public class IngredientsExpandableAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<SectionIngredient> mListSectionIngredients;

    public IngredientsExpandableAdapter(Context context, List<SectionIngredient> mListSectionIngredients) {
        mContext = context;
        this.mListSectionIngredients = mListSectionIngredients;
    }

    @Override
    public int getGroupCount() {
        return mListSectionIngredients.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mListSectionIngredients.get(groupPosition).getListIngredients().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mListSectionIngredients.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mListSectionIngredients.get(groupPosition).getListIngredients().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup viewGroup) {
        SectionIngredient sectionIngredient = mListSectionIngredients.get(groupPosition);
        if (convertView == null) {
            convertView = new SectionIngredientView(mContext);
        }
        ((SectionIngredientView) convertView).display(sectionIngredient);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup viewGroup) {
        Ingredient ingredient = mListSectionIngredients.get(groupPosition).getListIngredients().get(childPosition);
        if (convertView == null) {
            convertView = new ItemSectionIngredientView(mContext);
        }
        ((ItemSectionIngredientView) convertView).display(ingredient);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
