package se.maxjonsson.days.december10;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;

import sun.jvm.hotspot.utilities.Assert;

public class December10Test {

    @Test
    public void taskATest() {
        final List<Integer> input = Arrays.asList(3, 4, 1, 5);
        final int[] hashSequence = {0, 1, 2, 3, 4};
        final December10_TaskA task = new December10_TaskA(input, hashSequence);

        task.run();

        Assert.that(task.getResult() == 12, String.format("Expected product of 2 first elements to be 12, was %s.", task.getResult()));
    }

    @Test
    public void taskBTest() {
        final String input1 = "";
        final String input2 = "AoC 2017";
        final String input3 = "1,2,3";
        final String input4 = "1,2,4";

        final int[] startSequence1 = IntStream.range(0, 256).toArray();
        final int[] startSequence2 = IntStream.range(0, 256).toArray();
        final int[] startSequence3 = IntStream.range(0, 256).toArray();
        final int[] startSequence4 = IntStream.range(0, 256).toArray();

        final December10_TaskB task1 = new December10_TaskB(input1, startSequence1);
        final December10_TaskB task2 = new December10_TaskB(input2, startSequence2);
        final December10_TaskB task3 = new December10_TaskB(input3, startSequence3);
        final December10_TaskB task4 = new December10_TaskB(input4, startSequence4);

        task1.run();
        task2.run();
        task3.run();
        task4.run();

        Assert.that(task1.getResult().equals("a2582a3a0e66e6e86e3812dcb672a272"), String.format("Hash of input '%s' didn't match expected.\n%s\n%s", input1, task1.getResult(), "a2582a3a0e66e6e86e3812dcb672a272"));
        Assert.that(task2.getResult().equals("33efeb34ea91902bb2f59c9920caa6cd"), String.format("Hash of input '%s' didn't match expected.", input1));
        Assert.that(task3.getResult().equals("3efbe78a8d82f29979031a4aa0b16a9d"), String.format("Hash of input '%s' didn't match expected.", input1));
        Assert.that(task4.getResult().equals("63960835bcdc130f0b66d7ff4f6a5a8e"), String.format("Hash of input '%s' didn't match expected.", input1));
    }
}
