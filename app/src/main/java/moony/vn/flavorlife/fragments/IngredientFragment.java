package moony.vn.flavorlife.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.ntq.fragments.NFragmentSwitcher;

import java.util.ArrayList;
import java.util.List;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.adapters.SectionIngredientAdapter;
import moony.vn.flavorlife.entities.SectionIngredient;
import moony.vn.flavorlife.utils.ListViewUtils;

/**
 * Created by moony on 3/4/15.
 */
public class IngredientFragment extends NFragmentSwitcher implements View.OnClickListener {
    private List<SectionIngredient> mSectionIngredients;
    private SectionIngredientAdapter mSectionIngredientAdapter;
    private ListView mListSectionIngredients;

    public static IngredientFragment newInstance() {
        IngredientFragment ingredientFragment = new IngredientFragment();
        Bundle bundle = new Bundle();
        ingredientFragment.setArguments(bundle);
        return ingredientFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListSectionIngredients = (ListView) view.findViewById(R.id.ingredients);
        view.findViewById(R.id.add_section).setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSectionIngredients = new ArrayList<SectionIngredient>();
        mSectionIngredientAdapter = new SectionIngredientAdapter(getActivity(), 0, mSectionIngredients);
        mListSectionIngredients.setAdapter(mSectionIngredientAdapter);
        ListViewUtils.setListViewHeightBasedOnChildren(mListSectionIngredients);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_ingredient;
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected boolean isDataReady() {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_section:
                mSectionIngredientAdapter.addItem(new SectionIngredient());
                ListViewUtils.setListViewHeightBasedOnChildren(mListSectionIngredients);
                break;
        }
    }

    public List<SectionIngredient> getSectionIngredients() {
        return mSectionIngredients;
    }
}
