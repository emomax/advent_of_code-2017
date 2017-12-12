package se.maxjonsson.days.december11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import se.maxjonsson.Task;
import se.maxjonsson.utils.FileReader;

public class December11_TaskB implements Task {

    private final class AStarCoordinate implements Comparable<AStarCoordinate>{
        private int x;
        private int y;

        private int fScore = 0;
        private int gScore = 0;

        private AStarCoordinate parent = null;

        AStarCoordinate(final int x, final int y) {
            this.x = x;
            this.y = y;
        }

        AStarCoordinate(final int x, final int y, final int distanceTravelled) {
            this.x = x;
            this.y = y;
            this.gScore = distanceTravelled;
        }

        AStarCoordinate(final int x, final int y, final int distanceTravelled, final int heuristicApproximation) {
            this.x = x;
            this.y = y;
            this.gScore = distanceTravelled;
            this.fScore = this.gScore + heuristicApproximation;
        }

        int getX() { return x; }

        int getY() { return y; }

        @Override
        public boolean equals(Object other) {
            if (other == null) {
                return false;
            }

            if (!AStarCoordinate.class.isAssignableFrom(other.getClass())) {
                return false;
            }
            final AStarCoordinate comparator = (AStarCoordinate) other;

            return comparator.getX() == this.x && comparator.getY() == this.y;
        }

        public void setParent(final AStarCoordinate parent) {
            this.parent = parent;
        }

        @Override
        public int compareTo(final AStarCoordinate other) {
            return Integer.compare(this.fScore, other.fScore);
        }

        @Override
        public String toString() {
            return "(" + this.x + ", " + this.y + ") - [f: " + this.fScore + "]";
        }
    }

    private List<String> input;
    private int result = 0;

    public December11_TaskB() {
        this.input = Arrays.asList(FileReader.readSingleLine("input/december11/input.txt").split(","));
    }

    public December11_TaskB(final List<String> input) {
        this.input = new ArrayList<>(input);
    }

    @Override
    public void run() {
        int dist = walkDistance(input);
        //AStarCoordinate endNodeWithClosestPath = shortestPath(new AStarCoordinate(0, 0), farOff).parent;

        result = dist;
        System.out.println(String.format("Shortest path of walked distance was: '%s'.", result));
    }

    private AStarCoordinate shortestPath(final AStarCoordinate start, final AStarCoordinate goal) {
        Set<String> closedSet = new HashSet<>();
        final Queue<AStarCoordinate> openSet = new PriorityQueue<>();

        openSet.add(start);

        while (openSet.size() != 0) {
            AStarCoordinate currentNode = openSet.poll();

            if (currentNode.getX() == goal.getX() && currentNode.getY() == goal.getY()) {
                return currentNode;
            }

            closedSet.add(simpleHash(currentNode));

            for (AStarCoordinate neighbour : getNeighbors(currentNode, goal)) {
                if (closedSet.contains(simpleHash(neighbour))) {
                    continue;
                }

                if (!openSet.contains(neighbour)) {
                    openSet.add(neighbour);
                }
            }
        }

        return start;
    }

    private List<AStarCoordinate> getNeighbors(final AStarCoordinate center, final AStarCoordinate goal) {
        boolean centerIsOnEvenOffset = center.getX() % 2 == 0;

        final int l = center.getX() - 1;
        final int r = center.getX() + 1;
        final int southIsh = centerIsOnEvenOffset ? center.getY() : center.getY() + 1;
        final int northIsh = centerIsOnEvenOffset ? center.getY() - 1 : center.getY();

        final AStarCoordinate se =  new AStarCoordinate(r, southIsh, center.gScore + 1, approximate(r, southIsh, goal));
        final AStarCoordinate sw =  new AStarCoordinate(l, southIsh , center.gScore + 1, approximate(l, southIsh, goal));
        final AStarCoordinate s =  new AStarCoordinate(center.getX(), center.getY() + 1, center.gScore + 1, approximate(center.getX(), center.getY() + 1, goal));
        final AStarCoordinate ne =  new AStarCoordinate(r, northIsh, center.gScore + 1, approximate(r, northIsh, goal));
        final AStarCoordinate nw =  new AStarCoordinate(l, northIsh , center.gScore + 1, approximate(l, northIsh, goal));
        final AStarCoordinate n =  new AStarCoordinate(center.getX(), center.getY() - 1, center.gScore + 1, approximate(center.getX(), center.getY() - 1, goal));

        se.setParent(center);
        sw.setParent(center);
        s.setParent(center);
        ne.setParent(center);
        nw.setParent(center);
        n.setParent(center);

        final List<AStarCoordinate> neighbors = Arrays.asList(se, sw, s, ne, nw, n);
        return neighbors;
    }

