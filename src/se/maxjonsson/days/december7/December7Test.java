package se.maxjonsson.days.december7;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import sun.jvm.hotspot.utilities.Assert;

public class December7Test {

    @Test
    public void taskATest() {

        final List<String> testInput = Arrays.asList(
                "pbga (66)",
                "xhth (57)",
                "ebii (61)",
                "havc (66)",
                "ktlj (57)",
                "fwft (72) -> ktlj, cntj, xhth",
                "qoyq (66)",
                "padx (45) -> pbga, havc, qoyq",
                "tknk (41) -> ugml, padx, fwft",
                "jptl (61)",
                "ugml (68) -> gyxo, ebii, jptl",
                "gyxo (61)",
                "cntj (57)"
        );


        final December7_TaskA task = new December7_TaskA(testInput);
        task.run();
        Assert.that(task.getResult().equals("tknk"), String.format("Expected base 'tknk', but found %s", task.getResult()));
    }

    @Test
    public void taskBTest() {

        final List<String> testInput = Arrays.asList(
                "pbga (66)",
                "xhth (57)",
                "ebii (61)",
                "havc (66)",
                "ktlj (57)",
                "fwft (72) -> ktlj, cntj, xhth",
                "qoyq (66)",
                "padx (45) -> pbga, havc, qoyq",
                "tknk (41) -> ugml, padx, fwft",
                "jptl (61)",
                "ugml (68) -> gyxo, ebii, jptl",
                "gyxo (61)",
                "cntj (57)"
        );


        final December7_TaskB task = new December7_TaskB(testInput);
        task.run();
        Assert.that(task.getResult() == 60, String.format("Expected adjusted tower weight 60, but found %s", task.getResult()));
    }
}
