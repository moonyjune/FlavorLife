package moony.vn.flavorlife.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by moony on 3/16/15.
 */
public class User {
    @SerializedName("id")
    private int id;
    @SerializedName("email")
    private String email;
    @SerializedName("inspiration")
    private String inspiration;
    @SerializedName("num_follows")
    private int numFollows;
    @SerializedName("num_followers")
    private int numFollowers;
    private int state;

    public enum State {
        LOGGED_IN,
        State, LOGGED_OUT
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
}
