package hu.alkfejl.model;

/**
 * Simple tuple class, mostly needed in SceneManager
 * @param <U> first
 * @param <V> second
 */
public class Tuple <U, V> {
    private final U first;
    private final V second;

    public Tuple(U first, V second) {
        this.first = first;
        this.second = second;
    }

    public U getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }
}
