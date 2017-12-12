package se.maxjonsson;

import java.util.ArrayList;
import java.util.List;

import se.maxjonsson.days.december1.December1_TaskA;
import se.maxjonsson.days.december1.December1_TaskB;
import se.maxjonsson.days.december10.December10_TaskA;
import se.maxjonsson.days.december10.December10_TaskB;
import se.maxjonsson.days.december11.December11_TaskA;
import se.maxjonsson.days.december12.December12_TaskA;
import se.maxjonsson.days.december12.December12_TaskB;
import se.maxjonsson.days.december2.December2_TaskA;
import se.maxjonsson.days.december2.December2_TaskB;
import se.maxjonsson.days.december3.December3_TaskA;
import se.maxjonsson.days.december3.December3_TaskB;
import se.maxjonsson.days.december4.December4_TaskA;
import se.maxjonsson.days.december4.December4_TaskB;
import se.maxjonsson.days.december5.December5_TaskA;
import se.maxjonsson.days.december5.December5_TaskB;
import se.maxjonsson.days.december6.December6_TaskA;
import se.maxjonsson.days.december6.December6_TaskB;
import se.maxjonsson.days.december7.December7_TaskA;
import se.maxjonsson.days.december7.December7_TaskB;
import se.maxjonsson.days.december8.December8_TaskA;
import se.maxjonsson.days.december8.December8_TaskB;
import se.maxjonsson.days.december9.December9_TaskA;
import se.maxjonsson.days.december9.December9_TaskB;

class TaskRunner {

    final List<Task> tasks = new ArrayList<Task>();

    TaskRunner() {
        populateTasks();
    }

    void populateTasks() {
        tasks.add(new December1_TaskA());
        tasks.add(new December1_TaskB());
        tasks.add(new December2_TaskA());
        tasks.add(new December2_TaskB());
        tasks.add(new December3_TaskA());
        tasks.add(new December3_TaskB());
        tasks.add(new December4_TaskA());
        tasks.add(new December4_TaskB());
        tasks.add(new December5_TaskA());
        tasks.add(new December5_TaskB());
        tasks.add(new December6_TaskA());
        tasks.add(new December6_TaskB());
        tasks.add(new December7_TaskA());
        tasks.add(new December7_TaskB());
        tasks.add(new December8_TaskA());
        tasks.add(new December8_TaskB());
        tasks.add(new December9_TaskA());
        tasks.add(new December9_TaskB());
        tasks.add(new December10_TaskA());
        tasks.add(new December10_TaskB());
        tasks.add(new December11_TaskA());
        //tasks.add(new December11_TaskB());
        tasks.add(new December12_TaskA());
        tasks.add(new December12_TaskB());
    }

    void run() {
        tasks.forEach(this::runTask);
    }

    void runTask(final Task task) {
        final long startTime = System.currentTimeMillis();

        print(String.format("Running task '%s'...\n", task.getTaskName()));
        task.run();
        print(String.format("Task completed in %s seconds.\n\n", (System.currentTimeMillis() - startTime) / 1000f));
    }

    private void print(final String output) {
        System.out.println(output);
    }
}
