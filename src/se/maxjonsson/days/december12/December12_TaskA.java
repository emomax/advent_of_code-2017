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

public class December12_TaskA implements Task {
    private class MailNode {
        private String id;
        private List<MailNode> neighbors = new ArrayList<>();

        MailNode(final String id) {
            this.id = id;
        }

        void addNeighbor(MailNode neighbor) {
            System.out.println(this.id + ": Adding neighbor '" + neighbor.id + "'");
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
    private Map<String, MailNode> graph  = new HashMap<>();
    private int                   result = 0;

    public December12_TaskA() {
        this.input = FileReader.readLines("input/december12/input.txt");
    }

    public December12_TaskA(final List<String> input) {
        this.input = new ArrayList<>(input);
    }

    @Override
    public void run() {
        mapGraph(input);

        result = getGroupSizeContainingId("0");
        System.out.println(String.format("Size of group: '%s'.", result));
    }

    private int getGroupSizeContainingId(String id) {
        MailNode start = graph.get(id);

        final Set<String> checkedNodes = new HashSet<>();
        final List<MailNode> openNodes = new ArrayList<>();

        openNodes.add(start);

        int size = 0;

        while (!openNodes.isEmpty()) {
            MailNode nextNeighbor = openNodes.remove(0);

            checkedNodes.add(nextNeighbor.id);

            for (MailNode neighbor : nextNeighbor.getNeighbors()) {
                if (!checkedNodes.contains(neighbor.id) && !openNodes.contains(neighbor)) {
                    openNodes.add(neighbor);
                }
            }

            size++;
        }

        return size;
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
        return "Day 12: Digital Plumber";
    }

    @Override
    public String getTaskDescription() {
        return "Walking along the memory banks of the stream, you find a small village that is experiencing a little confusion: some programs can't communicate with each other.\n" +
               "\n" +
               "Programs in this village communicate using a fixed system of pipes. Messages are passed between programs using these pipes, but most programs aren't connected to each other directly. Instead, programs pass messages between each other until the message reaches the intended recipient.\n" +
               "\n" +
               "For some reason, though, some of these messages aren't ever reaching their intended recipient, and the programs suspect that some pipes are missing. They would like you to investigate.\n" +
               "\n" +
               "You walk through the village and record the ID of each program and the IDs with which it can communicate directly (your puzzle input). Each program has one or more programs with which it can communicate, and these pipes are bidirectional; if 8 says it can communicate with 11, then 11 will say it can communicate with 8.\n" +
               "\n" +
               "You need to figure out how many programs are in the group that contains program ID 0.\n" +
               "\n" +
               "For example, suppose you go door-to-door like a travelling salesman and record the following list:\n" +
               "\n" +
               "0 <-> 2\n" +
               "1 <-> 1\n" +
               "2 <-> 0, 3, 4\n" +
               "3 <-> 2, 4\n" +
               "4 <-> 2, 3, 6\n" +
               "5 <-> 6\n" +
               "6 <-> 4, 5\n" +
               "In this example, the following programs are in the group that contains program ID 0:\n" +
               "\n" +
               "Program 0 by definition.\n" +
               "Program 2, directly connected to program 0.\n" +
               "Program 3 via program 2.\n" +
               "Program 4 via program 2.\n" +
               "Program 5 via programs 6, then 4, then 2.\n" +
               "Program 6 via programs 4, then 2.\n" +
               "Therefore, a total of 6 programs are in this group; all but program 1, which has a pipe that connects it to itself.\n" +
               "\n" +
               "How many programs are in the group that contains program ID 0?";
    }
}

