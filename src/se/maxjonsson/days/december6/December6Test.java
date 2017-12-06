package se.maxjonsson.days.december6;

import java.util.Arrays;

import org.junit.Test;

import sun.jvm.hotspot.utilities.Assert;

public class December6Test {

    /**
     * Tests derived from task.getDescription();
     */
    @Test
    public void taskATest() {
        final December6_TaskA task = new December6_TaskA(Arrays.asList(0, 2, 7, 0));

        task.run();

        Assert.that(task.getResult() == 5, "Expected 5 jumps for block, got " + task.getResult());
    }

    /**
     * Tests derived from task.getDescription();
     */
    @Test
    public void taskBTest() {
        final December6_TaskB task = new December6_TaskB(Arrays.asList(0, 2, 7, 0));

        task.run();

        Assert.that(task.getResult() == 4, "Expected 4 jumps for cycle, got " + task.getResult());
    }
}