    private int approximate(final int x, final int y, AStarCoordinate goal) {
        return Math.abs(goal.x - x) + Math.abs(goal.y - y);
    }

    private String simpleHash(final AStarCoordinate coordinate) {
        return coordinate.getX() + "," + coordinate.getY();
    }

    private int walkDistance(final List<String> input) {

        int currentX = 0;
        int currentY = 0;

        int maxDistanceWalked = 0;

        for (String direction : input) {
            switch (direction) {
                case "s":
                    currentY += 1;
                    break;
                case "sw":
                    currentY += currentX % 2 != 0 ? 1 : 0;
                    currentX -= 1;
                    break;
                case "se":
                    currentY += currentX % 2 != 0 ? 1 : 0;
                    currentX += 1;
                    break;
                case "n":
                    currentY -= 1;
                    break;
                case "nw":
                    currentY += currentX % 2 == 0 ? -1 : 0;
                    currentX -= 1;
                    break;
                case "ne":
                    currentY += currentX % 2 == 0 ? -1 : 0;
                    currentX += 1;
                    break;
                default:
                    throw new RuntimeException("Unparseable direction: '" + direction + "'");
            }

            AStarCoordinate currentNode = shortestPath(new AStarCoordinate(0, 0), new AStarCoordinate(currentX, currentY));
            int distance = 0;

            while (currentNode != null) {
                distance++;
                //System.out.println(distance + " : "  + endNodeWithClosestPath);
                currentNode = currentNode.parent;
            }

            if (maxDistanceWalked < distance) {
                maxDistanceWalked = distance;
            }
        }

        // Don't calculate first node as a step
        return maxDistanceWalked - 1;
    }


    public int getResult() {
        return result;
    }

    @Override
    public String getTaskName() {
        return "Day 11: Hex Ed";
    }

    @Override
    public String getTaskDescription() {
        return "Crossing the bridge, you've barely reached the other side of the stream when a program comes up to you, clearly in distress. \"It's my child process,\" she says, \"he's gotten lost in an infinite grid!\"\n" +
               "\n" +
               "Fortunately for her, you have plenty of experience with infinite grids.\n" +
               "\n" +
               "Unfortunately for you, it's a hex grid.\n" +
               "\n" +
               "The hexagons (\"hexes\") in this grid are aligned such that adjacent hexes can be found to the north, northeast, southeast, south, southwest, and northwest:\n" +
               "\n" +
               "  \\ n  /\n" +
               "nw +--+ ne\n" +
               "  /    \\\n" +
               "-+      +-\n" +
               "  \\    /\n" +
               "sw +--+ se\n" +
               "  / s  \\\n" +
               "You have the path the child process took. Starting where he started, you need to determine the fewest number of steps required to reach him. (A \"step\" means to move from the hex you are in to any adjacent hex.)\n" +
               "\n" +
               "For example:\n" +
               "\n" +
               "ne,ne,ne is 3 steps away.\n" +
               "ne,ne,sw,sw is 0 steps away (back where you started).\n" +
               "ne,ne,s,s is 2 steps away (se,se).\n" +
               "se,sw,se,sw,sw is 3 steps away (s,s,sw).";
    }
}

