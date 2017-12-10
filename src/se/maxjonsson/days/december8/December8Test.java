package se.maxjonsson.days.december8;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import sun.jvm.hotspot.utilities.Assert;

public class December8Test {

    @Test
    public void taskATest() {
        final List<String> input = Arrays.asList("b inc 5 if a > 1",
                                                 "a inc 1 if b < 5",
                                                 "c dec -10 if a >= 1",
                                                 "c inc -20 if c == 10");

        final December8_TaskA task = new December8_TaskA(input);
        task.run();
        Assert.that(task.getResult() == 1, String.format("Largest value in registers should be 1, was %s", task.getResult()));
    }

    @Test
    public void taskBTest() {
        final List<String> input = Arrays.asList("b inc 5 if a > 1",
                                                 "a inc 1 if b < 5",
                                                 "c dec -10 if a >= 1",
                                                 "c inc -20 if c == 10");

        final December8_TaskB task = new December8_TaskB(input);
        task.run();
        Assert.that(task.getResult() == 10, String.format("Largest value held in registers should be 10, was %s", task.getResult()));
    }

}
