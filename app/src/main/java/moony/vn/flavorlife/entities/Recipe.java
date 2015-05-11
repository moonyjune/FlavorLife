package moony.vn.flavorlife.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by moony on 3/4/15.
 */
public class Recipe implements Serializable {
    private static final String[] kindNames = new String[]{"Starter", "Main course", "Desserts"};
    public static final int STARTER = 1;
    public static final int MAIN_COURSE = 2;
    public static final int DESSERT = 3;

    private static final int CREATE_NEW = 0;
    private static final int EDIT = 1;
    private static final int UPGRADE = 2;
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("images")
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
    private String authorName = "author name default";
    @SerializedName("book_name")
    private String bookName;
    @SerializedName("chapter_name")
    private String chapterName;
    @SerializedName("is_liked")
    private int isLiked;
    @SerializedName("is_used")
    private int isUsed;
    @SerializedName("is_bookmarked")
    private int isBookmarked;
    @SerializedName("book_id")
    private int idBook;
    @SerializedName("from_author")
    private String fromAuthor = "author name default";
    @SerializedName("from_recipe_name")
    private String fromRecipeName = "recipe name default";
    @SerializedName("from_recipe_id")
    private int fromRecipeId;
    @SerializedName("author_image")
    private String authorImage;

    private ArrayList<SectionIngredient> listSectionIngredients;
    private ArrayList<SectionInstruction> listSectionInstructions;

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

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public static enum Type {
        CREATE_NEW,
        EDIT,
        UPGRADE,
        NONE
    }

    public void setTypeRecipe(Type type) {
        switch (type) {
            case CREATE_NEW:
                this.type = CREATE_NEW;
                break;
            case EDIT:
                this.type = EDIT;
                break;
            case UPGRADE:
                this.type = UPGRADE;
                break;
        }
    }

    private Type getTypeRecipe() {
        switch (type) {
            case CREATE_NEW:
                return Type.CREATE_NEW;
            case EDIT:
                return Type.EDIT;
            case UPGRADE:
                return Type.UPGRADE;
        }
        return Type.NONE;
    }

    public boolean isCreateNew() {
        if (getTypeRecipe() == Type.CREATE_NEW)
            return true;
        return false;
    }

    public boolean isEdit() {
        if (getTypeRecipe() == Type.EDIT)
            return true;
        return false;
    }

    public boolean isUpgrade() {
        if (getTypeRecipe() == Type.UPGRADE)
            return true;
        return false;
    }


    public String getTypePrefix() {
        switch (getType()) {
            case CREATE_NEW:
            case EDIT:
                return "Created by ";
            case UPGRADE:
                return "Upgraded by ";
        }
        return null;
    }

    public String getKindName() {
        switch (kind) {
            case STARTER:
                return kindNames[0];
            case MAIN_COURSE:
                return kindNames[1];
            case DESSERT:
                return kindNames[2];
        }
        return null;
    }

    public ArrayList<SectionIngredient> getListSectionIngredients() {
        if (listSectionIngredients == null) {
            listSectionIngredients = new ArrayList<SectionIngredient>();
        }
        if (listSectionIngredients.isEmpty()) {
            for (int i = 0; i < listSection.size(); i++) {
                SectionIngredient sectionIngredient = new SectionIngredient();
                sectionIngredient.setId(listSection.get(i).getId());
                sectionIngredient.setName(listSection.get(i).getName());
                sectionIngredient.setNumberSection(listSection.get(i).getNumberSection());
                sectionIngredient.setListIngredients(listSection.get(i).getListIngredient());
                listSectionIngredients.add(sectionIngredient);
            }
        }
        return listSectionIngredients;
    }

    public ArrayList<SectionInstruction> getListSectionInstructions() {
        if (listSectionInstructions == null) {
            listSectionInstructions = new ArrayList<SectionInstruction>();
        }
        if (listSectionInstructions.isEmpty()) {
            for (int i = 0; i < listSection.size(); i++) {
                SectionInstruction sectionInstruction = new SectionInstruction();
                sectionInstruction.setId(listSection.get(i).getId());
                sectionInstruction.setName(listSection.get(i).getName());
                sectionInstruction.setNumberSection(listSection.get(i).getNumberSection());
                sectionInstruction.setListSteps(listSection.get(i).getListStep());
                listSectionInstructions.add(sectionInstruction);
            }
        }
        return listSectionInstructions;
    }

    public boolean isLiked() {
        if (isLiked == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void setIsLiked(boolean isLiked) {
        if (isLiked) {
            this.isLiked = 1;
        } else {
            this.isLiked = 0;
        }
    }

    public boolean isUsed() {
        if (isUsed == 1)
            return true;
        return false;
    }

    public void setIsUsed(boolean isUsed) {
        this.isUsed = 0;
        if (isUsed)
            this.isUsed = 1;
    }

    public boolean isBookmarked() {
        if (isBookmarked == 1)
            return true;
        return false;
    }

    public void setIsBookmarked(boolean isBookmarked) {
        this.isBookmarked = 0;
        if (isBookmarked)
            this.isBookmarked = 1;
    }

    public void updateRecipe(Recipe recipe, boolean isUpdateListSection) {
        setId(recipe.getId());
        setIdBook(recipe.getIdBook());
        setIdChapter(recipe.getIdChapter());
        setIdUser(recipe.getIdUser());
        setImages(recipe.getImages());
        setIntroduction(recipe.getIntroduction());
        setIsUsed(recipe.isUsed());
        setIsLiked(recipe.isLiked());
        setIsBookmarked(recipe.isBookmarked());
        setKind(recipe.getKind());
        setAuthorName(recipe.getAuthorName());
        setBookName(recipe.getBookName());
        setChapterName(recipe.getChapterName());
        setCookingTime(recipe.getCookingTime());
        setLevel(recipe.getLevel());
        setLikes(recipe.getLikes());
        setUsed(recipe.getUsed());
        setName(recipe.getName());
        setTipNote(recipe.getTipNote());
        setAuthorComments(recipe.getAuthorComments());
        setType(recipe.getType());
        if (isUpdateListSection)
            updateListSection(recipe.getListSection());
        setCreateTime(recipe.getCreateTime());
    }

    private void updateListSection(ArrayList<Section> listSection) {
        this.listSection.clear();
        for (int i = 0; i < listSection.size(); i++) {
            Section section = new Section();
            Section temp = listSection.get(i);
            section.setId(temp.getId());
            section.setNumberSection(temp.getNumberSection());
            section.setName(temp.getName());
            section.updateListIngredients(temp.getListIngredient());
            section.updateListSteps(temp.getListStep());
            this.listSection.add(section);
        }
    }

    public String getTypeName() {
        return getTypePrefix() + authorName;
    }

    public String getSubTypeContent() {
        return "From \"" + fromRecipeName + "\"";
    }

    public String getAuthorImage() {
        return authorImage;
    }

    public void setAuthorImage(String authorImage) {
        this.authorImage = authorImage;
    }
}
