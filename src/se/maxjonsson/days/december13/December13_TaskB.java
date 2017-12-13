package se.maxjonsson.days.december13;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import se.maxjonsson.Task;
import se.maxjonsson.utils.FileReader;

public class December13_TaskA implements Task {

    private class FireWall {
        private int currentPosition = 0;
        private int direction       = 1;
        private final int index;
        private final int length;

        public FireWall(int index, int length) {
            this.index = index;
            this.length = length;
        }

        private void stepForward() {
            currentPosition += direction;

            if (currentPosition == length - 1) {
                direction = -1;
            }
            else if (currentPosition == 0) {
                direction = 1;
            }
        }

        private void reset() {

        }

        @Override
        public String toString() {
            return "[ " +
                   IntStream.range(0, length)
                           .mapToObj(i -> i == currentPosition ? "x" : "" + i)
                           .collect(Collectors.joining(", ")) +
                   "]";
        }
    }

    private List<String> input;
    private int                    result    = 0;
    private Map<Integer, FireWall> firewalls = new HashMap<>();

    public December13_TaskA() {
        this.input = FileReader.readLines("input/december13/input.txt");
    }

    public December13_TaskA(final List<String> input) {
        this.input = new ArrayList<>(input);
    }

    @Override
    public void run() {
        int maxIndex = mapFireWalls(input);

        int severity = 0;

        for (int i = 0; i < maxIndex + 1; i++) {

            if (!firewalls.containsKey(i)) {
                continue;
            }

            FireWall currentWall = firewalls.get(i);

            for (int j = 0; j < i; j++) {
                currentWall.stepForward();
            }

            System.out.println(currentWall);

            if (currentWall.currentPosition == 0) {
                System.out.println("Collision!");
                severity += currentWall.index * currentWall.length;
            }
        }

        result = severity;
        System.out.println(String.format("Collisions: '%s'.", result));
    }

    private int mapFireWalls(final List<String> input) {
        int currentMax = 0;

        for (final String wall : input) {
            Matcher firewallMatcher = Pattern.compile("^(?<id>\\d+): (?<maxLength>\\d+)$").matcher(wall);

            if (!firewallMatcher.matches()) {
                throw new RuntimeException("Failed to parse firewall: " + wall);
            }

            final int id = Integer.parseInt(firewallMatcher.group("id"));
            final int maxLength = Integer.parseInt(firewallMatcher.group("maxLength"));

            firewalls.put(id, new FireWall(id, maxLength));

            if (currentMax < id) {
                currentMax = id;
            }
        }

        return currentMax;
    }


    public int getResult() {
        return result;
    }

    @Override
    public String getTaskName() {
        return "Day 13: Packet Scanners";
    }

