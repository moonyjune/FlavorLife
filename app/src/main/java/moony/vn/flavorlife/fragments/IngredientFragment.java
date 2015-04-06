package moony.vn.flavorlife.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.ntq.fragments.NFragmentSwitcher;

import java.util.ArrayList;
import java.util.List;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.adapters.IngredientsExpandableAdapter;
import moony.vn.flavorlife.entities.Ingredient;
import moony.vn.flavorlife.entities.Recipe;
import moony.vn.flavorlife.entities.Section;
import moony.vn.flavorlife.entities.SectionIngredient;

/**
 * Created by moony on 3/4/15.
 */
public class IngredientFragment extends NFragmentSwitcher implements View.OnClickListener {
    //    private ItemIngredientView mItemIngredientView;
    private List<SectionIngredient> mSectionIngredients;
    private IngredientsExpandableAdapter mIngredientsExpandableAdapter;
    private ExpandableListView mIngredients;
    private EditText mSectionName, mIngredientName, mIngredientValue, mIngredientUnit;
    private int mPosition;

    public static IngredientFragment newInstance() {
        IngredientFragment ingredientFragment = new IngredientFragment();
        Bundle bundle = new Bundle();
        ingredientFragment.setArguments(bundle);
        return ingredientFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mItemIngredientView = (ItemIngredientView) view.findViewById(R.id.item_ingredient);
        mIngredients = (ExpandableListView) view.findViewById(R.id.ingredients);
        mSectionName = (EditText) view.findViewById(R.id.section_name);
        mIngredientName = (EditText) view.findViewById(R.id.ingredient_name);
        mIngredientValue = (EditText) view.findViewById(R.id.ingredient_value);
        mIngredientUnit = (EditText) view.findViewById(R.id.ingredient_unit);
        view.findViewById(R.id.add_section).setOnClickListener(this);
        view.findViewById(R.id.add_ingredient).setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSectionIngredients = new ArrayList<SectionIngredient>();
//        SectionIngredient sectionIngredient = new SectionIngredient();
//        sectionIngredient.setName("section");
//        sectionIngredient.setNumberSection(1);
//        List<Ingredient> ingredients = new ArrayList<>();
//        Ingredient ingredient = new Ingredient();
//        ingredients.add(ingredient);
//        ingredients.add(ingredasas//        ingredients.add(ingredient);
//        sectionIngredient.setListIngredients(ingredients);
//        mSectionIngredients.add(sectionIngredient);
//        mSectionIngredients.add(sectionIngredient);
        mIngredientsExpandableAdapter = new IngredientsExpandableAdapter(getActivity(), mSectionIngredients);
        mIngredients.setAdapter(mIngredientsExpandableAdapter);
        mIngredients.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                mPosition = i;
                return false;
            }
        });
        mIngredients.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long l) {
                mPosition = i;
                return false;
            }
        });

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_ingredient;
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_section:
                SectionIngredient sectionIngredient = new SectionIngredient();
                sectionIngredient.setName(mSectionName.getText().toString());
                sectionIngredient.setNumberSection(mSectionIngredients.size() + 1);
                mSectionIngredients.add(sectionIngredient);
                mIngredientsExpandableAdapter.notifyDataSetChanged();
                break;
            case R.id.add_ingredient:
                Ingredient ingredient = new Ingredient();
                ingredient.setName(mIngredientName.getText().toString());
                ingredient.setValue(Integer.valueOf(mIngredientValue.getText().toString()));
                ingredient.setUnit(mIngredientUnit.getText().toString());
                mSectionIngredients.get(mPosition).getListIngredients().add(ingredient);
                mIngredientsExpandableAdapter.notifyDataSetChanged();
                mIngredients.expandGroup(mPosition, true);
                break;
        }
    }

    public List<SectionIngredient> getSectionIngredients() {
        return mSectionIngredients;
    }
}
