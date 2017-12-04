package se.maxjonsson.utils;

public class IncrementableInt {

    private int value;

    public IncrementableInt(final int startValue) {
        this.value = startValue;
    }

    public void increment() {
        this.value++;
    }

    public int get() {
        return value;
    }
}
