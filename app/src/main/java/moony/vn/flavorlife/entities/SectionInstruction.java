package moony.vn.flavorlife.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moony on 3/14/15.
 */
public class SectionInstruction implements Parcelable {
    private String name;
    private int numberSection;
    private List<Step> mListSteps = new ArrayList<Step>();

    public SectionInstruction() {
        Step step = new Step();
        step.setNumberStep(1);
        mListSteps.add(step);
    }

    public SectionInstruction(Parcel source) {
        name = source.readString();
        numberSection = source.readInt();
        source.readList(mListSteps, Step.class.getClassLoader());
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(numberSection);
        dest.writeList(mListSteps);
    }

    public static Creator<SectionInstruction> CREATOR = new Creator<SectionInstruction>() {
        @Override
        public SectionInstruction createFromParcel(Parcel source) {
            return new SectionInstruction(source);
        }

        @Override
        public SectionInstruction[] newArray(int size) {
            return new SectionInstruction[size];
        }
    };
}
