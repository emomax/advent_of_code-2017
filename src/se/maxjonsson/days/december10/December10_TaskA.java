package se.maxjonsson.days.december10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import se.maxjonsson.Task;
import se.maxjonsson.utils.FileReader;

public class December10_TaskA implements Task {

    private List<Integer> input;
    private int[] hashSequence;

    private int result = 0;

    public December10_TaskA() {
        input = Arrays.asList(FileReader.readSingleLine("input/december10/input.txt").split(",")).stream()
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

        hashSequence = IntStream.range(0, 256).toArray();
    }

    public December10_TaskA(final List<Integer> input, final int[] hashSequence) {
        this.input = new ArrayList<>(input);
        this.hashSequence = hashSequence;
    }

    @Override
    public void run() {
        int skipSize = 0;
        int currentPosition = 0;

        for (int subListLength : input) {
            updateHash(subListLength, currentPosition);

            currentPosition += subListLength + skipSize++;
            while (currentPosition > hashSequence.length) {
                currentPosition -= hashSequence.length;
            }
        }

        result = hashSequence[0] * hashSequence[1];
        System.out.println(String.format("Product of knot-hash's first 2 elements: '%s'.", result));
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
        final List<Integer> subList= new ArrayList<>();

        for (int i = currentPosition; i < currentPosition + subListLength; i++) {
            int indexWithinBounds = i >= hashSequence.length ? i - hashSequence.length : i;

            subList.add(hashSequence[indexWithinBounds]);
        }

        return subList;
    }

    public int getResult() {
        return result;
    }

    @Override
    public String getTaskName() {
        return "Day 10: Knot Hash";
    }

    @Override
    public String getTaskDescription() {
        return  "You come across some programs that are trying to implement a software emulation of a hash based on knot-tying. The hash these programs are implementing isn't very strong, but you decide to help them anyway. You make a mental note to remind the Elves later not to invent their own cryptographic functions.\n" +
                "\n" +
                "This hash function simulates tying a knot in a circle of string with 256 marks on it. Based on the input to be hashed, the function repeatedly selects a span of string, brings the ends together, and gives the span a half-twist to reverse the order of the marks within it. After doing this many times, the order of the marks is used to build the resulting hash.\n" +
                "\n" +
                "  4--5   pinch   4  5           4   1\n" +
                " /    \\  5,0,1  / \\/ \\  twist  / \\ / \\\n" +
                "3      0  -->  3      0  -->  3   X   0\n" +
                " \\    /         \\ /\\ /         \\ / \\ /\n" +
                "  2--1           2  1           2   5\n" +
                "To achieve this, begin with a list of numbers from 0 to 255, a current position which begins at 0 (the first element in the list), a skip size (which starts at 0), and a sequence of lengths (your puzzle input). Then, for each length:\n" +
                "\n" +
                "Reverse the order of that length of elements in the list, starting with the element at the current position.\n" +
                "Move the current position forward by that length plus the skip size.\n" +
                "Increase the skip size by one.\n" +
                "The list is circular; if the current position and the length try to reverse elements beyond the end of the list, the operation reverses using as many extra elements as it needs from the front of the list. If the current position moves past the end of the list, it wraps around to the front. Lengths larger than the size of the list are invalid.\n" +
                "\n" +
                "Here's an example using a smaller list:\n" +
                "\n" +
                "Suppose we instead only had a circular list containing five elements, 0, 1, 2, 3, 4, and were given input lengths of 3, 4, 1, 5.\n" +
                "\n" +
                "The list begins as [0] 1 2 3 4 (where square brackets indicate the current position).\n" +
                "The first length, 3, selects ([0] 1 2) 3 4 (where parentheses indicate the sublist to be reversed).\n" +
                "After reversing that section (0 1 2 into 2 1 0), we get ([2] 1 0) 3 4.\n" +
                "Then, the current position moves forward by the length, 3, plus the skip size, 0: 2 1 0 [3] 4. Finally, the skip size increases to 1.\n" +
                "The second length, 4, selects a section which wraps: 2 1) 0 ([3] 4.\n" +
                "The sublist 3 4 2 1 is reversed to form 1 2 4 3: 4 3) 0 ([1] 2.\n" +
                "The current position moves forward by the length plus the skip size, a total of 5, causing it not to move because it wraps around: 4 3 0 [1] 2. The skip size increases to 2.\n" +
                "The third length, 1, selects a sublist of a single element, and so reversing it has no effect.\n" +
                "The current position moves forward by the length (1) plus the skip size (2): 4 [3] 0 1 2. The skip size increases to 3.\n" +
                "The fourth length, 5, selects every element starting with the second: 4) ([3] 0 1 2. Reversing this sublist (3 0 1 2 4 into 4 2 1 0 3) produces: 3) ([4] 2 1 0.\n" +
                "Finally, the current position moves forward by 8: 3 4 2 1 [0]. The skip size increases to 4.\n" +
                "In this example, the first two numbers in the list end up being 3 and 4; to check the process, you can multiply them together to produce 12.\n" +
                "\n" +
                "However, you should instead use the standard list size of 256 (with values 0 to 255) and the sequence of lengths in your puzzle input. Once this process is complete, what is the result of multiplying the first two numbers in the list?";
    }
}

