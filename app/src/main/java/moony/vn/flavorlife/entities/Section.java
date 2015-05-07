package moony.vn.flavorlife.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by moony on 3/25/15.
 */
public class Section implements Serializable{
    @SerializedName("id")
    private int id;
    @SerializedName("number_section")
    private int numberSection;
    @SerializedName("name")
    private String name;
    @SerializedName("ingredients")
    private ArrayList<Ingredient> listIngredient = new ArrayList<Ingredient>();
    @SerializedName("steps")
    private ArrayList<Step> listStep = new ArrayList<Step>();
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public ArrayList<Ingredient> getListIngredient() {
        return listIngredient;
    }

    public void setListIngredient(ArrayList<Ingredient> listIngredient) {
        this.listIngredient = listIngredient;
    }

    public ArrayList<Step> getListStep() {
        return listStep;
    }

    public void setListStep(ArrayList<Step> listStep) {
        this.listStep = listStep;
    }

    public void updateListIngredients(ArrayList<Ingredient> listIngredient) {
        this.listIngredient.clear();
        for (int i = 0; i < listIngredient.size(); i++) {
            Ingredient ingredient = new Ingredient();
            Ingredient temp = listIngredient.get(i);
            ingredient.setName(temp.getName());
            ingredient.setValue(temp.getValue());
            ingredient.setId(temp.getId());
            ingredient.setUnit(temp.getUnit());
            ingredient.setIdRecipe(temp.getIdRecipe());
            ingredient.setIdSection(temp.getIdSection());
            this.listIngredient.add(ingredient);
        }
    }

    public void updateListSteps(ArrayList<Step> listStep) {
        this.listStep.clear();
        for (int i = 0; i < listStep.size(); i++) {
            Step step = new Step();
            Step temp = listStep.get(i);
            step.setId(temp.getId());
            step.setNumberStep(temp.getNumberStep());
            step.setIdSection(temp.getIdSection());
            step.setContent(temp.getContent());
            this.listStep.add(step);
        }
    }
}
