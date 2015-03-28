package moony.vn.flavorlife.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moony on 3/14/15.
 */
public class SectionIngredient implements Parcelable {
    private String name;
    private int numberSection;
    private List<Ingredient> mListIngredients = new ArrayList<Ingredient>();

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

    public List<Ingredient> getListIngredients() {
        return mListIngredients;
    }

    public void setListIngredients(List<Ingredient> mListIngredients) {
        this.mListIngredients = mListIngredients;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
