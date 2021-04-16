package hu.alkfejl.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;


public class FruitModel {
    private final ObjectProperty<FruitType> type = new SimpleObjectProperty<>();

    public FruitModel() { }

    public FruitModel(FruitType type) {
        this.type.set(type);
    }

    public Color getColor() {
        return type.get().getColor();
    }

    public ObjectProperty<Color> colorProperty() {
        return type.get().colorProperty();
    }

    public void setColor(Color color) {
        type.get().setColor(color);
    }

    public int getValue() {
        return type.get().getValue();
    }

    public FruitType getType() {
        return type.get();
    }

    public ObjectProperty<FruitType> typeProperty() {
        return type;
    }

    public void setType(FruitType type) {
        this.type.set(type);
    }
}
