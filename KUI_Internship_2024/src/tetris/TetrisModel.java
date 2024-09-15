package tetris;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

public class TetrisModel implements GameEventsListener {

    public static final int DEFAULT_HEIGHT = 20;
    public static final int DEFAULT_WIDTH = 10;
    public static final int DEFAULT_COLORS_NUMBER = 7;
    private static final int DEFAULT_PERIOD = 1000;

    int maxColors;
    public TetrisState state = new TetrisState();
    final List<ModelListener> listeners = new ArrayList<>();

    public void addListener(ModelListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ModelListener listener) {
        listeners.remove(listener);
    }

    public TetrisModel(int width, int height, int maxColors) {
        this.state.width = width;
        this.state.height = height;
        this.maxColors = maxColors;
        state.field = new int[height][width];
        if (!state.gameOver)
            initFigure();
    }

    private void initFigure() {
        state.figure = FigureFactory.createNextFigure();
        state.position = new Pair(state.width / 2 - state.figure[0].length / 2, 0);
    }

    public Pair size() {
        return new Pair(state.width, state.height);
    }

    @Override
    public void slideDown() {
        if (state.gameOver) return;

        if (!state.paused) {
            var newPosition = new Pair(state.position.x(), state.position.y() + 1);
            if (isNewFiguresPositionValid(newPosition)) {
                state.position = newPosition;
                notifyListeners();
            } else {
                pasteFigure();
                deleteFullRows();
                initFigure();
                notifyListeners();
                if (!isNewFiguresPositionValid(state.position)) {
                    gameOver();
                }
            }
        }
    }

    @Override
    public void moveLeft() {
        if (state.gameOver) return;

        if (!state.paused) {
            var newPosition = new Pair(state.position.x() - 1, state.position.y());
            if (isNewFiguresPositionValid(newPosition)) {
                state.position = newPosition;
                notifyListeners();
            }
        }
    }

    @Override
    public void moveRight() {
        if (state.gameOver) return;

        if (!state.paused) {
            var newPosition = new Pair(state.position.x() + 1, state.position.y());
            if (isNewFiguresPositionValid(newPosition)) {
                state.position = newPosition;
                notifyListeners();
            }
        }
    }

    @Override
    public void rotate() {
        if (state.gameOver) return;

        if (!state.paused) {
            if (!checkFigureForSquare(state.figure.clone())) {
                int[][] rotatedFigure = new int[4][4];
                for (int r = 0; r < state.figure.length; r++) {
                    for (int c = 0; c < state.figure[r].length; c++) {
                        rotatedFigure[c][3 - r] = state.figure[r][c];
                    }
                }
                if (isNewFiguresPositionValidAfterRotation(state.position, rotatedFigure)) {
                    state.figure = rotatedFigure;
                    notifyListeners();
                }
            }
        }
    }

    private boolean checkFigureForSquare(int[][] figure) {
        return Arrays.deepEquals(figure, new int[][]{
                {0, 1, 1, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        });
    }

    @Override
    public void drop() {
        if (state.gameOver) return;

        if (!state.paused) {
            var droppedFigurePosition = new Pair(state.position.x(), state.position.y() + 1);

            while (isNewFiguresPositionValid(droppedFigurePosition)) {
                state.position = droppedFigurePosition;
                droppedFigurePosition = new Pair(state.position.x(), state.position.y() + 1);
            }

            pasteFigure();
            deleteFullRows();
            initFigure();
            notifyListeners();
        }
    }

    public boolean isNewFiguresPositionValid(Pair newPosition) {
        return isNewFiguresPositionValidAfterRotation(newPosition, state.figure);
    }

    public boolean isNewFiguresPositionValidAfterRotation(Pair newPosition, int[][] figure) {
        boolean[] result = new boolean[1];
        result[0] = true;

        walkThroughAllFigureCells(newPosition, figure, (absPos, k) -> {
            if (result[0]) {
                result[0] = checkAbsPos(absPos);
            }
        });

        return result[0];
    }

    private void walkThroughAllFigureCells(Pair newPosition, int[][] figure, BiConsumer<Pair, Pair> payload) {
        for (int row = 0; row < figure.length; row++) {
            for (int col = 0; col < figure[row].length; col++) {
                if (figure[row][col] == 0)
                    continue;
                int absCol = newPosition.x() + col;
                int absRow = newPosition.y() + row;
                payload.accept(new Pair(absCol, absRow), new Pair(col, row));
            }
        }
    }

    private boolean checkAbsPos(Pair absPos) {
        var absCol = absPos.x();
        var absRow = absPos.y();
        if (isColumnPositionOutOfBoundaries(absCol))
            return false;
        if (isRowPositionOutOfBoundaries(absRow))
            return false;
        return state.field[absRow][absCol] == 0;
    }

    private boolean isRowPositionOutOfBoundaries(int absRow) {
        return absRow < 0 || absRow >= state.height;
    }

    private boolean isColumnPositionOutOfBoundaries(int absCol) {
        return absCol < 0 || absCol >= state.width;
    }

    public void pasteFigure() {
        walkThroughAllFigureCells(state.position, state.figure, (absPos, relPos) ->
                state.field[absPos.y()][absPos.x()] = state.figure[relPos.y()][relPos.x()]);
    }

    private boolean isFullRow(int[] fieldRow) {
        return Arrays.stream(fieldRow).noneMatch(value -> value == 0);
    }

    private void deleteFullRows() {
        for (int row = 0; row < state.field.length; row++) {
            if (isFullRow(state.field[row])) {
                deleteRow(row);
                calculateScore();
                notifyListeners();
            }
        }
    }

    private void calculateScore() {
        state.score += state.width;
        checkLevelUp();
        notifyListeners();
    }

    private void checkLevelUp() {
        if (state.score % 100 == 0) {
            state.level++;
            changePeriod();
        }
    }

    private void deleteRow(int row) {
        for (int r = row; r > 0; r--) {
            state.field[r] = Arrays.copyOf(state.field[r - 1], state.field[r - 1].length);
        }
        state.field[0] = new int[state.width];
    }

    public void gameOver() {
        state.gameOver = true;
        notifyListeners();
    }

    public void restartGame() {
        state.gameOver = false;
        state.score = 0;
        state.level = 1;
        state.period = TetrisModel.DEFAULT_PERIOD;

        for (int[] row : state.field) {
            Arrays.fill(row, 0);
        }

        initFigure();
        notifyListeners();
    }

    @Override
    public void pause() {
        state.paused = !state.paused;
        notifyListeners();
    }

    public void changePeriod() {
        state.period -= 50;
        notifyListeners();
        System.out.println(state.period);
    }

    private void notifyListeners() {
        listeners.forEach(listener -> listener.onChange(this));
    }

}
