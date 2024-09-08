package io.github.ColumnGDX;

import java.util.Random;

class Figure {
    static Random r = new Random();
    int x = 4;
    int y = 1;
    private int[] c = new int[3];

    static void rotateArraysElementsLeft(int[] c) {
        int i = c[0];
        c[0] = c[1];
        c[1] = c[2];
        c[2] = i;
    }

    static void rotateArraysElementsRight(int[] c) {
        int i = c[0];
        c[0] = c[2];
        c[2] = c[1];
        c[1] = i;
    }

    Figure() {
        this.x = 4;
        this.y = 1;
        this.c[0] = Math.abs(r.nextInt()) % 7 + 1;
        this.c[1] = Math.abs(r.nextInt()) % 7 + 1;
        this.c[2] = Math.abs(r.nextInt()) % 7 + 1;
    }

    void rotateFigureCellsRight() {
        int i = this.c[0];
        this.c[0] = this.c[2];
        this.c[2] = this.c[1];
        this.c[1] = i;
    }

    void rotateFigureCellsLeft() {
        rotateArraysElementsLeft(this.c);
    }

    public int[] getCells() {
        return this.c;
    }
}

