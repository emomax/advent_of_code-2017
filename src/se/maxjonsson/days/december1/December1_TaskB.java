package se.maxjonsson.days.december1;

import java.util.logging.Logger;

import se.maxjonsson.Task;
import se.maxjonsson.utils.FileReader;

public class December1_TaskB implements Task {

    final Logger debug = Logger.getAnonymousLogger();
    final String input;

    private int result = 0;

    public December1_TaskB() {
        this.input = FileReader.readSingleLine("input/december1/input.txt");
    }

    public December1_TaskB(final String input) {
        this.input = input;
    }

    @Override
    public void run() {
        int sum = 0;
        final int baseOffset = input.length() / 2;

        for (int currentIndex = 0; currentIndex < input.length(); currentIndex++ ) {
            char currentChar = input.charAt(currentIndex);

            int offset = wrapOffset(baseOffset, currentIndex);
            char charToCompareWith = input.charAt(offset);

            if (currentChar == charToCompareWith) {
                final int value = Character.getNumericValue(currentChar);
                sum += value;
            }
        }

        result = sum;
        debug.info("Secret number: " + result);
    }

    private int wrapOffset(final int baseOffset, final int i) {
        int offset;

        if (i + baseOffset >= input.length()) {
            offset = i + baseOffset - input.length();
        }
        else {
            offset = i + baseOffset;
        }
        return offset;
    }

    public int getResult() {
        return result;
    }

    @Override
    public String getTaskName() {
        return "Day 1: Inverse Captcha - Part Two";
    }

    @Override
    public String getTaskDescription() {
        return "You notice a progress bar that jumps to 50% completion. Apparently, the door isn't yet satisfied, but it did emit a star as encouragement. The instructions change:\n" +
               "\n" +
               "Now, instead of considering the next digit, it wants you to consider the digit halfway around the circular list. That is, if your list contains 10 items, only include a digit in your sum if the digit 10/2 = 5 steps forward matches it. Fortunately, your list has an even number of elements.\n" +
               "\n" +
               "For example:\n" +
               "\n" +
               "\t - 1212 produces 6: the list contains 4 items, and all four digits match the digit 2 items ahead.\n" +
               "\t - 1221 produces 0, because every comparison is between a 1 and a 2.\n" +
               "\t - 123425 produces 4, because both 2s match each other, but no other digit has a match.\n" +
               "\t - 123123 produces 12.\n" +
               "\t - 12131415 produces 4.\n";
    }
}
