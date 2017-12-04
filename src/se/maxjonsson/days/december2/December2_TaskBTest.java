package se.maxjonsson.days.december2;

import java.util.Arrays;

import org.junit.Test;

import sun.jvm.hotspot.utilities.Assert;

public class December2_TaskBTest {

    /**
     * Tests derived from task.getDescription();
     */
    @Test
    public void taskATest() {
        final December2_TaskB task = new December2_TaskB(Arrays.asList("5 9 2 8", "9 4 7 3", "3 8 6 5"));

        task.run();

        Assert.that(task.getResult() == 9, "Expected 9 - got " + task.getResult());
    }
}