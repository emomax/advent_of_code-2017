package se.maxjonsson.days.december3;

import org.junit.Test;

import sun.jvm.hotspot.utilities.Assert;

public class December3_TaskBTest {

    /**
     * Tests derived from task.getDescription();
     */
    @Test
    public void taskBTest() {
        final December3_TaskB task = new December3_TaskB(9);
        final December3_TaskB task1 = new December3_TaskB(30);
        final December3_TaskB task2 = new December3_TaskB(320);

        task.run();
        task1.run();
        task2.run();

        Assert.that(task.getResult() == 10, "Expected 10 - got " + task.getResult());
        Assert.that(task1.getResult() == 54, "Expected 54 - got " + task1.getResult());
        Assert.that(task2.getResult() == 330, "Expected 330 - got " + task2.getResult());
    }
}