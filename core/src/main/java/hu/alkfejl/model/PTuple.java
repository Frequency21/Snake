package hu.alkfejl.model;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Property Tuple
 */
public class PTuple<U, V> {
    private final ObjectProperty<U> first = new SimpleObjectProperty<>();
    private final ObjectProperty<V> second = new SimpleObjectProperty<>();

    public PTuple() { }

    public PTuple(U first, V second) {
        this.first.set(first);
        this.second.set(second);
    }

    public U getFirst() {
        return first.get();
    }

    public ObjectProperty<U> firstProperty() {
        return first;
    }

    public void setFirst(U first) {
        this.first.set(first);
    }

    public V getSecond() {
        return second.get();
    }

    public ObjectProperty<V> secondProperty() {
        return second;
    }

    public void setSecond(V second) {
        this.second.set(second);
    }
}
