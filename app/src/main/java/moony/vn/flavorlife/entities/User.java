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
    @SerializedName("user_name")
    private String userName;
    @SerializedName("image")
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
    @SerializedName("socialnetwork_image")
    private String socialNetworkImage;
    private boolean isUpdatedProfile;
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

    public String getImageDisplay() {
        if (image != null && !image.isEmpty())
            return image;
        else
            return socialNetworkImage;
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

    public void updateUser(User user) {
        setId(user.getId());
        setUserName(user.getUserName());
        setFollowed(user.isFollowed());
        setNumFollowers(user.getNumFollowers());
        setNumBooks(user.getNumBooks());
        setNumRecipes(user.getNumRecipes());
        setNumFollows(user.getNumFollows());
        setEmail(user.getEmail());
        setImage(user.getImage());
        setInspiration(user.getInspiration());
        setSocialNetworkImage(user.getSocialNetworkImage());
    }

    public String getSocialNetworkImage() {
        return socialNetworkImage;
    }

    public void setSocialNetworkImage(String socialNetworkImage) {
        this.socialNetworkImage = socialNetworkImage;
    }

    public boolean isUpdatedProfile() {
        return isUpdatedProfile;
    }

    public void setUpdatedProfile(boolean isUpdatedProfile) {
        this.isUpdatedProfile = isUpdatedProfile;
    }

    public void clear() {
        id = -1;
        email = null;
        userName = null;
        image = null;
        inspiration = null;
        numBooks = -1;
        numFollowers = -1;
        numFollows = -1;
        numRecipes = -1;
        isFollowed = 0;
        socialNetworkImage = null;
        isUpdatedProfile = false;
        state = 2;
    }
}
