package se.maxjonsson.days.december2;

import java.util.Arrays;

import org.junit.Test;

import sun.jvm.hotspot.utilities.Assert;

public class December2Test {

    /**
     * Tests derived from task.getDescription();
     */
    @Test
    public void taskATest() {
        final December2_TaskA task = new December2_TaskA(Arrays.asList("5 1 9 5", "7 5 3", "2 4 6 8"));

        task.run();

        Assert.that(task.getResult() == 18, "Expected 18 - got " + task.getResult());
    }
    /**
     * Tests derived from task.getDescription();
     */
    @Test
    public void taskBTest() {
        final December2_TaskB task = new December2_TaskB(Arrays.asList("5 9 2 8", "9 4 7 3", "3 8 6 5"));

        task.run();

        Assert.that(task.getResult() == 9, "Expected 9 - got " + task.getResult());
    }
}