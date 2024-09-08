package io.github.ColumnGDX;

public interface GameEventListener {
    void tryMoveLeft();

    void tryMoveRight();

    void shiftUp();

    void shiftDown();

    void trySlideDown();

    void drop();

    void gameOver();
}