    @Override
    public String getTaskDescription() {
        return "You need to cross a vast firewall. The firewall consists of several layers, each with a security scanner that moves back and forth across the layer. To succeed, you must not be detected by a scanner.\n" +
               "\n" +
               "By studying the firewall briefly, you are able to record (in your puzzle input) the depth of each layer and the range of the scanning area for the scanner within it, written as depth: range. Each layer has a thickness of exactly 1. A layer at depth 0 begins immediately inside the firewall; a layer at depth 1 would start immediately after that.\n" +
               "\n" +
               "For example, suppose you've recorded the following:\n" +
               "\n" +
               "0: 3\n" +
               "1: 2\n" +
               "4: 4\n" +
               "6: 4\n" +
               "This means that there is a layer immediately inside the firewall (with range 3), a second layer immediately after that (with range 2), a third layer which begins at depth 4 (with range 4), and a fourth layer which begins at depth 6 (also with range 4). Visually, it might look like this:\n" +
               "\n" +
               " 0   1   2   3   4   5   6\n" +
               "[ ] [ ] ... ... [ ] ... [ ]\n" +
               "[ ] [ ]         [ ]     [ ]\n" +
               "[ ]             [ ]     [ ]\n" +
               "                [ ]     [ ]\n" +
               "Within each layer, a security scanner moves back and forth within its range. Each security scanner starts at the top and moves down until it reaches the bottom, then moves up until it reaches the top, and repeats. A security scanner takes one picosecond to move one step. Drawing scanners as S, the first few picoseconds look like this:\n" +
               "\n" +
               "\n" +
               "Picosecond 0:\n" +
               " 0   1   2   3   4   5   6\n" +
               "[S] [S] ... ... [S] ... [S]\n" +
               "[ ] [ ]         [ ]     [ ]\n" +
               "[ ]             [ ]     [ ]\n" +
               "                [ ]     [ ]\n" +
               "\n" +
               "Picosecond 1:\n" +
               " 0   1   2   3   4   5   6\n" +
               "[ ] [ ] ... ... [ ] ... [ ]\n" +
               "[S] [S]         [S]     [S]\n" +
               "[ ]             [ ]     [ ]\n" +
               "                [ ]     [ ]\n" +
               "\n" +
               "Picosecond 2:\n" +
               " 0   1   2   3   4   5   6\n" +
               "[ ] [S] ... ... [ ] ... [ ]\n" +
               "[ ] [ ]         [ ]     [ ]\n" +
               "[S]             [S]     [S]\n" +
               "                [ ]     [ ]\n" +
               "\n" +
               "Picosecond 3:\n" +
               " 0   1   2   3   4   5   6\n" +
               "[ ] [ ] ... ... [ ] ... [ ]\n" +
               "[S] [S]         [ ]     [ ]\n" +
               "[ ]             [ ]     [ ]\n" +
               "                [S]     [S]\n" +
               "Your plan is to hitch a ride on a packet about to move through the firewall. The packet will travel along the top of each layer, and it moves at one layer per picosecond. Each picosecond, the packet moves one layer forward (its first move takes it into layer 0), and then the scanners move one step. If there is a scanner at the top of the layer as your packet enters it, you are caught. (If a scanner moves into the top of its layer while you are there, you are not caught: it doesn't have time to notice you before you leave.) If you were to do this in the configuration above, marking your current position with parentheses, your passage through the firewall would look like this:\n" +
               "\n" +
               "Initial state:\n" +
               " 0   1   2   3   4   5   6\n" +
               "[S] [S] ... ... [S] ... [S]\n" +
               "[ ] [ ]         [ ]     [ ]\n" +
               "[ ]             [ ]     [ ]\n" +
               "                [ ]     [ ]\n" +
               "\n" +
               "Picosecond 0:\n" +
               " 0   1   2   3   4   5   6\n" +
               "(S) [S] ... ... [S] ... [S]\n" +
               "[ ] [ ]         [ ]     [ ]\n" +
               "[ ]             [ ]     [ ]\n" +
               "                [ ]     [ ]\n" +
               "\n" +
               " 0   1   2   3   4   5   6\n" +
               "( ) [ ] ... ... [ ] ... [ ]\n" +
               "[S] [S]         [S]     [S]\n" +
               "[ ]             [ ]     [ ]\n" +
               "                [ ]     [ ]\n" +
               "\n" +
               "\n" +
               "Picosecond 1:\n" +
               " 0   1   2   3   4   5   6\n" +
               "[ ] ( ) ... ... [ ] ... [ ]\n" +
               "[S] [S]         [S]     [S]\n" +
               "[ ]             [ ]     [ ]\n" +
               "                [ ]     [ ]\n" +
               "\n" +
               " 0   1   2   3   4   5   6\n" +
               "[ ] (S) ... ... [ ] ... [ ]\n" +
               "[ ] [ ]         [ ]     [ ]\n" +
               "[S]             [S]     [S]\n" +
               "                [ ]     [ ]\n" +
               "\n" +
               "\n" +
               "Picosecond 2:\n" +
               " 0   1   2   3   4   5   6\n" +
               "[ ] [S] (.) ... [ ] ... [ ]\n" +
               "[ ] [ ]         [ ]     [ ]\n" +
               "[S]             [S]     [S]\n" +
               "                [ ]     [ ]\n" +
               "\n" +
               " 0   1   2   3   4   5   6\n" +
               "[ ] [ ] (.) ... [ ] ... [ ]\n" +
               "[S] [S]         [ ]     [ ]\n" +
               "[ ]             [ ]     [ ]\n" +
               "                [S]     [S]\n" +
               "\n" +
               "\n" +
               "Picosecond 3:\n" +
               " 0   1   2   3   4   5   6\n" +
               "[ ] [ ] ... (.) [ ] ... [ ]\n" +
               "[S] [S]         [ ]     [ ]\n" +
               "[ ]             [ ]     [ ]\n" +
               "                [S]     [S]\n" +
               "\n" +
               " 0   1   2   3   4   5   6\n" +
               "[S] [S] ... (.) [ ] ... [ ]\n" +
               "[ ] [ ]         [ ]     [ ]\n" +
               "[ ]             [S]     [S]\n" +
               "                [ ]     [ ]\n" +
               "\n" +
               "\n" +
               "Picosecond 4:\n" +
               " 0   1   2   3   4   5   6\n" +
               "[S] [S] ... ... ( ) ... [ ]\n" +
               "[ ] [ ]         [ ]     [ ]\n" +
               "[ ]             [S]     [S]\n" +
               "                [ ]     [ ]\n" +
               "\n" +
               " 0   1   2   3   4   5   6\n" +
               "[ ] [ ] ... ... ( ) ... [ ]\n" +
               "[S] [S]         [S]     [S]\n" +
               "[ ]             [ ]     [ ]\n" +
               "                [ ]     [ ]\n" +
               "\n" +
               "\n" +
               "Picosecond 5:\n" +
               " 0   1   2   3   4   5   6\n" +
               "[ ] [ ] ... ... [ ] (.) [ ]\n" +
               "[S] [S]         [S]     [S]\n" +
               "[ ]             [ ]     [ ]\n" +
               "                [ ]     [ ]\n" +
               "\n" +
               " 0   1   2   3   4   5   6\n" +
               "[ ] [S] ... ... [S] (.) [S]\n" +
               "[ ] [ ]         [ ]     [ ]\n" +
               "[S]             [ ]     [ ]\n" +
               "                [ ]     [ ]\n" +
               "\n" +
               "\n" +
               "Picosecond 6:\n" +
               " 0   1   2   3   4   5   6\n" +
               "[ ] [S] ... ... [S] ... (S)\n" +
               "[ ] [ ]         [ ]     [ ]\n" +
               "[S]             [ ]     [ ]\n" +
               "                [ ]     [ ]\n" +
               "\n" +
               " 0   1   2   3   4   5   6\n" +
               "[ ] [ ] ... ... [ ] ... ( )\n" +
               "[S] [S]         [S]     [S]\n" +
               "[ ]             [ ]     [ ]\n" +
               "                [ ]     [ ]\n" +
               "In this situation, you are caught in layers 0 and 6, because your packet entered the layer when its scanner was at the top when you entered it. You are not caught in layer 1, since the scanner moved into the top of the layer once you were already there.\n" +
               "\n" +
               "The severity of getting caught on a layer is equal to its depth multiplied by its range. (Ignore layers in which you do not get caught.) The severity of the whole trip is the sum of these values. In the example above, the trip severity is 0*3 + 6*4 = 24.\n" +
               "\n" +
               "Given the details of the firewall you've recorded, if you leave immediately, what is the severity of your whole trip?";
    }
}

