package se.maxjonsson.days.december5;

import java.util.Arrays;
import org.junit.Test;
import sun.jvm.hotspot.utilities.Assert;

public class December5Test {

    /**
     * Tests derived from task.getDescription();
     */
    @Test
    public void taskATest() {
        final December5_TaskA task = new December5_TaskA(Arrays.asList(0, 3, 0, 1, -3));

        task.run();

        Assert.that(task.getResult() == 5, "Expected 5 jumps, got " + task.getResult());
    }

    /**
     * Tests derived from task.getDescription();
     */
    @Test
    public void taskBTest() {
        final December5_TaskB task = new December5_TaskB(Arrays.asList(0, 3, 0, 1, -3));

        task.run();

        Assert.that(task.getResult() == 10, "Expected 10 jumps, got " + task.getResult());
    }
}