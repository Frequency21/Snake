package hu.alkfejl.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

public enum FruitType {
    COMMON(Color.GRAY, 1),
    SLOW(Color.BLUE, 2),
    BERSERK(Color.RED, 2),
    EXTRA(Color.PURPLE, 5);

    private final ObjectProperty<Color> color = new SimpleObjectProperty<>();
    private final int value;

    FruitType(Color color, int value) {
        this.color.set(color);
        this.value = value;
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
}
