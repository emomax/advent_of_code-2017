package se.maxjonsson.days.december2;

import java.util.Arrays;
import java.util.List;

import se.maxjonsson.Task;
import se.maxjonsson.utils.FileReader;

public class December2_TaskB implements Task {

    private int result = 0;
    final List<String> input;

    public December2_TaskB() {
        this.input = FileReader.readLines("input/december2/input.txt");
    }

    public December2_TaskB(final List<String> input) {
        this.input = input;
    }

    private class IntPair {
        final int a;
        final int b;

        public IntPair(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public int getA() {
            return a;
        }

        public int getB() {
            return b;
        }
    }

    @Override
    public void run() {
        int sum = 0;

        for (String line : input) {
            final List<String> lineTokens = Arrays.asList(line.split("\\s+"));

            final IntPair pairForLine = findOnlyDivisibleIntPair(lineTokens);

            sum += pairForLine.getA() / pairForLine.getB();
        }

        result = sum;
        System.out.println("Checksum was: " + result);
    }

    private IntPair findOnlyDivisibleIntPair(final List<String> tokens) {

        for (int outer = 0; outer < tokens.size(); outer++) {
            final int outerInt = Integer.parseInt(tokens.get(outer));

            for (int inner = 0; inner < tokens.size(); inner++) {
                final int innerInt = Integer.parseInt(tokens.get(inner));

                if (outerInt == innerInt) {
                    continue;
                }

                if (outerInt % innerInt == 0) {
                    return new IntPair(outerInt, innerInt);
                }

                if (innerInt % outerInt == 0) {
                    return new IntPair(innerInt, outerInt);
                }
            }
        }

        throw new AssertionError("Failed to find any divisible combinations.");
    }

    public int getResult() {
        return result;
    }

    @Override
    public String getTaskName() {
        return "Day 2: Corruption Checksum - Part Two";
    }

    @Override
    public String getTaskDescription() {
        return "\"Great work; looks like we're on the right track after all. Here's a star for your effort.\" However, the program seems a little worried. Can programs be worried?\n" +
               "\n" +
               "\"Based on what we're seeing, it looks like all the User wanted is some information about the evenly divisible values in the spreadsheet. Unfortunately, none of us are equipped for that kind of calculation - most of us specialize in bitwise operations.\"\n" +
               "\n" +
               "It sounds like the goal is to find the only two numbers in each row where one evenly divides the other - that is, where the result of the division operation is a whole number. They would like you to find those numbers on each line, divide them, and add up each line's result.\n" +
               "\n" +
               "For example, given the following spreadsheet:\n" +
               "\n" +
               "5 9 2 8\n" +
               "9 4 7 3\n" +
               "3 8 6 5\n" +
               "\t - In the first row, the only two numbers that evenly divide are 8 and 2; the result of this division is 4.\n" +
               "\t - In the second row, the two numbers are 9 and 3; the result is 3.\n" +
               "\t - In the third row, the result is 2.\n" +
               "\t - In this example, the sum of the results would be 4 + 3 + 2 = 9.\n" +
               "\n" +
               "What is the sum of each row's result in your puzzle input?";
    }
}

