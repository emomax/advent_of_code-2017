package se.maxjonsson.days.december12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import se.maxjonsson.Task;
import se.maxjonsson.utils.FileReader;

public class December12_TaskB implements Task {
    private class MailNode {
        private final String id;
        private final List<MailNode> neighbors = new ArrayList<>();

        MailNode(final String id) {
            this.id = id;
        }

        void addNeighbor(MailNode neighbor) {
            neighbors.add(neighbor);
        }

        List<MailNode> getNeighbors() {
            return this.neighbors;
        }

        @Override
        public String toString() {
            return this.id + "(" + neighbors.stream().map(n -> n.id).collect(Collectors.joining(",")) + ")";
        }
    }

    private List<String> input;
    private Map<String, MailNode> graph = new HashMap<>();

    private Set<String> checkedNodes = new HashSet<>();
    private int result = 0;

    public December12_TaskB() {
        this.input = FileReader.readLines("input/december12/input.txt");
    }

    public December12_TaskB(final List<String> input) {
        this.input = new ArrayList<>(input);
    }

    @Override
    public void run() {
        mapGraph(input);

        result = getNumberOfGroups();
        System.out.println(String.format("Size of group: '%s'.", result));
    }

    private int getNumberOfGroups() {
        int groups = 0;

        for (Map.Entry<String, MailNode> nodeEntry : graph.entrySet()) {
            if (checkedNodes.contains(nodeEntry.getKey())) {
                continue;
            }

            final MailNode start = nodeEntry.getValue();
            mapGroup(start);

            groups++;
        }

        return groups;
    }

    private void mapGroup(final MailNode start) {
        final Set<String> checkedNeighbors = new HashSet<>();
        final List<MailNode> uncheckedNeighbors = new ArrayList<>();

        uncheckedNeighbors.add(start);

        while (!uncheckedNeighbors.isEmpty()) {
            MailNode nextNeighbor = uncheckedNeighbors.remove(0);

            checkedNodes.add(nextNeighbor.id);
            checkedNeighbors.add(nextNeighbor.id);

            for (final MailNode neighbor : nextNeighbor.getNeighbors()) {
                if (!checkedNeighbors.contains(neighbor.id) && !uncheckedNeighbors.contains(neighbor)) {
                    uncheckedNeighbors.add(neighbor);
                }
            }
        }
    }

    private void mapGraph(final List<String> input) {
        final String instructionRegex = "^(?<id>\\d+) \\<\\-\\> (?<neighbors>[0-9, ]+)$";

        for (String instruction : input) {
            final Matcher instructionMatcher = Pattern.compile(instructionRegex).matcher(instruction);
            if (!instructionMatcher.matches()) {
                throw new RuntimeException("Failed to parse instruction: '" + instruction + "'");
            }

            final String washedNeighbors = instructionMatcher.group("neighbors").replaceAll("\\s", "");
            final List<String> neighbors = Arrays.asList(washedNeighbors.split(","));

            final MailNode node = getNeighbor(instructionMatcher.group("id"));

            for (String neighbor : neighbors) {
                MailNode neighborNode = getNeighbor(neighbor);
                node.addNeighbor(neighborNode);
            }
        }
    }

    private MailNode getNeighbor(final String id) {
        if (graph.containsKey(id)) {
            return graph.get(id);
        }

        graph.put(id, new MailNode(id));
        return graph.get(id);
    }

    public int getResult() {
        return result;
    }

    @Override
    public String getTaskName() {
        return "Day 12: Digital Plumber - Part Two";
    }

    @Override
    public String getTaskDescription() {
        return "There are more programs than just the ones in the group containing program ID 0. The rest of them have no way of reaching that group, and still might have no way of reaching each other.\n" +
               "\n" +
               "A group is a collection of programs that can all communicate via pipes either directly or indirectly. The programs you identified just a moment ago are all part of the same group. Now, they would like you to determine the total number of groups.\n" +
               "\n" +
               "In the example above, there were 2 groups: one consisting of programs 0,2,3,4,5,6, and the other consisting solely of program 1.\n" +
               "\n" +
               "How many groups are there in total?";
    }
}

