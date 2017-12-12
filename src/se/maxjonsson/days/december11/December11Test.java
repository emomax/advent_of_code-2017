package se.maxjonsson.days.december11;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import sun.jvm.hotspot.utilities.Assert;

public class December11Test {
    @Test
    public void taskATest() {
        final List<String> input1 = Arrays.asList("ne", "ne", "ne");
        final List<String> input2 = Arrays.asList("ne", "ne", "sw", "sw");
        final List<String> input3 = Arrays.asList("ne", "ne", "s", "s");
        final List<String> input4 = Arrays.asList("se", "sw", "se", "sw", "sw");

        final December11_TaskA task1 = new December11_TaskA(input1);
        final December11_TaskA task2 = new December11_TaskA(input2);
        final December11_TaskA task3 = new December11_TaskA(input3);
        final December11_TaskA task4 = new December11_TaskA(input4);

        task1.run();
        task2.run();
        task3.run();
        task4.run();

        Assert.that(task1.getResult() == 3, String.format("1 - Shortest path should've been 3, was %s.", task1.getResult()));
        Assert.that(task2.getResult() == 0, String.format("2 - Shortest path should've been 0, was %s.", task2.getResult()));
        Assert.that(task3.getResult() == 2, String.format("3 - Shortest path should've been 2, was %s.", task3.getResult()));
        Assert.that(task4.getResult() == 3, String.format("4 - Shortest path should've been 3, was %s.", task4.getResult()));
    }
}
