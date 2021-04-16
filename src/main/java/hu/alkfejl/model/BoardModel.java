package hu.alkfejl.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;


// TODO: 2021. 04. 15. create wall
public class BoardModel {
    /** square board.. */
    private IntegerProperty size = new SimpleIntegerProperty();
    private BooleanProperty boundary = new SimpleBooleanProperty();
    /** obstacles' and snake BodyParts' size in pixel */
    private final int blockSize = 25;

    public BoardModel() { }

    public BoardModel(int size, boolean boundary) {
        this.size.set(size);
        this.boundary.set(boundary);
    }

    public int getSize() {
        return size.get();
    }

    public IntegerProperty sizeProperty() {
        return size;
    }

    public void setSize(int size) {
        this.size.set(size);
    }

    public boolean isBoundary() {
        return boundary.get();
    }

    public BooleanProperty boundaryProperty() {
        return boundary;
    }

    public void setBoundary(boolean boundary) {
        this.boundary.set(boundary);
    }

    public int getSizePx() {
        System.out.println(size.get() + " " + blockSize);
        return size.get() * blockSize;
    }

    public int getBlockSize() {
        return blockSize;
    }
}
