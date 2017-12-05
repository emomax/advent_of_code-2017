package se.maxjonsson.days.december5;

import java.util.List;
import java.util.stream.Collectors;

import se.maxjonsson.Task;
import se.maxjonsson.utils.FileReader;

public class December5_TaskA implements Task {

    private List<Integer> input;
    private int result = 0;

    public December5_TaskA() {
        final List<String> instructions = FileReader.readLines("input/december5/input.txt");
        this.input = instructions.stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    public December5_TaskA(final List<Integer> input) {
        this.input = input;
    }

    @Override
    public void run() {
        int position = 0;
        int jumps = 0;

        while (position >= 0 && position < input.size()) {
            final int jumpDistance = getJumpDistance(position);
            input.set(position, input.get(position) + 1);

            position += jumpDistance;
            jumps++;
        }

        result = jumps;
        System.out.println(String.format("Required %s jumps to escape the maze.", result));
    }

    private int getJumpDistance(final int currentPosition) {
        return input.get(currentPosition);
    }

    public int getResult() {
        return result;
    }

    @Override
    public String getTaskName() {
        return "Day 5: A Maze of Twisty Trampolines, All Alike";
    }

    @Override
    public String getTaskDescription() {
        return "An urgent interrupt arrives from the CPU: it's trapped in a maze of jump instructions, and it would like assistance from any programs with spare cycles to help find the exit.\n" +
               "\n" +
               "The message includes a list of the offsets for each jump. Jumps are relative: -1 moves to the previous instruction, and 2 skips the next one. Start at the first instruction in the list. The goal is to follow the jumps until one leads outside the list.\n" +
               "\n" +
               "In addition, these instructions are a little strange; after each jump, the offset of that instruction increases by 1. So, if you come across an offset of 3, you would move three instructions forward, but change it to a 4 for the next time it is encountered.\n" +
               "\n" +
               "For example, consider the following list of jump offsets:\n" +
               "\n" +
               "0\n" +
               "3\n" +
               "0\n" +
               "1\n" +
               "-3\n" +
               "Positive jumps (\"forward\") move downward; negative jumps move upward. For legibility in this example, these offset values will be written all on one line, with the current instruction marked in parentheses. The following steps would be taken before an exit is found:\n" +
               "\n" +
               "(0) 3  0  1  -3  - before we have taken any steps.\n" +
               "(1) 3  0  1  -3  - jump with offset 0 (that is, don't jump at all). Fortunately, the instruction is then incremented to 1.\n" +
               " 2 (3) 0  1  -3  - step forward because of the instruction we just modified. The first instruction is incremented again, now to 2.\n" +
               " 2  4  0  1 (-3) - jump all the way to the end; leave a 4 behind.\n" +
               " 2 (4) 0  1  -2  - go back to where we just were; increment -3 to -2.\n" +
               " 2  5  0  1  -2  - jump 4 steps forward, escaping the maze.\n" +
               "In this example, the exit is reached in 5 steps.";
    }
}

