package moony.vn.flavorlife.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.List;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.Ingredient;
import moony.vn.flavorlife.entities.SectionIngredient;
import moony.vn.flavorlife.entities.SectionInstruction;
import moony.vn.flavorlife.entities.Step;
import moony.vn.flavorlife.layout.ItemSectionIngredientView;
import moony.vn.flavorlife.layout.ItemSectionInstructionView;
import moony.vn.flavorlife.layout.SectionIngredientView;
import moony.vn.flavorlife.layout.SectionInstructionView;

/**
 * Created by moony on 3/14/15.
 */
public class InstructionExpandableAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<SectionInstruction> mListSectionInstruction;

    public InstructionExpandableAdapter(Context context, List<SectionInstruction> mListSectionInstruction) {
        mContext = context;
        this.mListSectionInstruction = mListSectionInstruction;
    }

    @Override
    public int getGroupCount() {
        return mListSectionInstruction.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mListSectionInstruction.get(groupPosition).getListSteps().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mListSectionInstruction.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mListSectionInstruction.get(groupPosition).getListSteps().get(childPosition);
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup viewGroup) {
        SectionInstruction sectionInstruction = mListSectionInstruction.get(groupPosition);
        if (convertView == null) {
            convertView = new SectionInstructionView(mContext);
        }
        ((SectionInstructionView) convertView).display(sectionInstruction);
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Step step = mListSectionInstruction.get(groupPosition).getListSteps().get(childPosition);
        if (convertView == null) {
            convertView = new ItemSectionInstructionView(mContext);
        }
        ((ItemSectionInstructionView) convertView).display(step);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
