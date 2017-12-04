package se.maxjonsson.days.december3;

import se.maxjonsson.Task;
import se.maxjonsson.utils.Coordinate;
import se.maxjonsson.utils.FileReader;

public class December3_TaskB implements Task {

    private int result = 0;
    final int input;

    int[][] matrix;

    private December3_TaskA helper = new December3_TaskA();

    public December3_TaskB() {
        final String inputText = FileReader.readSingleLine("input/december3/input.txt");
        this.input = Integer.parseInt(inputText);
    }

    public December3_TaskB(final int input) {
        this.input = input;
    }

    @Override
    public void run() {
        matrix = getMatrix();

        int valueOfSquare = 1;
        int currentIndex = 2;

        // Initial value
        matrix[50][50] = 1;

        while (valueOfSquare < input) {
            final Coordinate cartesianIndex = helper.convertToCartesian(currentIndex);

            updateMatrixAt(matrix, cartesianIndex);

            valueOfSquare = matrix[50 + cartesianIndex.getX()][50 + cartesianIndex.getY()];
            currentIndex++;
        }

        result = valueOfSquare;
        System.out.println("Kernel value over input was: " + result);
    }

    private void updateMatrixAt(int[][] matrix, final Coordinate centerOfUpdate) {
        int sumForCenter = 0;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0) {
                    // center
                    continue;
                }

                sumForCenter += matrix[50 + centerOfUpdate.getX() + x][50 + centerOfUpdate.getY() + y];
            }
        }

        matrix[50 + centerOfUpdate.getX()][50 + centerOfUpdate.getY()] = sumForCenter;
    }

    private int[][] getMatrix() {
        int[][] matrix = new int[101][101];

        for (int i = 0; i < 101; i++) {
            for (int j = 0; j < 101; j++) {
                matrix[i][j] = 0;
            }
        }
        return matrix;
    }

    private int getSideLength(final int index) {
        return (1 + index * 2) * (1 + index * 2);
    }

    public int getResult() {
        return result;
    }

    public void printMatrix() {
        for (int y = 45; y < 55; y++) {
            String row = "";

            for (int x = 45; x < 55; x++) {
                row += matrix[x][y] + "\t";
            }
            System.out.println(row);
        }
    }

    @Override
    public String getTaskName() {
        return "Day 3: Spiral Memory - Part 2";
    }

    @Override
    public String getTaskDescription() {
        return "As a stress test on the system, the programs here clear the grid and then store the value 1 in square 1. Then, in the same allocation order as shown above, they store the sum of the values in all adjacent squares, including diagonals.\n" +
               "\n" +
               "So, the first few squares' values are chosen as follows:\n" +
               "\n" +
               "Square 1 starts with the value 1.\n" +
               "Square 2 has only one adjacent filled square (with value 1), so it also stores 1.\n" +
               "Square 3 has both of the above squares as neighbors and stores the sum of their values, 2.\n" +
               "Square 4 has all three of the aforementioned squares as neighbors and stores the sum of their values, 4.\n" +
               "Square 5 only has the first and fourth squares as neighbors, so it gets the value 5.\n" +
               "Once a square is written, its value does not change. Therefore, the first few squares would receive the following values:\n" +
               "\n" +
               "147  142  133  122   59\n" +
               "304    5    4    2   57\n" +
               "330   10    1    1   54\n" +
               "351   11   23   25   26\n" +
               "362  747  806--->   ...\n" +
               "What is the first value written that is larger than your puzzle input?";
    }
}

