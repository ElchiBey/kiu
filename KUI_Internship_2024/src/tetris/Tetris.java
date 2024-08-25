package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Tetris {

    static final Color[] COLORS = {Color.BLACK.darker(), Color.BLUE.darker(), Color.RED.darker(), Color.GREEN.darker(), Color.CYAN.darker(), Color.MAGENTA.darker(),
            Color.ORANGE.darker(), Color.YELLOW.darker()};

    public static void main(String[] args) {

        JFrame frame = new JFrame("Tetris");

        JPanel panel = new JPanel();

        panel.setPreferredSize(new Dimension(400, 700));

        frame.add(panel);

        frame.pack();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setVisible(true);

        Graphics2D graphics = (Graphics2D) panel.getGraphics();

        TetrisModel model = new TetrisModel(TetrisModel.DEFAULT_WIDTH, TetrisModel.DEFAULT_HEIGHT,
                TetrisModel.DEFAULT_COLORS_NUMBER);

        View view = new View((i, j, value) -> {
            graphics.setColor(COLORS[value]);
            graphics.fillRect(i, j, View.BOX_SIZE - 1, View.BOX_SIZE - 1);

//                View.showScore(graphics, model.state.score);
        });

        Controller controller = new Controller(model, view);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (model.state.gameOver) {
                    model.gameOver();
                    if (e.getKeyCode() == KeyEvent.VK_R) {
                        controller.restartGame();
                    } else if (e.getKeyCode() == KeyEvent.VK_Q) {
                        controller.quitGame();
                    }
                } else {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_LEFT:
                            controller.moveLeft();
                            break;
                        case KeyEvent.VK_RIGHT:
                            controller.moveRight();
                            break;
                        case KeyEvent.VK_UP:
                            controller.rotate();
                            break;
                        case KeyEvent.VK_DOWN:
                            controller.slideDown();
                            break;
                        case KeyEvent.VK_SPACE:
                            controller.drop();
                            break;
                        case KeyEvent.VK_P:
                            model.pause();
                    }
                }
            }
        });

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(controller::slideDown, 0, 1, TimeUnit.SECONDS);
    }

}
