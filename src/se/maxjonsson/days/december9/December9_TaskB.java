package se.maxjonsson.days.december9;

import se.maxjonsson.Task;
import se.maxjonsson.utils.FileReader;

public class December9_TaskB implements Task {

    private String input;
    private int result = 0;

    public December9_TaskB() {
        input = FileReader.readSingleLine("input/december9/input.txt");
    }

    public December9_TaskB(final String input) {
        this.input = input;
    }

    @Override
    public void run() {
        int garbageCollected = 0;
        boolean collectingGarbage = false;
        boolean skipNext = false;

        char[] chars = input.toCharArray();

        for (char character : chars) {
            if (skipNext) {
                skipNext = false;
                continue;
            }

            if (character == '!') {
                skipNext = true;
                continue;
            }

            if (collectingGarbage) {
                if (character == '>') {
                    collectingGarbage = false;
                }
                else {
                    garbageCollected++;
                }
                continue;
            }

            if (character == '<') {
                collectingGarbage = true;
            }
        }

        result = garbageCollected;
        System.out.println(String.format("Size of removed garbage was %s for the current char stream.", result));
    }

    public int getResult() {
        return result;
    }

    @Override
    public String getTaskName() {
        return "Day 9: Stream Processing - Part Two";
    }

    @Override
    public String getTaskDescription() {
        return "";
    }
}

