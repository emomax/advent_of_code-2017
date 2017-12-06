package se.maxjonsson.days.december6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import se.maxjonsson.Task;
import se.maxjonsson.utils.FileReader;

public class December6_TaskB implements Task {

    private List<Integer> input;
    private int result = 0;

    public December6_TaskB() {
        final List<String> instructions = Arrays.asList(FileReader.readSingleLine("input/december6/input.txt").split("\\s+"));
        this.input = instructions.stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    public December6_TaskB(final List<Integer> input) {
        this.input = new ArrayList<>(input);
    }

    @Override
    public void run() {
        final Map<String, Integer> jumpsMap = new HashMap<>();
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

            distributeHighestBlock(blocks, maxValue, maxIndex);

            final String currentList = blocks.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining("-"));

            if (jumpsMap.containsKey(currentList)) {
                result = dataRedistributions - jumpsMap.get(currentList);
                break;
            }

            jumpsMap.put(currentList, dataRedistributions);
        }

        System.out.println(String.format("Block shift cycle was %s.", result));
    }

    private void distributeHighestBlock(final List<Integer> mutatedData, final int maxValue, final int offset) {
        mutatedData.set(offset, 0);

        for (int i = 1; i <= maxValue; i++) {
            int currentIndex = (offset + i) % mutatedData.size();
            mutatedData.set(currentIndex, mutatedData.get(currentIndex) + 1);
        }
    }

    public int getResult() {
        return result;
    }

    @Override
    public String getTaskName() {
        return "Day 6: Memory Reallocation - Part Two";
    }

    @Override
    public String getTaskDescription() {
        return  "Out of curiosity, the debugger would also like to know the size of the loop: starting from a state that has already been seen, how many block redistribution cycles must be performed before that same state is seen again?\n" +
                "\n" +
                "In the example above, 2 4 1 2 is seen again after four cycles, and so the answer in that example would be 4.\n" +
                "\n" +
                "How many cycles are in the infinite loop that arises from the configuration in your puzzle input?";  }
}

