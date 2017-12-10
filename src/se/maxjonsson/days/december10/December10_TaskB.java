package se.maxjonsson.days.december10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import se.maxjonsson.Task;
import se.maxjonsson.utils.FileReader;

public class December10_TaskB implements Task {

    private List<Integer> input;
    private int[]         hashSequence;

    private String result = "";

    public December10_TaskB() {
        final String inputString = FileReader.readSingleLine("input/december10/input.txt");
        this.input = new ArrayList<>();

        for (char c : inputString.toCharArray()) {
            input.add((int) c);
        }

        hashSequence = IntStream.range(0, 256).toArray();
        input.addAll(Arrays.asList(17, 31, 73, 47, 23));
    }

    public December10_TaskB(final String inputString, final int[] hashSequence) {
        this.hashSequence = hashSequence;
        this.input = new ArrayList<>();

        for (char c : inputString.toCharArray()) {
            input.add((int) c);
        }

        this.input.addAll(Arrays.asList(17, 31, 73, 47, 23));
    }

    @Override
    public void run() {
        int skipSize = 0;
        int currentPosition = 0;

        for (int i = 0; i < 64; i++) {
            for (int subListLength : input) {
                updateHash(subListLength, currentPosition);

                currentPosition += subListLength + skipSize++;
                while (currentPosition > hashSequence.length) {
                    currentPosition -= hashSequence.length;
                }
            }
        }

        int[] sparseHash = hashSequence;
        result = densify(sparseHash);

        System.out.println(String.format("Dense hash of input: '%s'.", result));
    }

    private String densify(int[] sparseHash) {
        final List<String> denseHash = new ArrayList<>();

        for (int hashPart = 0; hashPart < 16; hashPart++) {
            int currentPart = 0;

            for (int i = 0; i < 16; i++) {
                currentPart = currentPart ^ sparseHash[hashPart * 16 + i];
            }

            String hex = Integer.toHexString(currentPart);
            if (hex.length() != 2) {
                hex = "0" + hex;
            }
            denseHash.add(hex);
        }

        return denseHash.stream()
                .collect(Collectors.joining(""));
    }

    private void updateHash(int subListLength, int currentPosition) {
        List<Integer> subList = getSubList(subListLength, currentPosition);
        Collections.reverse(subList);

        for (int i = 0; i < subListLength; i++) {
            int indexWithinBounds = currentPosition + i >= hashSequence.length ? currentPosition + i - hashSequence.length : currentPosition + i;

            hashSequence[indexWithinBounds] = subList.get(i);
        }
    }

    private List<Integer> getSubList(int subListLength, int currentPosition) {
        final List<Integer> subList = new ArrayList<>();

        for (int i = currentPosition; i < currentPosition + subListLength; i++) {
            int indexWithinBounds = i >= hashSequence.length ? i - hashSequence.length : i;

            subList.add(hashSequence[indexWithinBounds]);
        }

        return subList;
    }

    public String getResult() {
        return result;
    }

    @Override
    public String getTaskName() {
        return "Day 10: Knot Hash - Part Two";
    }

    @Override
    public String getTaskDescription() {
        return "The logic you've constructed forms a single round of the Knot Hash algorithm; running the full thing requires many of these rounds. Some input and output processing is also required.\n" +
               "\n" +
               "First, from now on, your input should be taken not as a list of numbers, but as a string of bytes instead. Unless otherwise specified, convert characters to bytes using their ASCII codes. This will allow you to handle arbitrary ASCII strings, and it also ensures that your input lengths are never larger than 255. For example, if you are given 1,2,3, you should convert it to the ASCII codes for each character: 49,44,50,44,51.\n" +
               "\n" +
               "Once you have determined the sequence of lengths to use, add the following lengths to the end of the sequence: 17, 31, 73, 47, 23. For example, if you are given 1,2,3, your final sequence of lengths should be 49,44,50,44,51,17,31,73,47,23 (the ASCII codes from the input string combined with the standard length suffix values).\n" +
               "\n" +
               "Second, instead of merely running one round like you did above, run a total of 64 rounds, using the same length sequence in each round. The current position and skip size should be preserved between rounds. For example, if the previous example was your first round, you would start your second round with the same length sequence (3, 4, 1, 5, 17, 31, 73, 47, 23, now assuming they came from ASCII codes and include the suffix), but start with the previous round's current position (4) and skip size (4).\n" +
               "\n" +
               "Once the rounds are complete, you will be left with the numbers from 0 to 255 in some order, called the sparse hash. Your next task is to reduce these to a list of only 16 numbers called the dense hash. To do this, use numeric bitwise XOR to combine each consecutive block of 16 numbers in the sparse hash (there are 16 such blocks in a list of 256 numbers). So, the first element in the dense hash is the first sixteen elements of the sparse hash XOR'd together, the second element in the dense hash is the second sixteen elements of the sparse hash XOR'd together, etc.\n" +
               "\n" +
               "For example, if the first sixteen elements of your sparse hash are as shown below, and the XOR operator is ^, you would calculate the first output number like this:\n" +
               "\n" +
               "65 ^ 27 ^ 9 ^ 1 ^ 4 ^ 3 ^ 40 ^ 50 ^ 91 ^ 7 ^ 6 ^ 0 ^ 2 ^ 5 ^ 68 ^ 22 = 64\n" +
               "Perform this operation on each of the sixteen blocks of sixteen numbers in your sparse hash to determine the sixteen numbers in your dense hash.\n" +
               "\n" +
               "Finally, the standard way to represent a Knot Hash is as a single hexadecimal string; the final output is the dense hash in hexadecimal notation. Because each number in your dense hash will be between 0 and 255 (inclusive), always represent each number as two hexadecimal digits (including a leading zero as necessary). So, if your first three numbers are 64, 7, 255, they correspond to the hexadecimal numbers 40, 07, ff, and so the first six characters of the hash would be 4007ff. Because every Knot Hash is sixteen such numbers, the hexadecimal representation is always 32 hexadecimal digits (0-f) long.\n" +
               "\n" +
               "Here are some example hashes:\n" +
               "\n" +
               "The empty string becomes a2582a3a0e66e6e86e3812dcb672a272.\n" +
               "AoC 2017 becomes 33efeb34ea91902bb2f59c9920caa6cd.\n" +
               "1,2,3 becomes 3efbe78a8d82f29979031a4aa0b16a9d.\n" +
               "1,2,4 becomes 63960835bcdc130f0b66d7ff4f6a5a8e.\n" +
               "Treating your puzzle input as a string of ASCII characters, what is the Knot Hash of your puzzle input? Ignore any leading or trailing whitespace you might encounter.";
    }
}

