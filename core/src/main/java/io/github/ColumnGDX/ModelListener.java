package io.github.ColumnGDX;

public interface ModelListener {
    void foundNeighboursAt(int var1, int var2, int var3, int var4, int var5, int var6);

    void fieldUpdated(int[][] var1);

    void scoreHasChanged(int var1);

    void levelHasChanged(int var1);

    void figureMovedFrom(int var1, int var2);

    void figureUpdated(Figure var1);

    void gameOver();

}
