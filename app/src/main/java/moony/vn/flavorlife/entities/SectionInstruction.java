package moony.vn.flavorlife.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moony on 3/14/15.
 */
public class SectionInstruction {
    private String name;
    private int numberSection;
    private List<Step> mListSteps = new ArrayList<Step>();

    public SectionInstruction() {
        mListSteps.add(new Step());
    }

    public int getNumberSection() {
        return numberSection;
    }

    public void setNumberSection(int numberSection) {
        this.numberSection = numberSection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Step> getListSteps() {
        return mListSteps;
    }

    public void setListSteps(List<Step> mListIngredients) {
        this.mListSteps = mListIngredients;
    }

}
