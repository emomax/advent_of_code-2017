package se.maxjonsson.days.december9;

import org.junit.Test;

import sun.jvm.hotspot.utilities.Assert;

public class December9Test {

    @Test
    public void taskA_testGroups() {
        final December9_TaskA task1 = new December9_TaskA("{}");
        final December9_TaskA task2 = new December9_TaskA("{{{}}}");
        final December9_TaskA task3 = new December9_TaskA("{{},{}}");
        final December9_TaskA task4 = new December9_TaskA("{{{},{},{{}}}}");
        final December9_TaskA task5 = new December9_TaskA("{<a>,<a>,<a>,<a>}");
        final December9_TaskA task6 = new December9_TaskA("{{<ab>},{<ab>},{<ab>},{<ab>}}");
        final December9_TaskA task7 = new December9_TaskA("{{<!!>},{<!!>},{<!!>},{<!!>}}");
        final December9_TaskA task8 = new December9_TaskA("{{<a!>},{<a!>},{<a!>},{<ab>}}");

        task1.run();
        task2.run();
        task3.run();
        task4.run();
        task5.run();
        task6.run();
        task7.run();
        task8.run();

        Assert.that(task1.getResult() == 1, "Expected score of 1, got '" + +task1.getResult() + "'");
        Assert.that(task2.getResult() == 6, "Expected score of 6, got '" + +task2.getResult() + "'");
        Assert.that(task3.getResult() == 5, "Expected score of 5, got '" + +task3.getResult() + "'");
        Assert.that(task4.getResult() == 16, "Expected score of 16, got '" + +task4.getResult() + "'");
        Assert.that(task5.getResult() == 1, "Expected score of 1, got '" + +task5.getResult() + "'");
        Assert.that(task6.getResult() == 9, "Expected score of 9, got '" + +task6.getResult() + "'");
        Assert.that(task7.getResult() == 9, "Expected score of 9, got '" + +task7.getResult() + "'");
        Assert.that(task8.getResult() == 3, "Expected score of 3, got '" + +task8.getResult() + "'");
    }

    @Test
    public void taskB_testGC() {
        final December9_TaskB task1 = new December9_TaskB("<>");
        final December9_TaskB task2 = new December9_TaskB("<random characters>");
        final December9_TaskB task3 = new December9_TaskB("<<<<>");
        final December9_TaskB task4 = new December9_TaskB("<{!>}>");
        final December9_TaskB task5 = new December9_TaskB("<!!>");
        final December9_TaskB task6 = new December9_TaskB("<!!!>>");
        final December9_TaskB task7 = new December9_TaskB("<{o\"i!a,<{i<a>");

        task1.run();
        task2.run();
        task3.run();
        task4.run();
        task5.run();
        task6.run();
        task7.run();

        Assert.that(task1.getResult() == 0,  "Expected garbage collection size of 0, got '" + +task1.getResult() + "'");
        Assert.that(task2.getResult() == 17, "Expected garbage collection size of 17, got '" + +task1.getResult() + "'");
        Assert.that(task3.getResult() == 3,  "Expected garbage collection size of 3, got '" + +task1.getResult() + "'");
        Assert.that(task4.getResult() == 2,  "Expected garbage collection size of 2 got '" + +task1.getResult() + "'");
        Assert.that(task5.getResult() == 0,  "Expected garbage collection size of 0, got '" + +task1.getResult() + "'");
        Assert.that(task6.getResult() == 0,  "Expected garbage collection size of 0, got '" + +task1.getResult() + "'");
        Assert.that(task7.getResult() == 10, "Expected garbage collection size of 10, got '" + +task1.getResult() + "'");

    }
}
