package se.maxjonsson.days.december1;

import org.junit.Test;

import sun.jvm.hotspot.utilities.Assert;

public class December1_TaskATest {

    @Test
    public void taskATest() {
        final December1_TaskA task =  new December1_TaskA("1122");
        final December1_TaskA task1 = new December1_TaskA("1111");
        final December1_TaskA task2 = new December1_TaskA("1234");
        final December1_TaskA task3 = new December1_TaskA("91212129");

        task.run();
        task1.run();
        task2.run();
        task3.run();

        Assert.that(task.getResult() == 3, "Expected 3, got " + task.getResult());
        Assert.that(task1.getResult() == 4, "Expected 4, got " + task.getResult());
        Assert.that(task2.getResult() == 0, "Expected 0, got " + task.getResult());
        Assert.that(task3.getResult() == 9, "Expected 9, got " + task.getResult());
    }
}