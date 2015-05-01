package moony.vn.flavorlife.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by moony on 3/15/15.
 */
public class Step implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private String image;
    @SerializedName("number_step")
    private int numberStep;
    @SerializedName("content")
    private String content;
    @SerializedName("tip_note")
    private String tipNote;
    @SerializedName("section_id")
    private int idSection;
    @SerializedName("recipe_id")
    private int idRecipe;

    public Step() {
//        this.name = "name";
//        this.image = "image";
//        this.numberStep = 1;
//        this.content = "content";
//        this.tipNote = "tipNote";
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNumberStep() {
        return numberStep;
    }

    public void setNumberStep(int numberStep) {
        this.numberStep = numberStep;
        this.name = "Step " + numberStep;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTipNote() {
        return tipNote;
    }

    public void setTipNote(String tipNote) {
        this.tipNote = tipNote;
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
