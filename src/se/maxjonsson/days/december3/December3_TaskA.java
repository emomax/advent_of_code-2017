package se.maxjonsson.days.december3;

import se.maxjonsson.Task;
import se.maxjonsson.utils.Coordinate;
import se.maxjonsson.utils.FileReader;

public class December3_TaskA implements Task {


    private int result = 0;
    final int input;

    public December3_TaskA() {
        final String inputText = FileReader.readSingleLine("input/december3/input.txt");
        this.input = Integer.parseInt(inputText);
    }

    public December3_TaskA(final int input) {
        this.input = input;
    }

    @Override
    public void run() {
        final Coordinate translateFromSpiralToCartesian = convertToCartesian(input);

        result = manhattanDistance(translateFromSpiralToCartesian);

        System.out.println("Distance was: " + result);
    }

    Coordinate convertToCartesian(final int spiralCoordinate) {
        final int stepsToCorner = findLowestSquareAboveValue(spiralCoordinate);

        if (stepsToCorner == 0) {
            return new Coordinate(0, 0);
        }

        int lengthOfASide = (stepsToCorner * 2);

        int lowerRightCornerValue = (1 + stepsToCorner * 2) * (1 + stepsToCorner * 2);
        int lowerLeftCornerValue = lowerRightCornerValue - lengthOfASide;
        int upperLeftCornerValue = lowerRightCornerValue - lengthOfASide * 2;
        int upperRightCornerValue = lowerRightCornerValue - lengthOfASide * 3;

        if (spiralCoordinate > lowerLeftCornerValue) {
            return new Coordinate(stepsToCorner - (lowerRightCornerValue - spiralCoordinate), -stepsToCorner);
        }
        else if (spiralCoordinate > upperLeftCornerValue) {
            return new Coordinate(-stepsToCorner, -stepsToCorner + (lowerLeftCornerValue - spiralCoordinate));
        }
        else if (spiralCoordinate > upperRightCornerValue) {
            return new Coordinate(-stepsToCorner + (upperLeftCornerValue - spiralCoordinate), stepsToCorner);
        }
        else if (spiralCoordinate > upperRightCornerValue - lengthOfASide) {
            return new Coordinate(stepsToCorner, stepsToCorner - (upperRightCornerValue - spiralCoordinate));
        }
        else {
            throw new RuntimeException("Input was in wrong square!");
        }

        // 1 + 2 * n
        //n = 0 -> 1
        //n = 1 -> 3
        //n = 2 -> 5
        //n = 3 -> 7

    }

    private int findLowestSquareAboveValue(final int value) {
        int squareIterator = 0;
        int memoryLayerRoot = 1 + 2 * squareIterator;

        while (memoryLayerRoot * memoryLayerRoot < value) {
            squareIterator++;
            memoryLayerRoot = 1 + 2 * squareIterator;
        }

        //System.out.println("Value: " + value + ", found n: " + squareIterator + "(" + (1 + 2 * squareIterator) + ")");
        return squareIterator;
    }

    private int manhattanDistance(final Coordinate c) {
        return Math.abs(c.getX()) + Math.abs(c.getY());
    }

    public int getResult() {
        return result;
    }

    @Override
    public String getTaskName() {
        return "Day 3: Spiral Memory";
    }

    @Override
    public String getTaskDescription() {
        return "You come across an experimental new kind of memory stored on an infinite two-dimensional grid.\n" +
               "\n" +
               "Each square on the grid is allocated in a spiral pattern starting at a location marked 1 and then counting up while spiraling outward. For example, the first few squares are allocated like this:\n" +
               "\n" +
               "17  16  15  14  13\n" +
               "18   5   4   3  12\n" +
               "19   6   1   2  11\n" +
               "20   7   8   9  10\n" +
               "21  22  23---> ...\n" +
               "While this is very space-efficient (no squares are skipped), requested data must be carried back to square 1 (the location of the only access port for this memory system) by programs that can only move up, down, left, or right. They always take the shortest path: the Manhattan Distance between the location of the data and square 1.\n" +
               "\n" +
               "For example:\n" +
               "\n" +
               "Data from square 1 is carried 0 steps, since it's at the access port.\n" +
               "Data from square 12 is carried 3 steps, such as: down, left, left.\n" +
               "Data from square 23 is carried only 2 steps: up twice.\n" +
               "Data from square 1024 must be carried 31 steps.\n" +
               "How many steps are required to carry the data from the square identified in your puzzle input all the way to the access port?";
    }
}

