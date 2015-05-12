package moony.vn.flavorlife.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by moony on 3/11/15.
 */
public class Cookbook implements Serializable {
    @SerializedName("image")
    private String image;
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("intro")
    private String intro;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("create_time")
    private Date createTime;
    @SerializedName("num_chapter")
    private int numChapter;
    private boolean isChosen;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public boolean isChosen() {
        return isChosen;
    }

    public void setChosen(boolean isChosen) {
        this.isChosen = isChosen;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getNumChapter() {
        return numChapter;
    }

    public void setNumChapter(int numChapter) {
        this.numChapter = numChapter;
    }

    public void update(Cookbook mOldBook) {
        setId(mOldBook.getId());
        setName(mOldBook.getName());
        setIntro(mOldBook.getIntro());
        setCreateTime(mOldBook.getCreateTime());
        setChosen(mOldBook.isChosen());
        setUserId(mOldBook.getUserId());
        setImage(mOldBook.getImage());
        setNumChapter(mOldBook.getNumChapter());
    }
}
