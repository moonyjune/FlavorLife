package moony.vn.flavorlife.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.List;

import moony.vn.flavorlife.entities.Section;
import moony.vn.flavorlife.entities.SectionInstruction;
import moony.vn.flavorlife.entities.Step;
import moony.vn.flavorlife.layout.AddSectionContentView;
import moony.vn.flavorlife.layout.AddSectionView;
import moony.vn.flavorlife.layout.CreateEditRecipeItemSectionIngredientView;
import moony.vn.flavorlife.layout.CreateEditRecipeItemSectionInstructionView;
import moony.vn.flavorlife.layout.CreateEditRecipeSectionInstructionView;
import moony.vn.flavorlife.layout.DetailRecipeItemSectionInstructionView;
import moony.vn.flavorlife.layout.DetailRecipeSectionInstructionView;

/**
 * Created by moony on 3/14/15.
 */
public class InstructionExpandableAdapter extends BaseExpandableListAdapter {
    private static final int NUMBER_GROUP_TYPE = 2;
    private static final int GROUP_TYPE_SECTION = 0;
    private static final int GROUP_TYPE_ADD_SECTION = 1;

    private static final int NUMBER_CHILD_TYPE = 2;
    private static final int CHILD_TYPE_STEP = 0;
    private static final int CHILD_TYPE_ADD_STEP = 1;

    private Context mContext;
    private List<SectionInstruction> mListSectionInstruction;

    public InstructionExpandableAdapter(Context context, List<SectionInstruction> mListSectionInstruction) {
        mContext = context;
        this.mListSectionInstruction = mListSectionInstruction;
    }

    @Override
    public int getGroupCount() {
        return mListSectionInstruction.size() + 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition < mListSectionInstruction.size()) {
            return mListSectionInstruction.get(groupPosition).getListSteps().size() + 1;
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (groupPosition < mListSectionInstruction.size()) {
            return mListSectionInstruction.get(groupPosition);
        } else {
            return null;
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (childPosition < mListSectionInstruction.get(groupPosition).getListSteps().size()) {
            return mListSectionInstruction.get(groupPosition).getListSteps().get(childPosition);
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
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup viewGroup) {
        switch (getGroupType(groupPosition)) {
            case GROUP_TYPE_SECTION:
                SectionInstruction sectionInstruction = mListSectionInstruction.get(groupPosition);
                if (convertView == null || !(convertView instanceof CreateEditRecipeSectionInstructionView)) {
                    convertView = new CreateEditRecipeSectionInstructionView(mContext);
                }
                convertView.setOnClickListener(null);
                ((CreateEditRecipeSectionInstructionView) convertView).setOnDeleteSection(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mListSectionInstruction.size() > 1) {
                            for (int i = groupPosition + 1; i < mListSectionInstruction.size(); i++) {
                                mListSectionInstruction.get(i).setNumberSection(mListSectionInstruction.get(i).getNumberSection() - 1);
                            }
                        }
                        mListSectionInstruction.remove(groupPosition);
                        notifyDataSetChanged();
                    }
                });
                ((CreateEditRecipeSectionInstructionView) convertView).display(sectionInstruction);
                break;
            case GROUP_TYPE_ADD_SECTION:
                if (convertView == null || !(convertView instanceof AddSectionView)) {
                    convertView = new AddSectionView(mContext);
                }
                ((AddSectionView) convertView).setOnAddSectionListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SectionInstruction newSectionInstruction = new SectionInstruction();
                        newSectionInstruction.setNumberSection(mListSectionInstruction.size() + 1);
                        mListSectionInstruction.add(newSectionInstruction);
                        notifyDataSetChanged();
                    }
                });
                break;
        }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        switch (getChildType(groupPosition, childPosition)) {
            case CHILD_TYPE_STEP:
                Step step = mListSectionInstruction.get(groupPosition).getListSteps().get(childPosition);
                if (convertView == null || !(convertView instanceof CreateEditRecipeItemSectionInstructionView)) {
                    convertView = new CreateEditRecipeItemSectionInstructionView(mContext);
                }
                convertView.setOnClickListener(null);
                ((CreateEditRecipeItemSectionInstructionView) convertView).setOnDeleteStep(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mListSectionInstruction.get(groupPosition).getListSteps().size() > 1) {
                            for (int i = childPosition + 1; i < mListSectionInstruction.get(groupPosition).getListSteps().size(); i++) {
                                mListSectionInstruction.get(groupPosition).getListSteps().get(i).setNumberStep(mListSectionInstruction.get(groupPosition).getListSteps().get(i).getNumberStep() - 1);
                            }
                        }
                        mListSectionInstruction.get(groupPosition).getListSteps().remove(childPosition);
                        notifyDataSetChanged();
                    }
                });
                ((CreateEditRecipeItemSectionInstructionView) convertView).display(step);
                break;
            case CHILD_TYPE_ADD_STEP:
                if (convertView == null || !(convertView instanceof AddSectionContentView)) {
                    convertView = new AddSectionContentView(mContext);
                }
                ((AddSectionContentView) convertView).setOnAddSectionContentListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Step step = new Step();
                        step.setNumberStep(mListSectionInstruction.get(groupPosition).getListSteps().size() + 1);
                        mListSectionInstruction.get(groupPosition).getListSteps().add(step);
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
        if (groupPosition < mListSectionInstruction.size()) {
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
        if (groupPosition < mListSectionInstruction.size()) {
            if (childPosition < mListSectionInstruction.get(groupPosition).getListSteps().size()) {
                return CHILD_TYPE_STEP;
            } else {
                return CHILD_TYPE_ADD_STEP;
            }
        }
        return CHILD_TYPE_ADD_STEP;
    }

    @Override
    public int getChildTypeCount() {
        return NUMBER_CHILD_TYPE;
    }
}
