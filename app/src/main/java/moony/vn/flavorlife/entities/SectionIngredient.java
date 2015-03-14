package moony.vn.flavorlife.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moony on 3/14/15.
 */
public class SectionIngredient {
    private String name;
    private List<Ingredient> mListIngredients = new ArrayList<Ingredient>();

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
}
