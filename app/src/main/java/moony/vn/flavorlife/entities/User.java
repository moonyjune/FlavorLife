package moony.vn.flavorlife.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by moony on 3/16/15.
 */
public class User implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("email")
    private String email;
    @SerializedName("image")
    private String userName;
    @SerializedName("user_name")
    private String image;
    @SerializedName("inspiration")
    private String inspiration;
    @SerializedName("num_follows")
    private int numFollows;
    @SerializedName("num_followers")
    private int numFollowers;
    @SerializedName("num_books")
    private int numBooks;
    @SerializedName("num_recipes")
    private int numRecipes;
    @SerializedName("is_followed")
    private int isFollowed;

    private int state;

    public enum State {
        LOGGED_IN,
        LOGGED_OUT
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setState(State state) {
        switch (state) {
            case LOGGED_IN:
                this.state = 1;
                break;
            case LOGGED_OUT:
                this.state = 2;
                break;
        }
    }

    public State getState() {
        switch (state) {
            case 1:
                return State.LOGGED_IN;
            case 2:
                return State.LOGGED_OUT;
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInspiration() {
        return inspiration;
    }

    public void setInspiration(String inspiration) {
        this.inspiration = inspiration;
    }

    public int getNumFollows() {
        return numFollows;
    }

    public void setNumFollows(int numFollows) {
        this.numFollows = numFollows;
    }

    public int getNumFollowers() {
        return numFollowers;
    }

    public void setNumFollowers(int numFollowers) {
        this.numFollowers = numFollowers;
    }

    public int getNumBooks() {
        return numBooks;
    }

    public void setNumBooks(int numBooks) {
        this.numBooks = numBooks;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNumRecipes() {
        return numRecipes;
    }

    public void setNumRecipes(int numRecipes) {
        this.numRecipes = numRecipes;
    }

    public boolean isFollowed() {
        if (isFollowed == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void setFollowed(boolean isFollowed) {
        if (isFollowed) {
            this.isFollowed = 1;
        } else {
            this.isFollowed = 0;
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
