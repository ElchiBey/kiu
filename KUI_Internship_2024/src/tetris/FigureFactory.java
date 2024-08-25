package tetris;

import java.util.Random;

public class FigureFactory {
    private static final Random random = new Random();

    public static int[][] createNextFigure() {
        return generateRandomFigure();
    }

    private static int[][] generateRandomFigure() {
        int[][][] figures = {O(), J(), I(), T(), L(), S(), Z()};
        return figures[random.nextInt(figures.length)];
    }

    static int[][] O() {
        return new int[][]{
                {0, 1, 1, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
    }

    static int[][] J() {
        return new int[][]{
                {0, 0, 2, 0},
                {0, 0, 2, 0},
                {0, 2, 2, 0},
                {0, 0, 0, 0},
        };
    }

    static int[][] I() {
        return new int[][]{
                {3, 3, 3, 3},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
    }

    static int[][] T() {
        return new int[][]{
                {0, 0, 4, 0},
                {0, 4, 4, 0},
                {0, 0, 4, 0},
                {0, 0, 0, 0},
        };
    }

    static int[][] L() {
        return new int[][]{
                {0, 5, 0, 0},
                {0, 5, 0, 0},
                {0, 5, 5, 0},
                {0, 0, 0, 0},
        };
    }

    static int[][] S() {
        return new int[][]{
                {0, 6, 0, 0},
                {0, 6, 6, 0},
                {0, 0, 6, 0},
                {0, 0, 0, 0},
        };
    }

    static int[][] Z() {
        return new int[][]{
                {0, 0, 7, 0},
                {0, 7, 7, 0},
                {0, 7, 0, 0},
                {0, 0, 0, 0},
        };
    }

    static int[][] rotatedJ() {
        return new int[][]{
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 2, 2, 2},
                {0, 0, 0, 0},
        };
    }

}
