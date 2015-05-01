package moony.vn.flavorlife.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by moony on 4/22/15.
 */
public class Chapter {
    @SerializedName("num_chapter")
    private int numChapter;
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String name;

    public int getNumChapter() {
        return numChapter;
    }

    public void setNumChapter(int numChapter) {
        this.numChapter = numChapter;
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
}
