package moony.vn.flavorlife.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by moony on 5/11/15.
 */
public class SearchRecipeCondition implements Serializable {
    @SerializedName("name")
    private String name;
    @SerializedName("kind")
    private int kind;
    @SerializedName("level")
    private int level;

    public SearchRecipeCondition() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
