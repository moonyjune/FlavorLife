package moony.vn.flavorlife.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moony on 3/14/15.
 */
public class SectionIngredient implements Parcelable{
    private String name;
    private int numberSection;
    private List<Ingredient> mListIngredients = new ArrayList<Ingredient>();

    public SectionIngredient() {
        mListIngredients.add(new Ingredient());
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(numberSection);
        dest.writeList(mListIngredients);
    }

    public SectionIngredient(Parcel source) {
        name = source.readString();
        numberSection = source.readInt();
        source.readList(mListIngredients, Ingredient.class.getClassLoader());
    }

    public static final Creator<SectionIngredient> CREATOR = new Creator<SectionIngredient>() {
        @Override
        public SectionIngredient createFromParcel(Parcel source) {
            return new SectionIngredient(source);
        }

        @Override
        public SectionIngredient[] newArray(int size) {
            return new SectionIngredient[size];
        }
    };
}
