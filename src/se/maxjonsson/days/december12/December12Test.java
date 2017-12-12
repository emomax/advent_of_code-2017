package se.maxjonsson.days.december12;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import sun.jvm.hotspot.utilities.Assert;

public class December12Test {

    @Test
    public void taskATest() {
        List<String> input = Arrays.asList(
                "0 <-> 2",
                "1 <-> 1",
                "2 <-> 0, 3, 4",
                "3 <-> 2, 4",
                "4 <-> 2, 3, 6",
                "5 <-> 6",
                "6 <-> 4, 5");

        final December12_TaskA task = new December12_TaskA(input);
        task.run();
        Assert.that(task.getResult() == 6, String.format("Expected group size of 6, found %s.", task.getResult()));
    }

    @Test
    public void taskBTest() {
        List<String> input = Arrays.asList(
                "0 <-> 2",
                "1 <-> 1",
                "2 <-> 0, 3, 4",
                "3 <-> 2, 4",
                "4 <-> 2, 3, 6",
                "5 <-> 6",
                "6 <-> 4, 5");

        final December12_TaskB task = new December12_TaskB(input);
        task.run();
        Assert.that(task.getResult() == 2, String.format("Expected number of groups to be 2, found %s.", task.getResult()));
    }
}
