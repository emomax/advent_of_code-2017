package se.maxjonsson.days.december3;

import org.junit.Test;

import sun.jvm.hotspot.utilities.Assert;

public class December3Test {

    /**
     * Tests derived from task.getDescription();
     */
    @Test
    public void taskATest() {
        final December3_TaskA task = new December3_TaskA(1);
        final December3_TaskA task1 = new December3_TaskA(12);
        final December3_TaskA task2 = new December3_TaskA(23);
        final December3_TaskA task3 = new December3_TaskA(1024);

        task.run();
        task1.run();
        task2.run();
        task3.run();

        Assert.that(task.getResult() == 0, "Expected 0 - got " + task.getResult());
        Assert.that(task1.getResult() == 3, "Expected 3 - got " + task1.getResult());
        Assert.that(task2.getResult() == 2, "Expected 2 - got " + task2.getResult());
        Assert.that(task3.getResult() == 31, "Expected 31 - got " + task3.getResult());
    }

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