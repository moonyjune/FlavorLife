package moony.vn.flavorlife.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.ntq.fragments.NFragmentSwitcher;

import java.util.ArrayList;
import java.util.List;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.adapters.InstructionExpandableAdapter;
import moony.vn.flavorlife.entities.SectionInstruction;
import moony.vn.flavorlife.entities.Step;

/**
 * Created by moony on 3/4/15.
 */
public class InstructionFragment extends NFragmentSwitcher implements View.OnClickListener {
    private ExpandableListView mInstructionListView;
    private InstructionExpandableAdapter mInstructionExpandableAdapter;
    private List<SectionInstruction> mSectionInstructions;

    public static InstructionFragment newInstance() {
        InstructionFragment instructionFragment = new InstructionFragment();
        Bundle bundle = new Bundle();
        instructionFragment.setArguments(bundle);
        return instructionFragment;
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSectionInstructions = new ArrayList<SectionInstruction>();
        mSectionInstructions.add(new SectionInstruction());
        mInstructionExpandableAdapter = new InstructionExpandableAdapter(getActivity(), mSectionInstructions) {
            @Override
            public void notifyDataSetChanged() {
                super.notifyDataSetChanged();
                for (int i = 0; i < getGroupCount() - 1; i++) {
                    mInstructionListView.expandGroup(i, true);
                }
            }
        };
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
}
