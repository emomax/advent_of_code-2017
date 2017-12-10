package se.maxjonsson.days.december7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import se.maxjonsson.Task;
import se.maxjonsson.utils.FileReader;

public class December7_TaskB implements Task {

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

        public List<Integer> getChildWeights() {
            List<Integer> weights = new ArrayList<>();

            for (String childName : children) {
                int childWeight = nodes.get(childName).getFullWeight();
                weights.add(childWeight);
            }

            return weights;
        }

        public int getFullWeight() {
            int sum = this.weight;

            sum += getChildWeights().stream().mapToInt(Integer::intValue).sum();

            return sum;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    private List<String> input;
    private int          result;

    private Map<String, ProgramTowerNode> nodes = new HashMap<>();

    public December7_TaskB() {
        input = FileReader.readLines("input/december7/input.txt");
    }

    public December7_TaskB(final List<String> input) {
        this.input = input;
    }

    @Override
    public void run() {
        nodes = mapNodes(input);

        final ProgramTowerNode baseNode = getTowerBaseProgram();
        ProgramTowerNode faultyChild = getFaultyChild(baseNode);
        ProgramTowerNode faultyParent = baseNode;

        while (true) {
            if (faultyChild == null) {
                break;
            }

            ProgramTowerNode faultyGrandChild = getFaultyChild(faultyChild);

            if (faultyGrandChild == null) {
                break;
            }

            faultyParent = faultyChild;
            faultyChild = faultyGrandChild;
        }

        int desiredWeight = getExpectedChildWeight(faultyParent, faultyChild.name);

        result = faultyChild.weight + (desiredWeight - faultyChild.getFullWeight());
        System.out.println(String.format("Faulty tower node (%s) should weigh %s.", faultyChild.name, result));
    }

    private int getExpectedChildWeight(final ProgramTowerNode faultyParent, final String faultyChildName) {
        int desiredWeight = 0;
        for (final String childName : faultyParent.children) {
            if (!childName.equals(faultyChildName)) {
                desiredWeight = nodes.get(childName).getFullWeight();
                break;
            }
        }
        return desiredWeight;
    }

    private ProgramTowerNode getFaultyChild(final ProgramTowerNode parent) {
        final Map<Integer, ArrayList<String>> weightCount = new HashMap<>();

        for (final String childName : parent.children) {
            final ProgramTowerNode child = nodes.get(childName);
            int childWeight = child.getFullWeight();

            if (weightCount.containsKey(childWeight)) {
                weightCount.get(childWeight).add(childName);
            }
            else {
                final ArrayList<String> childNames = new ArrayList<>();
                childNames.add(childName);
                weightCount.put(childWeight, childNames);
            }
        }

        for (Map.Entry<Integer, ArrayList<String>> entry : weightCount.entrySet()) {
            if (entry.getValue().size() == 1) {
                return nodes.get(entry.getValue().get(0));
            }
        }

        return null;
    }

    private ProgramTowerNode getTowerBaseProgram() {
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

        return baseNode;
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

    public int getResult() {
        return result;
    }

    @Override
    public String getTaskName() {
        return "Day 7: Recursive Circus - Part Two";
    }

    @Override
    public String getTaskDescription() {
        return "The programs explain the situation: they can't get down. Rather, they could get down, if they weren't expending all of their energy trying to keep the tower balanced. Apparently, one program has the wrong weight, and until it's fixed, they're stuck here.\n" +
               "\n" +
               "For any program holding a disc, each program standing on that disc forms a sub-tower. Each of those sub-towers are supposed to be the same weight, or the disc itself isn't balanced. The weight of a tower is the sum of the weights of the programs in that tower.\n" +
               "\n" +
               "In the example above, this means that for ugml's disc to be balanced, gyxo, ebii, and jptl must all have the same weight, and they do: 61.\n" +
               "\n" +
               "However, for tknk to be balanced, each of the programs standing on its disc and all programs above it must each match. This means that the following sums must all be the same:\n" +
               "\n" +
               "ugml + (gyxo + ebii + jptl) = 68 + (61 + 61 + 61) = 251\n" +
               "padx + (pbga + havc + qoyq) = 45 + (66 + 66 + 66) = 243\n" +
               "fwft + (ktlj + cntj + xhth) = 72 + (57 + 57 + 57) = 243\n" +
               "As you can see, tknk's disc is unbalanced: ugml's stack is heavier than the other two. Even though the nodes above ugml are balanced, ugml itself is too heavy: it needs to be 8 units lighter for its stack to weigh 243 and keep the towers balanced. If this change were made, its weight would be 60.\n" +
               "\n" +
               "Given that exactly one program is the wrong weight, what would its weight need to be to balance the entire tower?";
    }
}

