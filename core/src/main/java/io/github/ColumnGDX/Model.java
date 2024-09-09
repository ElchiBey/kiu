package io.github.ColumnGDX;

import java.util.ArrayList;
import java.util.List;

public class Model implements GameEventListener {
    static final int FigToDropForNextLevel = 33;
    static final int MaxLevel = 7;
    static final int Width = 7;
    static final int Depth = 15;
    public int level;
    public int removedCellsCounter;
    public int score;
    public int dropScore;
    public Figure Fig;
    public int[][] newField;
    public int[][] oldField;
    public boolean noChanges = true;
    public boolean gameOver = false;
    List<ModelListener> listeners = new ArrayList<>();
    static final int[][] SHIFTS = new int[][]{{0, -1, 0, 1}, {-1, 0, 1, 0}, {-1, -1, 1, 1}, {1, -1, -1, 1}};

    public Model() {
        initModel();
        initMembers();
        this.dropScore = 0;
        this.Fig = new Figure();
    }

    void checkIfFieldIsFull() {
        for (int i = 1; i <= 7; ++i) {
            if (this.newField[i][3] > 0) {
                this.fireGameOver();
                return;
            }
        }

        this.createNewFigure();
    }

    private void fireGameOver() {
        for (ModelListener listener : listeners) {
            listener.gameOver();
        }
    }

    void initModel() {
        this.newField = new int[9][17];
        this.oldField = new int[9][17];
    }

    void packField() {
        for (int i = 1; i <= 7; ++i) {
            int n = 15;

            int k;
            for (k = 15; k > 0; --k) {
                if (this.oldField[i][k] > 0) {
                    this.newField[i][n] = this.oldField[i][k];
                    --n;
                }
            }

            for (k = n; k > 0; --k) {
                this.newField[i][k] = 0;
            }
        }

        this.fireFieldUpdated();
    }

    private void fireFieldUpdated() {

        for (ModelListener modelListener : this.listeners) {
            modelListener.fieldUpdated(this.newField);
        }

    }

    void initMembers() {
        this.level = 0;
        this.score = 0;
        this.removedCellsCounter = 0;
    }

    void initMatrixes() {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 16; ++j) {
                this.newField[i][j] = 0;
                this.oldField[i][j] = 0;
            }
        }

    }

    void pasteFigure(Figure f) {
        int[] c = f.getCells();
        this.newField[f.x][f.y] = c[0];
        this.newField[f.x][f.y + 1] = c[1];
        this.newField[f.x][f.y + 2] = c[2];
        this.fireFieldUpdated();
    }

    boolean mayFigureSlideDown() {
        return this.Fig.y < 13 && this.newField[this.Fig.x][this.Fig.y + 3] == 0;
    }

    void testField() {
        this.copyField();

        for (int i = 1; i <= 15; ++i) {
            for (int j = 1; j <= 7; ++j) {
                if (this.newField[j][i] > 0) {
                    int[][] var3 = SHIFTS;
                    int var4 = var3.length;

                    for (int[] s : var3) {
                        if (this.checkNeighbours(j + s[0], i + s[1], j + s[2], i + s[3], i, j)) {

                            for (ModelListener modelListener : this.listeners) {
                                modelListener.foundNeighboursAt(j + s[0], i + s[1], j + s[2], i + s[3], i, j);
                            }
                        }
                    }
                }
            }
        }

    }

    private void copyField() {
        for (int i = 1; i <= 15; ++i) {
            for (int j = 1; j <= 7; ++j) {
                this.oldField[j][i] = this.newField[j][i];
            }
        }

    }

    public void trySlideDown() {
        if (this.mayFigureSlideDown()) {
            ++this.Fig.y;
            this.fireFigureMovedEvent(this.Fig.x, this.Fig.y - 1);
        } else {
            this.processFigureCantMoveDown();
        }

    }

    public void drop() {
        if (this.mayFigureSlideDown()) {
            ++this.Fig.y;
            this.fireFigureMovedEvent(this.Fig.x, this.Fig.y - 1);
        } else {
            this.processFigureCantMoveDown();
        }
    }

    public void gameOver() {
        gameOver = true;
        this.fireGameOver();
    }

    public void addListener(ModelListener listener) {
        this.listeners.add(listener);
    }

    boolean areNeighbours(int a, int b, int c, int d, int i, int j) {
        return this.newField[j][i] == this.newField[a][b] && this.newField[j][i] == this.newField[c][d];
    }

    boolean checkNeighbours(int a, int b, int c, int d, int i, int j) {
        if (!this.areNeighbours(a, b, c, d, i, j)) {
            return false;
        } else {
            this.oldField[a][b] = 0;
            this.oldField[j][i] = 0;
            this.oldField[c][d] = 0;
            this.noChanges = false;
            this.score += (this.level + 1) * 10;
            ++this.removedCellsCounter;
            return true;
        }
    }

    void changeScore() {
        this.score += this.dropScore;
        for (ModelListener listener : listeners) {
            listener.scoreHasChanged(score);
        }
    }

    private void changeLevel(int change) {
        this.level += change;
        for (ModelListener listener : listeners) {
            listener.levelHasChanged(level);
        }
    }

    void decreaseLevel() {
        this.changeLevel(-1);
    }

    void increaseLevel() {
        this.changeLevel(1);
    }

    void removeSimilarNeighboringCells() {
        this.noChanges = true;
        this.testField();
        if (!this.noChanges) {
            this.packField();
            this.changeScore();
            if (this.removedCellsCounter >= 33) {
                this.removedCellsCounter = 0;
                if (this.level < 7) {
                    this.increaseLevel();
                }

            }
        }
    }

    public void tryMoveLeft() {
        int oldX = this.Fig.x;
        int oldY = this.Fig.y;
        if (this.Fig.x > 1 && this.newField[this.Fig.x - 1][this.Fig.y + 2] == 0) {
            --this.Fig.x;
            this.fireFigureMovedEvent(oldX, oldY);
        }

    }

    public void tryMoveRight() {
        int oldX = this.Fig.x;
        int oldY = this.Fig.y;
        if (oldX < 7 && this.newField[oldX + 1][oldY + 2] == 0) {
            ++this.Fig.x;
            this.fireFigureMovedEvent(oldX, oldY);
        }

    }

    public void shiftUp() {
        this.Fig.rotateFigureCellsLeft();
        this.fireUpdateFigure();
    }

    public void shiftDown() {
        this.Fig.rotateFigureCellsRight();
        this.fireUpdateFigure();
    }

    public void fireFigureMovedEvent(int oldX, int oldY) {
        for (ModelListener listener : listeners) {
            listener.figureMovedFrom(oldX, oldY);
        }
    }

    public void fireUpdateFigure() {
        for (ModelListener listener : listeners) {
            listener.figureUpdated(Fig);
        }
    }

    void removeSimilarCellsRepeatedly() {
        do {
            this.removeSimilarNeighboringCells();
        } while (!this.noChanges);

    }

    void createNewFigure() {
        this.Fig = new Figure();
        this.fireUpdateFigure();
    }

    void processFigureCantMoveDown() {
        this.dropScore = 0;
        this.pasteFigure(this.Fig);
        this.removeSimilarCellsRepeatedly();
        this.checkIfFieldIsFull();
    }

    public void update(float delta) {

    }
}

