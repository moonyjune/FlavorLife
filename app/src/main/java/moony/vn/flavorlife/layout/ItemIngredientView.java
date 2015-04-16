package moony.vn.flavorlife.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.adapters.SectionIngredientAdapter;
import moony.vn.flavorlife.entities.Ingredient;
import moony.vn.flavorlife.entities.SectionIngredient;

/**
 * Created by moony on 3/14/15.
 */
public class ItemIngredientView extends LinearLayout {
    private ListView mListSection;
    private SectionIngredientAdapter mSectionIngredientAdapter;
    private int mCurrentSection;

    public ItemIngredientView(Context context) {
        super(context);
        init();
    }

    public ItemIngredientView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemIngredientView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.item_ingredient, this);
        mListSection = (ListView) findViewById(R.id.list_section);
        List<SectionIngredient> sectionIngredientList = new ArrayList<SectionIngredient>();
        sectionIngredientList.add(new SectionIngredient());
        mSectionIngredientAdapter = new SectionIngredientAdapter(getContext(), 0, sectionIngredientList);
        mListSection.setAdapter(mSectionIngredientAdapter);
        mListSection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCurrentSection = i;
            }
        });
    }

    public void addSection(SectionIngredient sectionIngredient) {
        mSectionIngredientAdapter.addItem(sectionIngredient);
        mListSection.setSelection(mSectionIngredientAdapter.getCount() - 1);
    }

    public void addIngredient(Ingredient ingredient) {
        mSectionIngredientAdapter.getItem(mCurrentSection).getListIngredients().add(ingredient);
        mSectionIngredientAdapter.notifyDataSetChanged();
    }

    public void display(Ingredient sectionIngredient) {

    }
}
