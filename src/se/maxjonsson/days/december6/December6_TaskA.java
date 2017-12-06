package se.maxjonsson.days.december6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import se.maxjonsson.Task;
import se.maxjonsson.utils.FileReader;

public class December6_TaskA implements Task {

    private List<Integer> input;
    private int result = 0;

    public December6_TaskA() {
        final List<String> instructions = Arrays.asList(FileReader.readSingleLine("input/december6/input.txt").split("\\s+"));
        this.input = instructions.stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    public December6_TaskA(final List<Integer> input) {
        this.input = new ArrayList<>(input);
    }

    @Override
    public void run() {
        final Set<String> items = new HashSet<>();
        final List<Integer> blocks = new ArrayList<>(input);

        int dataRedistributions = 0;

        while (true) {
            dataRedistributions++;

            int maxValue = 0;
            int maxIndex = -1;

            for (int i = 0; i < blocks.size(); i++) {
                if (maxValue < blocks.get(i)) {
                    maxValue = blocks.get(i);
                    maxIndex = i;
                }
            }

            int startOffset = maxIndex;
            blocks.set(maxIndex, 0);

            for (int i = 1; i <= maxValue; i++) {
                int currentIndex = (startOffset + i) % blocks.size();
                blocks.set(currentIndex, blocks.get(currentIndex) + 1);
            }

            final String currentList = blocks.stream().map(i -> i.toString()).collect(Collectors.joining(""));

            if (items.contains(currentList)) {
                break;
            }

            items.add(currentList);
        }

        result = dataRedistributions;
        System.out.println(String.format("Required %s block shifts until first cycle was found.", result));
    }

    public int getResult() {
        return result;
    }

    @Override
    public String getTaskName() {
        return "Day 6: Memory Reallocation";
    }

    @Override
    public String getTaskDescription() {
        return "A debugger program here is having an issue: it is trying to repair a memory reallocation routine, but it keeps getting stuck in an infinite loop.\n" +
               "\n" +
               "In this area, there are sixteen memory banks; each memory bank can hold any number of blocks. The goal of the reallocation routine is to balance the blocks between the memory banks.\n" +
               "\n" +
               "The reallocation routine operates in cycles. In each cycle, it finds the memory bank with the most blocks (ties won by the lowest-numbered memory bank) and redistributes those blocks among the banks. To do this, it removes all of the blocks from the selected bank, then moves to the next (by index) memory bank and inserts one of the blocks. It continues doing this until it runs out of blocks; if it reaches the last memory bank, it wraps around to the first one.\n" +
               "\n" +
               "The debugger would like to know how many redistributions can be done before a blocks-in-banks configuration is produced that has been seen before.\n" +
               "\n" +
               "For example, imagine a scenario with only four memory banks:\n" +
               "\n" +
               "The banks start with 0, 2, 7, and 0 blocks. The third bank has the most blocks, so it is chosen for redistribution.\n" +
               "Starting with the next bank (the fourth bank) and then continuing to the first bank, the second bank, and so on, the 7 blocks are spread out over the memory banks. The fourth, first, and second banks get two blocks each, and the third bank gets one back. The final result looks like this: 2 4 1 2.\n" +
               "Next, the second bank is chosen because it contains the most blocks (four). Because there are four memory banks, each gets one block. The result is: 3 1 2 3.\n" +
               "Now, there is a tie between the first and fourth memory banks, both of which have three blocks. The first bank wins the tie, and its three blocks are distributed evenly over the other three banks, leaving it with none: 0 2 3 4.\n" +
               "The fourth bank is chosen, and its four blocks are distributed such that each of the four banks receives one: 1 3 4 1.\n" +
               "The third bank is chosen, and the same thing happens: 2 4 1 2.\n" +
               "At this point, we've reached a state we've seen before: 2 4 1 2 was already seen. The infinite loop is detected after the fifth block redistribution cycle, and so the answer in this example is 5.\n" +
               "\n" +
               "Given the initial block counts in your puzzle input, how many redistribution cycles must be completed before a configuration is produced that has been seen before?";
    }
}

