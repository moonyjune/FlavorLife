package moony.vn.flavorlife.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ntq.api.model.OnDataChangedListener;
import com.ntq.fragments.NFragment;

import java.util.ArrayList;
import java.util.List;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.adapters.RecipePagerAdapter;
import moony.vn.flavorlife.api.ApiKey;
import moony.vn.flavorlife.api.UploadImage;
import moony.vn.flavorlife.api.model.DfeCreateRecipe;
import moony.vn.flavorlife.entities.Ingredient;
import moony.vn.flavorlife.entities.Recipe;
import moony.vn.flavorlife.entities.Section;
import moony.vn.flavorlife.entities.SectionIngredient;
import moony.vn.flavorlife.entities.SectionInstruction;
import moony.vn.flavorlife.entities.Step;
import moony.vn.flavorlife.layout.TabIndicator;

/**
 * Created by moony on 3/1/15.
 */
public class CreateRecipeFragment extends NFragment implements Response.ErrorListener, OnDataChangedListener {
    private ViewPager mRecipeViewPager;
    private TabIndicator mTabIndicator;
    private RecipePagerAdapter mRecipePagerAdapter;
    private List<SectionIngredient> mSectionIngredients;
    private List<SectionInstruction> mSectionInstructions;
    private Recipe mRecipe;
    private DfeCreateRecipe mDfeCreateRecipe;
    private List<Fragment> fragments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_recipe, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecipeViewPager = (ViewPager) view.findViewById(R.id.cnrf_view_content);
        mTabIndicator = (TabIndicator) view.findViewById(R.id.vp_tab_indicator);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionbar.syncActionBar(this);
        if (fragments == null) {
            fragments = new ArrayList<Fragment>();
            fragments.add(IngredientFragment2.newInstance());
            fragments.add(InstructionFragment.newInstance());
            fragments.add(IntroductionFragment.newInstance());
        }
        mRecipePagerAdapter = new RecipePagerAdapter(getChildFragmentManager(), fragments);
//        mRecipePagerAdapter = new RecipePagerAdapter(getChildFragmentManager());
        mRecipeViewPager.setAdapter(mRecipePagerAdapter);
        mTabIndicator.setViewPager(mRecipeViewPager, getListTabName());
        mTabIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private List<String> getListTabName() {
        List<String> listTabName = new ArrayList<String>();
        listTabName.add("Ingredients");
        listTabName.add("Instruction");
        listTabName.add("Introduction");
        return listTabName;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mRecipePagerAdapter != null) {
            mRecipePagerAdapter.getItem(2).onActivityResult(requestCode, resultCode, data);
        }
    }

    public void request() {
        collectData();
        if (mSectionIngredients != null && mSectionInstructions != null && mRecipe != null) {
            requestCreateRecipe();
        }
    }

    private void requestCreateRecipe() {
        if (mDfeCreateRecipe == null) {
            mDfeCreateRecipe = new DfeCreateRecipe(mApi);
            mDfeCreateRecipe.addDataChangedListener(this);
            mDfeCreateRecipe.addErrorListener(this);
        }
        mDfeCreateRecipe.makeRequest(mRecipe);
    }

    private void collectData() {
        for (int i = 0; i < mRecipePagerAdapter.getCount(); i++) {
            Fragment fragment = mRecipePagerAdapter.getItem(i);
            if (fragment instanceof IngredientFragment2) {
                System.out.println("Mj : ingredient");
                mSectionIngredients = ((IngredientFragment2) fragment).getSectionIngredients();
            } else if (fragment instanceof InstructionFragment) {
                System.out.println("Mj : instruction");
                mSectionInstructions = ((InstructionFragment) fragment).getSectionInstructions();
            } else if (fragment instanceof IntroductionFragment) {
                System.out.println("Mj : introduction");
                mRecipe = ((IntroductionFragment) fragment).getRecipe();
            } else {
                System.out.println("Mj : fragment");
            }
        }

        if (mSectionIngredients != null & mSectionInstructions != null) {
            List<Section> sections = new ArrayList<Section>();
            for (int j = 0; j < mSectionIngredients.size(); j++) {
                Section section = new Section();
                section.setName(mSectionIngredients.get(j).getName());
                section.setNumberSection(mSectionIngredients.get(j).getNumberSection());
                section.setListIngredient(new ArrayList<Ingredient>(mSectionIngredients.get(j).getListIngredients()));
                section.setListStep(new ArrayList<Step>(mSectionInstructions.get(j).getListSteps()));
                sections.add(section);
            }

            mRecipe.setListSection(new ArrayList<Section>(sections));
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onDataChanged() {
        if (mDfeCreateRecipe != null && mDfeCreateRecipe.isReady()) {
            String[] params = new String[3];
            params[0] = ApiKey.API_UPLOAD_IMAGE;
            params[1] = mRecipe.getImages();
            params[2] = String.valueOf(mDfeCreateRecipe.getRecipeId());
            new UploadImage() {
                @Override
                protected void onPostExecute(String result) {
                    super.onPostExecute(result);
                    System.out.println(result);
                }
            }.execute(params);
        }
    }

}
