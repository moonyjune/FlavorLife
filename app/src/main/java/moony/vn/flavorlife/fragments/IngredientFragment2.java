package moony.vn.flavorlife.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.ntq.fragments.NFragmentSwitcher;

import java.util.ArrayList;
import java.util.List;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.adapters.IngredientsExpandableAdapter;
import moony.vn.flavorlife.adapters.SectionIngredientAdapter;
import moony.vn.flavorlife.entities.SectionIngredient;
import moony.vn.flavorlife.utils.ListViewUtils;

/**
 * Created by moony on 3/4/15.
 */
public class IngredientFragment2 extends NFragmentSwitcher implements View.OnClickListener {
    private final String KEY_SECTION_INGREDIENTS = "section_ingredients";
    private List<SectionIngredient> mSectionIngredients;
    private IngredientsExpandableAdapter mIngredientExpandableAdapter;
    private ExpandableListView mListSectionIngredients;

    public static IngredientFragment2 newInstance() {
        IngredientFragment2 ingredientFragment2 = new IngredientFragment2();
        Bundle bundle = new Bundle();
        ingredientFragment2.setArguments(bundle);
        return ingredientFragment2;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null)
            mSectionIngredients = savedInstanceState.getParcelableArrayList(KEY_SECTION_INGREDIENTS);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListSectionIngredients = (ExpandableListView) view.findViewById(R.id.ingredients);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mSectionIngredients != null)
            outState.putParcelableArrayList(KEY_SECTION_INGREDIENTS, new ArrayList<SectionIngredient>(mSectionIngredients));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mSectionIngredients == null) {
            mSectionIngredients = new ArrayList<SectionIngredient>();
            mSectionIngredients.add(new SectionIngredient());
        }
        if (mIngredientExpandableAdapter == null) {
            mIngredientExpandableAdapter = new IngredientsExpandableAdapter(getActivity(), mSectionIngredients) {
                @Override
                public void notifyDataSetChanged() {
                    super.notifyDataSetChanged();
                    for (int i = 0; i < getGroupCount() - 1; i++) {
                        mListSectionIngredients.expandGroup(i);
                    }
                }
            };
        }
        mListSectionIngredients.setAdapter(mIngredientExpandableAdapter);
        mListSectionIngredients.setGroupIndicator(null);
        mIngredientExpandableAdapter.notifyDataSetChanged();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_ingredient_2;
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onClick(View view) {
    }

    public List<SectionIngredient> getSectionIngredients() {
        return mSectionIngredients;
    }
}
