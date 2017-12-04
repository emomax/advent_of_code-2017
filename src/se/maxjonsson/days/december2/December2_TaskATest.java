package se.maxjonsson.days.december2;

import java.util.Arrays;

import org.junit.Test;

import sun.jvm.hotspot.utilities.Assert;

public class December2_TaskATest {

    @Test
    public void taskATest() {
        final December2_TaskA task = new December2_TaskA(Arrays.asList("5 1 9 5", "7 5 3", "2 4 6 8"));

        task.run();

        Assert.that(task.getResult() == 18, "Expected 18 - got " + task.getResult());
    }
}