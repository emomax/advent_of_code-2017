package se.maxjonsson.days.december1;

import org.junit.Test;

import sun.jvm.hotspot.utilities.Assert;

public class December1_TaskBTest {

    /**
     * Tests derived from task.getDescription();
     */
    @Test
    public void taskBTest() {
        final December1_TaskB task = new December1_TaskB("1212");
        final December1_TaskB task1 = new December1_TaskB("1221");
        final December1_TaskB task2 = new December1_TaskB("123425");
        final December1_TaskB task3 = new December1_TaskB("123123");
        final December1_TaskB task4 = new December1_TaskB("12131415");

        task.run();
        task1.run();
        task2.run();
        task3.run();
        task4.run();

        Assert.that(task.getResult() == 6, "Expected 6, got " + task.getResult());
        Assert.that(task1.getResult() == 0, "Expected 0, got " + task.getResult());
        Assert.that(task2.getResult() == 4, "Expected 4, got " + task.getResult());
        Assert.that(task3.getResult() == 12, "Expected 12, got " + task.getResult());
        Assert.that(task4.getResult() == 4, "Expected 4, got " + task.getResult());
    }
}