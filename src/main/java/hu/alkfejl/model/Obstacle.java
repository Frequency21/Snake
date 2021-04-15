package hu.alkfejl.model;

import javafx.scene.paint.Color;

public class Obstacle {
    private Color color;
    private Position position;

    public Obstacle() { }

    public Obstacle(Color color, Position position) {
        this.color = color;
        this.position = position;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
