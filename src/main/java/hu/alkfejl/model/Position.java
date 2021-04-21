package hu.alkfejl.model;

import java.util.Objects;

/**
 * Stores object position on the board
 * [0..Board.size)
 */
public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(int x) {
        y = this.x = x;
    }

    public Position(Position position) {
        x = position.x;
        y = position.y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Position incX() {
        ++x;
        return this;
    }

    public Position incY() {
        ++y;
        return this;
    }

    public Position decX() {
        --x;
        return this;
    }

    public Position decY() {
        --y;
        return this;
    }

    public Position incX(int with) {
        x += with;
        return this;
    }

    public Position incY(int with) {
        y += with;
        return this;
    }

    public Position decX(int with) {
        x -= with;
        return this;
    }

    public Position decY(int with) {
        y -= with;
        return this;
    }

    public Position copy(Position other) {
        if (other != this) {
            x = other.getX();
            y = other.getY();
        }
        return this;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
