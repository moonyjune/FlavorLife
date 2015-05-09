package moony.vn.flavorlife.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ntq.api.model.OnDataChangedListener;
import com.ntq.fragments.NFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.adapters.RecipePagerAdapter;
import moony.vn.flavorlife.api.ApiKey;
import moony.vn.flavorlife.api.UploadImage;
import moony.vn.flavorlife.api.model.DfeCreateRecipe;
import moony.vn.flavorlife.api.model.DfeEditRecipe;
import moony.vn.flavorlife.api.model.DfeUpgradeRecipe;
import moony.vn.flavorlife.entities.Ingredient;
import moony.vn.flavorlife.entities.Recipe;
import moony.vn.flavorlife.entities.Section;
import moony.vn.flavorlife.entities.SectionIngredient;
import moony.vn.flavorlife.entities.SectionInstruction;
import moony.vn.flavorlife.entities.Step;
import moony.vn.flavorlife.layout.TabIndicator;
import moony.vn.flavorlife.utils.DialogUtils;
import moony.vn.flavorlife.utils.ValidateDataUtils;

/**
 * Created by moony on 3/1/15.
 */
public class CreateRecipeFragment extends NFragment implements Response.ErrorListener, OnDataChangedListener {
    private static final String FLAG = "flag";
    private static final String RECIPE = "recipe";
    private static final String OLD_RECIPE = "old_recipe";
    public static final int FLAG_CREATE = 0;
    public static final int FLAG_EDIT = 1;
    public static final int FLAG_UPGRADE = 2;
    private ViewPager mRecipeViewPager;
    private TabIndicator mTabIndicator;
    private RecipePagerAdapter mRecipePagerAdapter;
    private List<SectionIngredient> mSectionIngredients;
    private List<SectionInstruction> mSectionInstructions;
    private Recipe mRecipe;
    private Recipe mOldRecipe;
    private DfeCreateRecipe mDfeCreateRecipe;
    private DfeUpgradeRecipe mDfeUpgradeRecipe;
    private DfeEditRecipe mDfeEditRecipe;
    private List<Fragment> fragments;
    private int mFlag;
    private boolean mPageIngredientSelected, mPagedInstructionSelected;

    private OnDataChangedListener onUpgradeDataChanged = new OnDataChangedListener() {
        @Override
        public void onDataChanged() {
            hideDialogLoading();
            if (mDfeUpgradeRecipe != null && mDfeUpgradeRecipe.isReady()) {
                if (checkChangeImage()) {
                    uploadImage();
                } else {
                    Toast.makeText(getActivity(), "Upgrade recipe successfully!", Toast.LENGTH_SHORT).show();
                    mNavigationManager.showPageWithoutStack(RecipeDetailFragment.newInstance(mDfeUpgradeRecipe.getRecipeId()));
                }
            }
        }
    };

    private OnDataChangedListener onEditDataChanged = new OnDataChangedListener() {
        @Override
        public void onDataChanged() {
            hideDialogLoading();
            if (mDfeEditRecipe != null && mDfeEditRecipe.isReady()) {
                if (checkChangeImage()) {
                    uploadImage();
                } else {
                    Toast.makeText(getActivity(), "Edit recipe successfully!", Toast.LENGTH_SHORT).show();
                    mNavigationManager.showPageWithoutStack(RecipeDetailFragment.newInstance(mRecipe.getId()));
                }
            }
        }
    };

