package moony.vn.flavorlife.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ExpandableListView;

import com.ntq.fragments.NFragmentSwitcher;

import java.util.ArrayList;
import java.util.List;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.adapters.InstructionExpandableAdapter;
import moony.vn.flavorlife.entities.Recipe;
import moony.vn.flavorlife.entities.SectionIngredient;
import moony.vn.flavorlife.entities.SectionInstruction;

/**
 * Created by moony on 3/4/15.
 */
public class InstructionFragment extends NFragmentSwitcher implements View.OnClickListener {
    private static final String KEY_SECTION_INSTRUCTION = "section_instruction";
    private static final String RECIPE = "recipe";
    private ExpandableListView mInstructionListView;
    private InstructionExpandableAdapter mInstructionExpandableAdapter;
    private List<SectionInstruction> mSectionInstructions;
    private Recipe mRecipe;

    public static InstructionFragment newInstance(Recipe recipe) {
        InstructionFragment instructionFragment = new InstructionFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(RECIPE, recipe);
        instructionFragment.setArguments(bundle);
        return instructionFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mSectionInstructions = savedInstanceState.getParcelableArrayList(KEY_SECTION_INSTRUCTION);
            mRecipe = (Recipe) savedInstanceState.getSerializable(RECIPE);
        } else {
            mRecipe = (Recipe) getArguments().getSerializable(RECIPE);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mInstructionListView = (ExpandableListView) view.findViewById(R.id.instruction);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_instruction;
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected boolean isDataReady() {
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mSectionInstructions != null)
            outState.putParcelableArrayList(KEY_SECTION_INSTRUCTION, new ArrayList<SectionInstruction>(mSectionInstructions));
        if (mRecipe != null)
            outState.putSerializable(RECIPE, mRecipe);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mSectionInstructions == null) {
            if (mRecipe != null) {
                mSectionInstructions = mRecipe.getListSectionInstructions();
                mRecipe = null;
            } else {
                mSectionInstructions = new ArrayList<SectionInstruction>();
                mSectionInstructions.add(new SectionInstruction());
            }
        }
        if (mInstructionExpandableAdapter == null) {
            mInstructionExpandableAdapter = new InstructionExpandableAdapter(getActivity(), mSectionInstructions) {
                @Override
                public void notifyDataSetChanged() {
                    super.notifyDataSetChanged();
                    for (int i = 0; i < getGroupCount() - 1; i++) {
                        mInstructionListView.expandGroup(i);
                    }
                }
            };
        }
        mInstructionListView.setAdapter(mInstructionExpandableAdapter);
        mInstructionListView.setGroupIndicator(null);
        mInstructionExpandableAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

    public List<SectionInstruction> getSectionInstructions() {
        return mSectionInstructions;
    }

    public void clearData(){
        if(mSectionInstructions != null)
            mSectionInstructions.clear();
        mSectionInstructions = null;
        mInstructionExpandableAdapter = null;
    }
}
