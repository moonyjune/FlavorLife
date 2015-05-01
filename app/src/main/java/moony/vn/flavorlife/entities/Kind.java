package moony.vn.flavorlife.entities;

/**
 * Created by moony on 4/30/15.
 */
public class Kind {
    private int kind;
    private String name;
    private boolean isChose;

    public Kind() {
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChose() {
        return isChose;
    }

    public void setChose(boolean isChose) {
        this.isChose = isChose;
    }
}
