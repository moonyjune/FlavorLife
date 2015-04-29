package moony.vn.flavorlife.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by moony on 3/14/15.
 */
public class Ingredient {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private String type;
    @SerializedName("value")
    private float value;
    @SerializedName("unit")
    private String unit = "";
    @SerializedName("images")
    private String images;
    @SerializedName("section_id")
    private int idSection;
    @SerializedName("recipe_id")
    private int idRecipe;

    public Ingredient() {
//        this.name = "ingredient";
//        this.type = "type";
//        this.value = 200;
//        this.unit = "unit";
//        this.images = "images";
//        this.idSection = 1;
//        this.idRecipe = 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getIdSection() {
        return idSection;
    }

    public void setIdSection(int idSection) {
        this.idSection = idSection;
    }

    public int getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(int idRecipe) {
        this.idRecipe = idRecipe;
    }
}
