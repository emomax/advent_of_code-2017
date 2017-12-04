package se.maxjonsson.days.december2;

import java.util.Arrays;
import java.util.List;

import se.maxjonsson.Task;
import se.maxjonsson.utils.FileReader;

public class December2_TaskA implements Task {

    private int result = 0;
    final List<String> input;

    public December2_TaskA() {
        this.input = FileReader.readLines("input/december2/input.txt");
    }

    public December2_TaskA(final List<String> input) {
        this.input = input;
    }

    @Override
    public void run() {
        int sum = 0;

        for (String line : input) {
            final List<String> lineTokens = Arrays.asList(line.split("\\s+"));

            final int max = lineTokens.stream().mapToInt(Integer::parseInt).max().orElseThrow(() -> new RuntimeException("Failed to parse list!"));
            final int min = lineTokens.stream().mapToInt(Integer::parseInt).min().orElseThrow(() -> new RuntimeException("Failed to parse list!"));

            sum += max - min;
        }

        result = sum;
        System.out.println("Checksum was: " + result);
    }

    public int getResult() {
        return result;
    }

    @Override
    public String getTaskName() {
        return "Day 2: Corruption Checksum";
    }

    @Override
    public String getTaskDescription() {
        return "As you walk through the door, a glowing humanoid shape yells in your direction. \"You there! Your state appears to be idle. Come help us repair the corruption in this spreadsheet - if we take another millisecond, we'll have to display an hourglass cursor!\"\n" +
               "\n" +
               "The spreadsheet consists of rows of apparently-random numbers. To make sure the recovery process is on the right track, they need you to calculate the spreadsheet's checksum. For each row, determine the difference between the largest value and the smallest value; the checksum is the sum of all of these differences.\n" +
               "\n" +
               "For example, given the following spreadsheet:\n" +
               "\n" +
               "5 1 9 5\n" +
               "7 5 3\n" +
               "2 4 6 8\n" +
               "\t - The first row's largest and smallest values are 9 and 1, and their difference is 8.\n" +
               "\t - The second row's largest and smallest values are 7 and 3, and their difference is 4.\n" +
               "\t - The third row's difference is 6.\n" +
               "TIn this example, the spreadsheet's checksum would be 8 + 4 + 6 = 18.\n";
    }
}

