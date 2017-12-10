package se.maxjonsson.days.december7;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import se.maxjonsson.Task;
import se.maxjonsson.utils.FileReader;

public class December7_TaskA implements Task {

    private class ProgramTowerNode {
        private final String name;
        private final int    weight;

        private List<String> children;
        private ProgramTowerNode parent = null;

        public ProgramTowerNode(final String name, final int weight) {
            this.name = name;
            this.weight = weight;
            this.children = new LinkedList<>();
        }

        public ProgramTowerNode(final String name, final int weight, final List<String> children) {
            this.name = name;
            this.weight = weight;
            this.children = new LinkedList<>(children);
        }

        public void setParent(final ProgramTowerNode parentNode) {
            this.parent = parentNode;
        }

        public ProgramTowerNode getParent() {
            return parent;
        }

        public List<String> getChildren() {
            return children;
        }
    }

    private List<String> input;
    private String result = "";

    public December7_TaskA() {
        input = FileReader.readLines("input/december7/input.txt");
    }

    public December7_TaskA(final List<String> input) {
        this.input = input;
    }

    @Override
    public void run() {
        result = getTowerBaseProgram(input);

        System.out.println(String.format("Base tower node was %s.", result));
    }

    private String getTowerBaseProgram(final List<String> instructions) {
        final Map<String, ProgramTowerNode> nodes = mapNodes(instructions);

        for (ProgramTowerNode node : nodes.values()) {
            if (node.children.isEmpty()) {
                continue;
            }

            for (String child : node.children) {
                nodes.get(child).setParent(node);
            }
        }

        ProgramTowerNode baseNode = nodes.values().stream()
                .filter(node -> node.getChildren().isEmpty())
                .findAny()
                .get();

        while (baseNode.parent != null) {
            baseNode = baseNode.getParent();
        }

        return baseNode.name;
    }

    private Map<String, ProgramTowerNode> mapNodes(final List<String> instructions) {
        final Map<String, ProgramTowerNode> nodes = new HashMap<>();

        instructions.stream()
                .map(this::fromInstruction)
                .forEach(node -> nodes.put(node.name, node));

        return nodes;
    }


    public ProgramTowerNode fromInstruction(final String instruction) {
        final String instructionPattern = "^(?<name>\\w+) \\((?<weight>\\d+)\\)(?: ->(?<children>(?: \\w+,?)+))?$";
        final Matcher nodeMatcher = Pattern.compile(instructionPattern).matcher(instruction);

        if (!nodeMatcher.matches()) {
            throw new RuntimeException("Failed to parse instruction.");
        }

        if (nodeMatcher.group("children") != null) {
            final int weight = Integer.parseInt(nodeMatcher.group("weight"));
            final String name = nodeMatcher.group("name");
            final List<String> children = Arrays.asList(nodeMatcher
                                                                .group("children")
                                                                .replaceAll("\\s", "")
                                                                .split(","));
            return new ProgramTowerNode(name, weight, children);
        }
        else {
            final int weight = Integer.parseInt(nodeMatcher.group("weight"));
            final String name = nodeMatcher.group("name");
            return new ProgramTowerNode(name, weight);
        }
    }

    public String getResult() {
        return result;
    }

    @Override
    public String getTaskName() {
        return "Day 7: Recursive Circus";
    }

    @Override
    public String getTaskDescription() {
        return "Wandering further through the circuits of the computer, you come upon a tower of programs that have gotten themselves into a bit of trouble. A recursive algorithm has gotten out of hand, and now they're balanced precariously in a large tower.\n" +
               "\n" +
               "One program at the bottom supports the entire tower. It's holding a large disc, and on the disc are balanced several more sub-towers. At the bottom of these sub-towers, standing on the bottom disc, are other programs, each holding their own disc, and so on. At the very tops of these sub-sub-sub-...-towers, many programs stand simply keeping the disc below them balanced but with no disc of their own.\n" +
               "\n" +
               "You offer to help, but first you need to understand the structure of these towers. You ask each program to yell out their name, their weight, and (if they're holding a disc) the names of the programs immediately above them balancing on that disc. You write this information down (your puzzle input). Unfortunately, in their panic, they don't do this in an orderly fashion; by the time you're done, you're not sure which program gave which information.\n" +
               "\n" +
               "For example, if your list is the following:\n" +
               "\n" +
               "pbga (66)\n" +
               "xhth (57)\n" +
               "ebii (61)\n" +
               "havc (66)\n" +
               "ktlj (57)\n" +
               "fwft (72) -> ktlj, cntj, xhth\n" +
               "qoyq (66)\n" +
               "padx (45) -> pbga, havc, qoyq\n" +
               "tknk (41) -> ugml, padx, fwft\n" +
               "jptl (61)\n" +
               "ugml (68) -> gyxo, ebii, jptl\n" +
               "gyxo (61)\n" +
               "cntj (57)\n" +
               "...then you would be able to recreate the structure of the towers that looks like this:\n" +
               "\n" +
               "                gyxo\n" +
               "              /     \n" +
               "         ugml - ebii\n" +
               "       /      \\     \n" +
               "      |         jptl\n" +
               "      |        \n" +
               "      |         pbga\n" +
               "     /        /\n" +
               "tknk --- padx - havc\n" +
               "     \\        \\\n" +
               "      |         qoyq\n" +
               "      |             \n" +
               "      |         ktlj\n" +
               "       \\      /     \n" +
               "         fwft - cntj\n" +
               "              \\     \n" +
               "                xhth\n" +
               "In this example, tknk is at the bottom of the tower (the bottom program), and is holding up ugml, padx, and fwft. Those programs are, in turn, holding up other programs; in this example, none of those programs are holding up any other programs, and are all the tops of their own towers. (The actual tower balancing in front of you is much larger.)";
    }
}

