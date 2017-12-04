package se.maxjonsson.days.december1;

import se.maxjonsson.Task;
import se.maxjonsson.utils.FileReader;

public class December1_TaskA implements Task {

    private int result = 0;
    final String input;

    public December1_TaskA() {
        this.input = FileReader.readSingleLine("input/december1/input.txt");
    }

    public December1_TaskA(final String input) {
        this.input = input;
    }

    @Override
    public void run() {
        int sum = 0;

        for (int i = 0; i < input.length() - 1; i++ ) {
            char currentIndex = input.charAt(i);

            if (currentIndex == input.charAt(i + 1)) {
                final int value = Character.getNumericValue(currentIndex);
                sum += value;
            }
        }

        // Tail case
        if (input.charAt(0) == input.charAt(input.length() - 1)) {
            final int value = Character.getNumericValue(input.charAt(0));
            sum += value;
        }

        result = sum;
        System.out.println("Secret number: " + result);
    }

    public int getResult() {
        return result;
    }

    @Override
    public String getTaskName() {
        return "Day 1: Inverse Captcha";
    }

    @Override
    public String getTaskDescription() {
        return "The night before Christmas, one of Santa's Elves calls you in a panic. \"The printer's broken! We can't print the Naughty or Nice List!\" By the time you make it to sub-basement 17, there are only a few minutes until midnight. \"We have a big problem,\" she says; \"there must be almost fifty bugs in this system, but nothing else can print The List. Stand in this square, quick! There's no time to explain; if you can convince them to pay you in stars, you'll be able to--\" She pulls a lever and the world goes blurry.\n" +
               "\n" +
               "When your eyes can focus again, everything seems a lot more pixelated than before. She must have sent you inside the computer! You check the system clock: 25 milliseconds until midnight. With that much time, you should be able to collect all fifty stars by December 25th.\n" +
               "\n" +
               "Collect stars by solving puzzles. Two puzzles will be made available on each day millisecond in the advent calendar; the second puzzle is unlocked when you complete the first. Each puzzle grants one star. Good luck!\n" +
               "\n" +
               "You're standing in a room with \"digitization quarantine\" written in LEDs along one wall. The only door is locked, but it includes a small interface. \"Restricted Area - Strictly No Digitized Users Allowed.\"\n" +
               "\n" +
               "It goes on to explain that you may only leave by solving a captcha to prove you're not a human. Apparently, you only get one millisecond to solve the captcha: too fast for a normal human, but it feels like hours to you.\n" +
               "\n" +
               "The captcha requires you to review a sequence of digits (your puzzle input) and find the sum of all digits that match the next digit in the list. The list is circular, so the digit after the last digit is the first digit in the list.\n" +
               "\n" +
               "For example:\n" +
               "\n" +
               "\t - 1122 produces a sum of 3 (1 + 2) because the first digit (1) matches the second digit and the third digit (2) matches the fourth digit.\n" +
               "\t - 1111 produces 4 because each digit (all 1) matches the next.\n" +
               "\t - 1234 produces 0 because no digit matches the next.\n" +
               "\t - 91212129 produces 9 because the only digit that matches the next one is the last digit, 9.";
    }
}

