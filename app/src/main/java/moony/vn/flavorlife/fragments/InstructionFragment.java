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
    private int mCurrentSection;
    private EditText mSectionName, mInputStep;

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
        mSectionName = (EditText) view.findViewById(R.id.section_name);
        mInputStep = (EditText) view.findViewById(R.id.instruction_step);
        mSectionInstructions = new ArrayList<SectionInstruction>();
//        SectionInstruction sectionInstruction = new SectionInstruction();
//        sectionInstruction.setName("section");
//        sectionInstruction.setNumberSection(1);
//        Step step = new Step();
//        List<Step> steps = new ArrayList<>();
//        steps.add(step);
//        steps.add(step);
//        steps.add(step);
//        sectionInstruction.setListSteps(steps);
//        mSectionInstructions.add(sectionInstruction);
//        mSectionInstructions.add(sectionInstruction);
        mInstructionExpandableAdapter = new InstructionExpandableAdapter(getActivity(), mSectionInstructions);
        view.findViewById(R.id.add_section).setOnClickListener(this);
        view.findViewById(R.id.add_step).setOnClickListener(this);
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
        mInstructionListView.setAdapter(mInstructionExpandableAdapter);
        mInstructionListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                mCurrentSection = i;
                return false;
            }
        });
        mInstructionListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long l) {
                mCurrentSection = i;
                return false;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_section:
                SectionInstruction sectionInstruction = new SectionInstruction();
                sectionInstruction.setName(mSectionName.getText().toString());
                sectionInstruction.setNumberSection(mSectionInstructions.size() + 1);
                mSectionInstructions.add(sectionInstruction);
                mInstructionExpandableAdapter.notifyDataSetChanged();
                break;
            case R.id.add_step:
                Step step = new Step();
                step.setNumberStep(mSectionInstructions.get(mCurrentSection).getListSteps().size() + 1);
                step.setContent(mInputStep.getText().toString());
                mSectionInstructions.get(mCurrentSection).getListSteps().add(step);
                mInstructionExpandableAdapter.notifyDataSetChanged();
                mInstructionListView.expandGroup(mCurrentSection, true);
                break;
        }
    }

    public List<SectionInstruction> getSectionInstructions() {
        return mSectionInstructions;
    }
}
