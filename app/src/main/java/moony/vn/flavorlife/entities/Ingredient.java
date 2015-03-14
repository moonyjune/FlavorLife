package moony.vn.flavorlife.entities;

/**
 * Created by moony on 3/14/15.
 */
public class Ingredient {
    private String name;
    private String unit;
    private int value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
