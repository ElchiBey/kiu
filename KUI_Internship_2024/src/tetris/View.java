package tetris;

import java.awt.*;

public class View {

    static final int BOX_SIZE = 30;
    static final int ORIGIN = 50;
    private Graphics graphics;

    public View(Graphics graphics) {
        this.graphics = graphics;
    }

    public void showScore(int score) {
        graphics.setColor(Color.white);
        graphics.fillRect(50, 10, 150, 30);

        graphics.setColor(Color.black);
        graphics.drawString("Total Score: " + score, 50, 30);
    }

    public void showLevel(int level) {
        graphics.setColor(Color.white);
        graphics.fillRect(150, 10, 200, 30);

        graphics.setColor(Color.black);
        graphics.drawString("Level: " + level, 300, 30);
    }


    public void showGameOver(int panelWidth, int panelHeight) {
        graphics.setColor(Color.white); // Set the color to the background color to clear the area
        graphics.fillRect(ORIGIN, ORIGIN + 100, panelWidth, 150); // Clear the area

        graphics.setColor(Color.red.darker()); // Set the color for the text
        graphics.setFont(new Font("Arial", Font.BOLD, 40));

        String gameOverText = "GAME OVER";
        FontMetrics fontMetrics = graphics.getFontMetrics();
        int gameOverWidth = fontMetrics.stringWidth(gameOverText);
        int gameOverHeight = fontMetrics.getHeight();

        graphics.drawString(gameOverText, (panelWidth - gameOverWidth) / 2, (panelHeight + gameOverHeight) / 2);
    }


    public void draw(TetrisModel model) {
        showScore(model.state.score);
        showLevel(model.state.level);
        drawData(new DrawDataParameter(model.state.field, 0, 0, true));
        drawData(new DrawDataParameter(model.state.figure, model.state.position.y(), model.state.position.x(), false));
    }

    private void drawData(DrawDataParameter parameterObject) {
        for (int r = 0; r < parameterObject.fs.length; r++) {
            for (int c = 0; c < parameterObject.fs[r].length; c++) {
                if (!parameterObject.drawBackground && parameterObject.fs[r][c] == 0)
                    continue;
                drawBox(r + parameterObject.row, c + parameterObject.col, parameterObject.fs[r][c]);
            }
        }
    }

    private void drawBox(int row, int col, int value) {
        graphics.drawBoxAt(ORIGIN + col * BOX_SIZE, ORIGIN + row * BOX_SIZE, value);
    }
}
