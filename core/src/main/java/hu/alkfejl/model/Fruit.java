package hu.alkfejl.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;


public class Fruit {
    private final ObjectProperty<FruitType> type = new SimpleObjectProperty<>();
    private final ObjectProperty<Position> position = new SimpleObjectProperty<>();

    public Fruit() { }

    public Fruit(FruitType type) {
        this.type.set(type);
    }

    public Fruit(FruitType type, Position position) {
        this.type.set(type);
        this.setPosition(position);
    }

    public static FruitType randomFruitType() {
        int randInt = (int) (Math.random() * 100);
        int sum = 0;
        for (var fruitType: FruitType.values()) {
            sum += fruitType.getChance();
            if (randInt < sum) {
                return fruitType;
            }
        }
        // should never get there
        return null;
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

    public Position getPosition() {
        return position.get();
    }

    public ObjectProperty<Position> positionProperty() {
        return position;
    }

    public void setPosition(Position position) {
        this.position.set(position);
    }
}