    public static CreateRecipeFragment newInstance(Recipe recipe, int flag) {
        CreateRecipeFragment createRecipeFragment = new CreateRecipeFragment();
        Bundle bundle = new Bundle();
        if (recipe != null)
            bundle.putSerializable(RECIPE, recipe);
        bundle.putInt(FLAG, flag);
        createRecipeFragment.setArguments(bundle);
        return createRecipeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mFlag = savedInstanceState.getInt(FLAG);
            mRecipe = (Recipe) savedInstanceState.getSerializable(RECIPE);
            mOldRecipe = (Recipe) savedInstanceState.getSerializable(OLD_RECIPE);
        } else {
            mFlag = getArguments().getInt(FLAG);
            mRecipe = (Recipe) getArguments().getSerializable(RECIPE);
            mOldRecipe = (Recipe) getArguments().getSerializable(RECIPE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mRecipe != null)
            outState.putSerializable(RECIPE, mRecipe);
        outState.putInt(FLAG, mFlag);
        if (mOldRecipe != null)
            outState.putSerializable(OLD_RECIPE, mOldRecipe);
    }

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
        setup();
    }

    private void setup() {
        if (fragments == null) {
            fragments = new ArrayList<Fragment>();
            switch (mFlag) {
                case FLAG_CREATE:
                    fragments.add(new IngredientFragment2());
                    fragments.add(new InstructionFragment());
                    fragments.add(new IntroductionFragment());
                    break;
                case FLAG_EDIT:
                case FLAG_UPGRADE:
                    Recipe temp1 = new Recipe();
                    Recipe temp2 = new Recipe();
                    Recipe temp3 = new Recipe();
                    temp1.updateRecipe(mRecipe, true);
                    temp2.updateRecipe(mRecipe, true);
                    temp3.updateRecipe(mRecipe, true);
                    fragments.add(IngredientFragment2.newInstance(temp1));
                    fragments.add(InstructionFragment.newInstance(temp2));
                    fragments.add(IntroductionFragment.newInstance(temp3, mFlag));
                    break;
            }
        }
        if (mRecipePagerAdapter == null)
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
                switch (position) {
                    case 0:
                        if (!mPageIngredientSelected)
                            mPageIngredientSelected = true;
                        break;
                    case 1:
                        if (!mPagedInstructionSelected)
                            mPagedInstructionSelected = true;
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (mFlag == FLAG_CREATE) {
            mRecipeViewPager.setCurrentItem(0);
        } else {
            mRecipeViewPager.setCurrentItem(fragments.size() - 1);
        }
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
            int code = validateRecipe();
            if (code == ValidateDataUtils.VALID_DATA) {
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
                switch (mFlag) {
                    case CreateRecipeFragment.FLAG_CREATE:
                        mRecipe.setTypeRecipe(Recipe.Type.CREATE_NEW);
                        requestCreateRecipe();
                        break;
                    case CreateRecipeFragment.FLAG_UPGRADE:
                        mRecipe.setTypeRecipe(Recipe.Type.UPGRADE);
                        requestUpgradeRecipe();
                        break;
                    case CreateRecipeFragment.FLAG_EDIT:
                        mRecipe.setTypeRecipe(Recipe.Type.EDIT);
                        if (checkChangeData()) {
                            requestEditRecipe();
                        } else {
                            if (checkChangeImage()) {
                                uploadImage();
                            } else {
                                showDialogMessageError("You haven't changed anything yet...");
                            }
                        }
                        break;

                }
            } else {
                showDialogMessageError(ValidateDataUtils.get(mContext, code));
            }
        } else {
            showDialogMessageError("Collect data fail");
        }
    }

    private boolean checkChangeImage() {
        if (mOldRecipe.getImages().equals(mRecipe.getImages())) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkChangeData() {
        if (!mOldRecipe.getName().equals(mRecipe.getName()))
            return true;
        if (!mOldRecipe.getIntroduction().equals(mRecipe.getIntroduction()))
            return true;
        if (!mOldRecipe.getTipNote().equals(mRecipe.getTipNote()))
            return true;
        if (!mOldRecipe.getAuthorComments().equals(mRecipe.getAuthorComments()))
            return true;
        if (mOldRecipe.getIdBook() != mRecipe.getIdBook())
            return true;
        if (mOldRecipe.getIdChapter() != mRecipe.getIdChapter())
            return true;
        if (mOldRecipe.getKind() != mRecipe.getKind())
            return true;
        if (mOldRecipe.getCookingTime() != mRecipe.getCookingTime())
            return true;
        if (mOldRecipe.getLevel() != mRecipe.getLevel())
            return true;
        if (mOldRecipe.getListSection().size() != mRecipe.getListSection().size()) {
            return true;
        } else {
            for (int i = 0; i < mOldRecipe.getListSection().size(); i++) {
                Section section = mOldRecipe.getListSection().get(i);
                Section section1 = mRecipe.getListSection().get(i);
                if (!section.getName().equals(section1.getName())) {
                    return true;
                }
                if (section.getListIngredient().size() != section1.getListIngredient().size()) {
                    return true;
                } else {
                    for (int j = 0; j < section.getListIngredient().size(); j++) {
                        Ingredient ingredient = section.getListIngredient().get(j);
                        Ingredient ingredient1 = section1.getListIngredient().get(j);
                        if (ingredient.getValue() != ingredient1.getValue())
                            return true;
                        if (!ingredient.getName().equals(ingredient1.getName()))
                            return true;
                        if (!ingredient.getUnit().equals(ingredient1.getUnit()))
                            return true;
                    }
                }

                if (section.getListStep().size() != section1.getListStep().size()) {
                    return true;
                } else {
                    for (int j = 0; j < section.getListStep().size(); j++) {
                        Step step = section.getListStep().get(j);
                        Step step1 = section1.getListStep().get(j);
                        if (!step.getContent().equals(step1.getContent()))
                            return true;
                    }
                }
            }
        }
        return false;
    }

    private int validateRecipe() {
        if (mSectionIngredients.size() == 0)
            return ValidateDataUtils.SECTION_INGREDIENT_EMPTY;//section ingredient empty
        if (mSectionInstructions.size() == 0)
            return ValidateDataUtils.SECTION_INSTRUCTION_EMPTY;//section instruction empty
        if (mSectionIngredients.size() != mSectionInstructions.size())
            return ValidateDataUtils.NUMBER_SECTION_NOT_THE_SAME;//khong cung so section
        for (int i = 0; i < mSectionIngredients.size(); i++) {
            if (mSectionIngredients.get(i).getName() != null && !mSectionIngredients.get(i).getName().isEmpty()) {
                //valid
            } else {
                return ValidateDataUtils.SECTION_INGREDIENT_NAME_EMPTY;//section ingredient name empty
            }
        }

        for (int i = 0; i < mSectionInstructions.size(); i++) {
            if (mSectionInstructions.get(i).getName() != null && !mSectionInstructions.get(i).getName().isEmpty()) {
                //valid
            } else {
                return ValidateDataUtils.SECTION_INSTRUCTION_NAME_EMPTY;//section instruction name empty
            }
        }
        for (int i = 0; i < mSectionInstructions.size(); i++) {
            if (!mSectionInstructions.get(i).getName().equals(mSectionIngredients.get(i).getName()))
                return ValidateDataUtils.SECTION_NAME_NOT_THE_SAME;//khac ten section
        }
        for (int i = 0; i < mSectionIngredients.size(); i++) {
            if (mSectionIngredients.get(i).getListIngredients().isEmpty())
                return ValidateDataUtils.LIST_INGREDIENTS_EMPTY;//list ingredient empty
        }
        for (int i = 0; i < mSectionInstructions.size(); i++) {
            if (mSectionInstructions.get(i).getListSteps().isEmpty())
                return ValidateDataUtils.LIST_STEPS_EMPTY;//list step empty
        }
        for (int i = 0; i < mSectionIngredients.size(); i++) {
            for (int j = 0; j < mSectionIngredients.get(i).getListIngredients().size(); j++) {
                Ingredient ingredient = mSectionIngredients.get(i).getListIngredients().get(j);
                if (ingredient.getName() != null && !ingredient.getName().isEmpty() && ingredient.getValue() > 0 && ingredient.getUnit() != null) {
                    //valid
                } else {
                    return ValidateDataUtils.INGREDIENT_CONTENT_EMPTY;//ingredient invalid
                }
            }
        }
        for (int i = 0; i < mSectionInstructions.size(); i++) {
            for (int j = 0; j < mSectionInstructions.get(i).getListSteps().size(); j++) {
                Step step = mSectionInstructions.get(i).getListSteps().get(j);
                if (step.getContent() != null && !step.getContent().isEmpty()) {
                    //valid
                } else {
                    return ValidateDataUtils.STEP_CONTENT_EMPTY;//step invalid
                }
            }
        }

        if (mRecipe.getName() != null && !mRecipe.getName().isEmpty()) {
            //valid
        } else {
            return ValidateDataUtils.RECIPE_NAME_EMPTY;//recipe name empty
        }

        if (mRecipe.getIntroduction() != null && !mRecipe.getIntroduction().isEmpty()) {

        } else {
            return ValidateDataUtils.RECIPE_INTRO_EMPTY;//recipe intro empty
        }
        //Tipnote co the empty hoac null

        if (mRecipe.getLevel() == 0)
            return ValidateDataUtils.RECIPE_LEVEL_EMPTY;//level = 0
        if (mRecipe.getCookingTime() == 0)
            return ValidateDataUtils.RECIPE_COOKING_TIME_EMPTY;//chua set thoi gian
        if (mRecipe.getKind() == 0)
            return ValidateDataUtils.RECIPE_KIND_EMPTY;//chua chon kind
        if (mRecipe.getIdBook() == 0)
            return ValidateDataUtils.RECIPE_BOOK_EMPTY;//chua chon book
        if (mRecipe.getIdChapter() == 0)
            return ValidateDataUtils.RECIPE_CHAPTER_EMPTY;//chua chon chapter
        if (mRecipe.getImages() != null && !mRecipe.getImages().isEmpty()) {

        } else {
            return ValidateDataUtils.RECIPE_IMAGE_EMPTY;//chua co anh
        }
        //comments co the empty
        return ValidateDataUtils.VALID_DATA;
    }

    private void requestEditRecipe() {
        if (mDfeEditRecipe == null) {
            mDfeEditRecipe = new DfeEditRecipe(mApi);
            mDfeEditRecipe.addErrorListener(this);
            mDfeEditRecipe.addDataChangedListener(onEditDataChanged);
        }
        mDfeEditRecipe.makeRequest(mRecipe);
    }

    private void requestUpgradeRecipe() {
        showDialogLoading();
        if (mDfeUpgradeRecipe == null) {
            mDfeUpgradeRecipe = new DfeUpgradeRecipe(mApi);
            mDfeUpgradeRecipe.addDataChangedListener(onUpgradeDataChanged);
            mDfeUpgradeRecipe.addErrorListener(this);
        }
        mDfeUpgradeRecipe.makeRequest(mRecipe);
    }

    private void requestCreateRecipe() {
        showDialogLoading();
        if (mDfeCreateRecipe == null) {
            mDfeCreateRecipe = new DfeCreateRecipe(mApi);
            mDfeCreateRecipe.addDataChangedListener(this);
            mDfeCreateRecipe.addErrorListener(this);
        }
        mDfeCreateRecipe.makeRequest(mRecipe);
    }

    public void collectData() {
        if (mFlag == FLAG_CREATE) {
            mPageIngredientSelected = true;
            mPagedInstructionSelected = true;
        }
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            if (fragment instanceof IngredientFragment2) {
                System.out.println("Mj : ingredient");
                if (mPageIngredientSelected) {
                    mSectionIngredients = ((IngredientFragment2) fragment).getSectionIngredients();
                } else {
                    mSectionIngredients = null;
                }
            } else if (fragment instanceof InstructionFragment) {
                System.out.println("Mj : instruction");
                if (mPagedInstructionSelected) {
                    mSectionInstructions = ((InstructionFragment) fragment).getSectionInstructions();
                } else {
                    mSectionInstructions = null;
                }
            } else if (fragment instanceof IntroductionFragment) {
                System.out.println("Mj : introduction");
                if (mFlag == FLAG_CREATE) {
                    mRecipe = ((IntroductionFragment) fragment).getRecipe();
                } else {
                    mRecipe = new Recipe();
                    mRecipe.updateRecipe(((IntroductionFragment) fragment).getRecipe(), false);
                    if (mSectionIngredients == null)
                        mSectionIngredients = mOldRecipe.getListSectionIngredients();
                    if (mSectionInstructions == null)
                        mSectionInstructions = mOldRecipe.getListSectionInstructions();
                }
            } else {
                System.out.println("Mj : fragment");
            }
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        hideDialogLoading();
        showDialogMessageError(error);
    }

    @Override
    public void onDataChanged() {
        hideDialogLoading();
        if (mDfeCreateRecipe != null && mDfeCreateRecipe.isReady()) {
            uploadImage();
        }
    }

    private void uploadImage() {
        showDialogLoading();
        String[] params = new String[3];
        params[0] = ApiKey.API_UPLOAD_IMAGE;
        params[1] = mRecipe.getImages();
        switch (mFlag) {
            case FLAG_CREATE:
                params[2] = String.valueOf(mDfeCreateRecipe.getRecipeId());
                break;
            case FLAG_EDIT:
                params[2] = String.valueOf(mRecipe.getId());
                break;
            case FLAG_UPGRADE:
                params[2] = String.valueOf(mDfeUpgradeRecipe.getRecipeId());
                break;
        }
        if (mRecipe.getImages() != null && !mRecipe.getImages().isEmpty()) {
            new UploadImage() {
                @Override
                protected void onPostExecute(String result) {
                    super.onPostExecute(result);
                    hideDialogLoading();
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt(ApiKey.CODE);
                        if (code != ApiKey.SUCCESS) {
                            DialogUtils.getInstance().showDialogMessage(getActivity(), "Upload image fail. Do you want to retry ?", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    uploadImage();
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (mFlag == FLAG_CREATE) {
                                        Toast.makeText(getActivity(), "You've created a new recipe!", Toast.LENGTH_SHORT).show();
                                        mNavigationManager.showPageWithoutStack(RecipeDetailFragment.newInstance(mDfeCreateRecipe.getRecipeId()));
                                    } else if (mFlag == FLAG_EDIT) {
                                        Toast.makeText(getActivity(), "You've edit a new recipe!", Toast.LENGTH_SHORT).show();
//                                        mNavigationManager.showPageWithoutStack(RecipeDetailFragment.newInstance(mRecipe.getId()));
                                        getActivity().finish();
                                    } else {
                                        Toast.makeText(getActivity(), "You've upgrade a new recipe!", Toast.LENGTH_SHORT).show();
                                        getActivity().finish();
//                                        mNavigationManager.showPageWithoutStack(RecipeDetailFragment.newInstance(mDfeUpgradeRecipe.getRecipeId()));
                                    }
                                }
                            });
                        } else {
                            if (mFlag == FLAG_CREATE) {
                                Toast.makeText(getActivity(), "You've created a new recipe successfully!", Toast.LENGTH_SHORT).show();
                                mNavigationManager.showPageWithoutStack(RecipeDetailFragment.newInstance(mDfeCreateRecipe.getRecipeId()));
                            } else if (mFlag == FLAG_EDIT) {
                                Toast.makeText(getActivity(), "You've edited a recipe successfully!", Toast.LENGTH_SHORT).show();
//                                mNavigationManager.showPageWithoutStack(RecipeDetailFragment.newInstance(mRecipe.getId()));
                                getActivity().finish();
                            } else {
                                Toast.makeText(getActivity(), "You've upgraded a recipe successfully!", Toast.LENGTH_SHORT).show();
//                                mNavigationManager.showPageWithoutStack(RecipeDetailFragment.newInstance(mDfeUpgradeRecipe.getRecipeId()));
                                getActivity().finish();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.execute(params);
        }
    }

    public boolean hasData() {
        if (mFlag == FLAG_CREATE) {
            collectData();
        } else {
            if (mRecipe != null) {
                mSectionIngredients = mRecipe.getListSectionIngredients();
                mSectionInstructions = mRecipe.getListSectionInstructions();
            }
        }

        if (mRecipe == null && mSectionIngredients == null && mSectionInstructions == null)
            return false;
        else {
            if (mSectionIngredients != null)
                for (int i = 0; i < mSectionIngredients.size(); i++) {
                    SectionIngredient sectionIngredient = mSectionIngredients.get(i);
                    if (sectionIngredient.getName() != null && !sectionIngredient.getName().isEmpty())
                        return true;
                    for (int j = 0; j < sectionIngredient.getListIngredients().size(); j++) {
                        Ingredient ingredient = sectionIngredient.getListIngredients().get(j);
                        if (ingredient.getName() != null && !ingredient.getName().isEmpty())
                            return true;
                        if (ingredient.getUnit() != null && !ingredient.getUnit().isEmpty())
                            return true;
                        if (ingredient.getValue() != 0)
                            return true;
                    }
                }

            if (mSectionInstructions != null)
                for (int i = 0; i < mSectionInstructions.size(); i++) {
                    SectionInstruction sectionInstruction = mSectionInstructions.get(i);
                    if (sectionInstruction.getName() != null && !sectionInstruction.getName().isEmpty())
                        return true;
                    for (int j = 0; j < sectionInstruction.getListSteps().size(); j++) {
                        Step step = sectionInstruction.getListSteps().get(j);
                        if (step.getContent() != null && !step.getContent().isEmpty())
                            return true;
                    }
                }

            if (mRecipe.getImages() != null && !mRecipe.getName().isEmpty())
                return true;
            if (mRecipe.getIntroduction() != null && !mRecipe.getIntroduction().isEmpty())
                return true;
            if (mRecipe.getTipNote() != null && !mRecipe.getTipNote().isEmpty())
                return true;
            if (mRecipe.getAuthorComments() != null && !mRecipe.getAuthorComments().isEmpty())
                return true;
            if (mRecipe.getImages() != null && !mRecipe.getImages().isEmpty())
                return true;
            if (mRecipe.getLevel() != 0)
                return true;
            if (mRecipe.getIdBook() != 0)
                return true;
            if (mRecipe.getIdChapter() != 0)
                return true;
            if (mRecipe.getCookingTime() != 0)
                return true;
            if (mRecipe.getKind() != 0)
                return true;
            return false;
        }
    }

    public int getFlag() {
        return mFlag;
    }

    @Override
    public void onRefresh() {
        mRecipe = mOldRecipe;
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
//        for (int i = 0; i < mRecipePagerAdapter.getCount(); i++) {
//            Fragment fragment = mRecipePagerAdapter.getItem(i);
            if (fragment instanceof IngredientFragment2) {
                ((IngredientFragment2) fragment).resetData();
            } else if (fragment instanceof InstructionFragment) {
                ((InstructionFragment) fragment).clearData();
            } else if (fragment instanceof IntroductionFragment) {
                ((IntroductionFragment) fragment).clearData();
            }
        }
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setEnabled(true);
        }
//        fragments.clear();
//        fragments = null;
//        mRecipePagerAdapter = null;
//        mRecipeViewPager.setAdapter(null);
//        setup();
    }
}
