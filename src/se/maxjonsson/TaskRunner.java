package se.maxjonsson;

import java.util.ArrayList;
import java.util.List;

import se.maxjonsson.days.december1.December1_TaskA;
import se.maxjonsson.days.december1.December1_TaskB;
import se.maxjonsson.days.december2.December2_TaskA;
import se.maxjonsson.days.december2.December2_TaskB;
import se.maxjonsson.days.december3.December3_TaskA;
import se.maxjonsson.days.december3.December3_TaskB;
import se.maxjonsson.days.december4.December4_TaskA;
import se.maxjonsson.days.december4.December4_TaskB;
import se.maxjonsson.days.december5.December5_TaskA;
import se.maxjonsson.days.december5.December5_TaskB;

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
