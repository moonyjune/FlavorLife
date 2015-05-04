package moony.vn.flavorlife.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by moony on 4/22/15.
 */
public class Chapter implements Serializable {
    @SerializedName("num_chapter")
    private int numChapter;
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String name;
    @SerializedName("book_id")
    private int bookId;
    private int userId;
    private boolean isChosen;

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

    public boolean isChosen() {
        return isChosen;
    }

    public void setChosen(boolean isChosen) {
        this.isChosen = isChosen;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
