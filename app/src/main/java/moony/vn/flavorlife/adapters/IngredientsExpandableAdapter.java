package moony.vn.flavorlife.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.List;

import moony.vn.flavorlife.entities.Ingredient;
import moony.vn.flavorlife.entities.SectionIngredient;
import moony.vn.flavorlife.layout.AddSectionContentView;
import moony.vn.flavorlife.layout.AddSectionView;
import moony.vn.flavorlife.layout.CreateEditRecipeItemSectionIngredientView;
import moony.vn.flavorlife.layout.CreateEditRecipeSectionIngredientView;

/**
 * Created by moony on 3/14/15.
 */
public class IngredientsExpandableAdapter extends BaseExpandableListAdapter {
    private static final int NUMBER_GROUP_TYPE = 2;
    private static final int GROUP_TYPE_SECTION = 0;
    private static final int GROUP_TYPE_ADD_SECTION = 1;

    private static final int NUMBER_CHILD_TYPE = 2;
    private static final int CHILD_TYPE_INGREDIENT = 0;
    private static final int CHILD_TYPE_ADD_INGREDIENT = 1;

    private Context mContext;
    private List<SectionIngredient> mListSectionIngredients;
    private int mCurrentSection;
    private int mCurrentIngredient;

    public IngredientsExpandableAdapter(Context context, List<SectionIngredient> mListSectionIngredients) {
        mContext = context;
        this.mListSectionIngredients = mListSectionIngredients;
    }

    @Override
    public int getGroupCount() {
        return mListSectionIngredients.size() + 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition < mListSectionIngredients.size()) {
            return mListSectionIngredients.get(groupPosition).getListIngredients().size() + 1;
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (groupPosition < mListSectionIngredients.size()) {
            return mListSectionIngredients.get(groupPosition);
        } else {
            return null;
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (childPosition < mListSectionIngredients.get(groupPosition).getListIngredients().size()) {
            return mListSectionIngredients.get(groupPosition).getListIngredients().get(childPosition);
        } else {
            return null;
        }
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
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup viewGroup) {
        mCurrentSection = groupPosition;
        switch (getGroupType(groupPosition)) {
            case GROUP_TYPE_SECTION:
                SectionIngredient sectionIngredient = mListSectionIngredients.get(groupPosition);
                if (convertView == null || !(convertView instanceof CreateEditRecipeSectionIngredientView)) {
                    convertView = new CreateEditRecipeSectionIngredientView(mContext);
                }
                convertView.setOnClickListener(null);
                ((CreateEditRecipeSectionIngredientView) convertView).setOnDeleteSection(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mListSectionIngredients.size() > 1) {
                            for (int i = groupPosition + 1; i < mListSectionIngredients.size(); i++) {
                                mListSectionIngredients.get(i).setNumberSection(mListSectionIngredients.get(i).getNumberSection() - 1);
                            }
                        }
                        mListSectionIngredients.remove(groupPosition);
                        notifyDataSetChanged();
                    }
                });
                ((CreateEditRecipeSectionIngredientView) convertView).display(sectionIngredient);
                break;
            case GROUP_TYPE_ADD_SECTION:
                if (convertView == null || !(convertView instanceof AddSectionView)) {
                    convertView = new AddSectionView(mContext);
                }
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SectionIngredient newSectionIngredient = new SectionIngredient();
                        newSectionIngredient.setNumberSection(mListSectionIngredients.size() + 1);
                        mListSectionIngredients.add(newSectionIngredient);
                        notifyDataSetChanged();
                    }
                });
                break;
        }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup viewGroup) {
        switch (getChildType(groupPosition, childPosition)) {
            case CHILD_TYPE_INGREDIENT:
                Ingredient ingredient = mListSectionIngredients.get(groupPosition).getListIngredients().get(childPosition);
                if (convertView == null || !(convertView instanceof CreateEditRecipeItemSectionIngredientView)) {
                    convertView = new CreateEditRecipeItemSectionIngredientView(mContext);
                }
                convertView.setOnClickListener(null);
                ((CreateEditRecipeItemSectionIngredientView) convertView).setOnDeleteIngredient(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListSectionIngredients.get(groupPosition).getListIngredients().remove(childPosition);
                        notifyDataSetChanged();
                    }
                });
                ((CreateEditRecipeItemSectionIngredientView) convertView).display(ingredient);
                break;
            case CHILD_TYPE_ADD_INGREDIENT:
                if (convertView == null || !(convertView instanceof AddSectionContentView)) {
                    convertView = new AddSectionContentView(mContext);
                }
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListSectionIngredients.get(groupPosition).getListIngredients().add(new Ingredient());
                        notifyDataSetChanged();
                    }
                });
                break;
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public int getGroupType(int groupPosition) {
        if (groupPosition < mListSectionIngredients.size()) {
            return GROUP_TYPE_SECTION;
        } else {
            return GROUP_TYPE_ADD_SECTION;
        }
    }

    @Override
    public int getGroupTypeCount() {
        return NUMBER_GROUP_TYPE;
    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {
        if (groupPosition < mListSectionIngredients.size()) {
            if (childPosition < mListSectionIngredients.get(groupPosition).getListIngredients().size()) {
                return CHILD_TYPE_INGREDIENT;
            } else {
                return CHILD_TYPE_ADD_INGREDIENT;
            }
        }
        return CHILD_TYPE_ADD_INGREDIENT;
    }

    @Override
    public int getChildTypeCount() {
        return NUMBER_CHILD_TYPE;
    }

}
