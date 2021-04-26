package hu.alkfejl.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

public enum FruitType {
    COMMON(Color.GRAY, 1, 25),
    REVERSE(Color.YELLOW, 1, 30),
    SLOW(Color.BLUE, 2, 20),
    BERSERK(Color.RED, 2, 5),
    EXTRA(Color.PURPLE, 5, 20);

    private final ObjectProperty<Color> color = new SimpleObjectProperty<>();
    private final int value;
    /* sum of the types chances must be 100 */
    private final int chance;

    FruitType(Color color, int value, int chance) {
        this.color.set(color);
        this.value = value;
        this.chance = chance;
    }

    public Color getColor() {
        return color.get();
    }

    public ObjectProperty<Color> colorProperty() {
        return color;
    }

    public void setColor(Color color) {
        this.color.set(color);
    }

    public int getValue() {
        return value;
    }

    public int getChance() {
        return chance;
    }
}
