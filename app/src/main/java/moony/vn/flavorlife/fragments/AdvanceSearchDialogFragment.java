package moony.vn.flavorlife.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.entities.Recipe;
import moony.vn.flavorlife.entities.SearchRecipeCondition;

/**
 * Created by moony on 5/11/15.
 */
public class AdvanceSearchDialogFragment extends DialogFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    public static final String DATA = "data";
    private static final String SEARCH_CONDITION = "search_condition";
    private SearchRecipeCondition mSearchRecipeCondition;
    private EditText mName;
    private RadioGroup mKinds;
    private RadioButton mStarter, mMain, mDessert;
    private RatingBar mLevel;

    public static AdvanceSearchDialogFragment newInstance(SearchRecipeCondition searchRecipeCondition) {
        AdvanceSearchDialogFragment advanceSearchDialogFragment = new AdvanceSearchDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SEARCH_CONDITION, searchRecipeCondition);
        advanceSearchDialogFragment.setArguments(bundle);
        return advanceSearchDialogFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_advance_search, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, STYLE_NORMAL);
        if (savedInstanceState == null) {
            mSearchRecipeCondition = (SearchRecipeCondition) getArguments().getSerializable(SEARCH_CONDITION);
        } else {
            mSearchRecipeCondition = (SearchRecipeCondition) savedInstanceState.getSerializable(SEARCH_CONDITION);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        int height = getResources().getDimensionPixelSize(R.dimen.dialog_search_height);
        int width = getResources().getDimensionPixelSize(R.dimen.dialog_search_width);
        getDialog().getWindow().setLayout(width, height);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mName = (EditText) view.findViewById(R.id.search_name);
        mKinds = (RadioGroup) view.findViewById(R.id.search_kind);
        mStarter = (RadioButton) view.findViewById(R.id.kind_starter);
        mMain = (RadioButton) view.findViewById(R.id.kind_main);
        mDessert = (RadioButton) view.findViewById(R.id.kind_dessert);
        mLevel = (RatingBar) view.findViewById(R.id.search_level);
        view.findViewById(R.id.search).setOnClickListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mSearchRecipeCondition != null)
            outState.putSerializable(SEARCH_CONDITION, mSearchRecipeCondition);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mSearchRecipeCondition != null) {
            mName.setText(mSearchRecipeCondition.getName());
            mLevel.setProgress(mSearchRecipeCondition.getLevel());
            mKinds.setOnCheckedChangeListener(this);
            switch (mSearchRecipeCondition.getKind()) {
                case Recipe.STARTER:
                    mKinds.check(R.id.kind_starter);
                    break;
                case Recipe.MAIN_COURSE:
                    mKinds.check(R.id.kind_main);
                    break;
                case Recipe.DESSERT:
                    mKinds.check(R.id.kind_dessert);
                    break;
                default:
                    mKinds.check(R.id.kind_starter);
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(mSearchRecipeCondition == null)
            mSearchRecipeCondition = new SearchRecipeCondition();
        if (mName.getText() != null)
            mSearchRecipeCondition.setName(mName.getText().toString());
        if (mLevel.getProgress() != 0)
            mSearchRecipeCondition.setLevel(mLevel.getProgress());
        Intent data = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SEARCH_CONDITION, mSearchRecipeCondition);
        data.putExtra(DATA, bundle);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
        dismiss();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
        if(mSearchRecipeCondition == null)
            mSearchRecipeCondition = new SearchRecipeCondition();
        switch (checkId) {
            case R.id.kind_starter:
                mSearchRecipeCondition.setKind(Recipe.STARTER);
                break;
            case R.id.kind_main:
                mSearchRecipeCondition.setKind(Recipe.MAIN_COURSE);
                break;
            case R.id.kind_dessert:
                mSearchRecipeCondition.setKind(Recipe.DESSERT);
                break;
        }
    }
}
