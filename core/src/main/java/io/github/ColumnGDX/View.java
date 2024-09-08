package io.github.ColumnGDX;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class View {

    private static final Color[] COLORS = {
        Color.BLACK, Color.CYAN, Color.BLUE, Color.RED, Color.GREEN,
        Color.YELLOW, Color.PINK, Color.MAGENTA, Color.WHITE
    };

    private static final int BOX_SIZE = 25;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font;

    public View(LibGDXGraphics graphics) {
        this.shapeRenderer = new ShapeRenderer();
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
    }

    public void render(int[][] field, Figure figure, int score, int level, boolean gameOver) {
        shapeRenderer.begin(ShapeType.Filled);

        // Draw the field
        for (int y = 1; y <= 15; y++) {
            for (int x = 1; x <= 7; x++) {
                drawBox(x, y, field[x][y]);
            }
        }

        // Draw the current figure
        drawFigure(figure);

        shapeRenderer.end();

        // Render the text (score, level, and game over message)
        batch.begin();
        font.setColor(Color.BLACK);
        font.draw(batch, "Score: " + score, 10, 20);
        font.draw(batch, "Level: " + level, 10, 40);
        if (gameOver) {
            font.setColor(Color.RED);
            font.draw(batch, "GAME OVER", 100, 200);
        }
        batch.end();
    }

    private void drawBox(int x, int y, int colorIndex) {
        Color color = COLORS[colorIndex];
        shapeRenderer.setColor(color);
        shapeRenderer.rect(
            2 + (x - 1) * BOX_SIZE,
            2 + (y - 1) * BOX_SIZE,
            BOX_SIZE, BOX_SIZE
        );

        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(
            2 + (x - 1) * BOX_SIZE,
            2 + (y - 1) * BOX_SIZE,
            BOX_SIZE, BOX_SIZE
        );
    }

    private void drawFigure(Figure figure) {
        if (figure != null) {
            int[] cells = figure.getCells();
            drawBox(figure.x, figure.y, cells[0]);
            drawBox(figure.x, figure.y + 1, cells[1]);
            drawBox(figure.x, figure.y + 2, cells[2]);
        }
    }
}
