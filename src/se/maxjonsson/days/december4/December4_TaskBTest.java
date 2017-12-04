package se.maxjonsson.days.december4;

import java.util.Arrays;

import org.junit.Test;

import sun.jvm.hotspot.utilities.Assert;

public class December4_TaskBTest {

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