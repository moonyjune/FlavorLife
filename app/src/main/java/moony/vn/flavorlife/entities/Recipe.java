package moony.vn.flavorlife.entities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by moony on 3/4/15.
 */
public class Recipe {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("imagse")
    private String images;
    @SerializedName("type")
    private int type;
    @SerializedName("kind")
    private int kind;
    @SerializedName("level")
    private int level;
    @SerializedName("likes")
    private int likes;
    @SerializedName("used")
    private int used;
    @SerializedName("cooking_time")
    private int cookingTime;
    @SerializedName("create_time")
    private Date createTime;
    @SerializedName("tip_note")
    private String tipNote;
    @SerializedName("author_comments")
    private String authorComments;
    @SerializedName("sections")
    private ArrayList<Section> listSection = new ArrayList<Section>();
    @SerializedName("user_id")
    private int idUser;
    @SerializedName("chapter_id")
    private int idChapter;
    @SerializedName("introduction")
    private String introduction;
    @SerializedName("author_name")
    private String authorName;

    public Recipe() {
//        this.name = "name";
//        this.images = "images";
//        this.type = 1;
//        this.kind = 1;
//        this.level = 1;
//        this.likes = 1;
//        this.used = 1;
//        this.cookingTime = 10;
//        this.createTime = new Date();
//        this.tipNote = "tipNote";
//        this.authorComments = "authorComments";
//        this.idChapter = 1;
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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTipNote() {
        return tipNote;
    }

    public void setTipNote(String tipNote) {
        this.tipNote = tipNote;
    }

    public String getAuthorComments() {
        return authorComments;
    }

    public void setAuthorComments(String authorComments) {
        this.authorComments = authorComments;
    }

    public ArrayList<Section> getListSection() {
        return listSection;
    }

    public void setListSection(ArrayList<Section> listSection) {
        this.listSection = listSection;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdChapter() {
        return idChapter;
    }

    public void setIdChapter(int idChapter) {
        this.idChapter = idChapter;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
