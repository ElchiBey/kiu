package tetris;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Controller implements ModelListener, GameEventsListener {

    private TetrisModel model;
    private View view;
    private ScheduledExecutorService service;

    public Controller(TetrisModel model, View view) {
        this.model = model;
        this.view = view;
        model.addListener(this);
    }

    @Override
    public void onChange(TetrisModel model) {
        if (model.state.gameOver) {
            view.showGameOver();
        } else if (model.state.paused) {
            view.paused();
        } else {
            view.draw(model);
            view.showScore(model.state.score);
        }
    }

    public void setTask(ScheduledExecutorService service) {
        this.service = service;
    }

    @Override
    public void slideDown() {
        model.slideDown();
    }

    @Override
    public void moveLeft() {
        model.moveLeft();
    }

    @Override
    public void moveRight() {
        model.moveRight();
    }

    @Override
    public void rotate() {
        model.rotate();
    }

    @Override
    public void drop() {
        model.drop();
    }

    @Override
    public void pause() {
        model.pause();
    }

    @Override
    public void gameOver() {
        model.gameOver();
    }

    public void restartGame() {
        model.restartGame();
    }

    public void quitGame() {
        System.exit(0);
    }
}
