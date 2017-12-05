package se.maxjonsson.days.december4;

import java.util.Arrays;

import org.junit.Test;

import sun.jvm.hotspot.utilities.Assert;

public class December4Test {

    /**
     * Tests derived from task.getDescription();
     */
    @Test
    public void taskATest() {
        final December4_TaskA task = new December4_TaskA(Arrays.asList("aa bb cc dd ee", "aa bb cc dd aa", "aa bb cc dd aaa"));

        task.run();

        Assert.that(task.getResult() == 2, "Expected 2 number of valid pass phrases, found " + task.getResult());
    }

    /**
     * Tests derived from task.getDescription();
     */
    @Test
    public void taskBTest() {
        final December4_TaskB task = new December4_TaskB(Arrays.asList(
                "abcde fghij",
                "abcde xyz ecdab",
                "a ab abc abd abf abj",
                "iiii oiii ooii oooi oooo",
                "oiii ioii iioi iiio"));

        task.run();

        Assert.that(task.getResult() == 3, "Expected 3 number of valid pass phrases, found " + task.getResult());
    }

}