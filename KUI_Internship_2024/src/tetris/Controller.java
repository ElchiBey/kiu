package tetris;


public class Controller implements ModelListener, GameEventsListener {

    private TetrisModel model;
    private View view;

    public Controller(TetrisModel model, View view) {
        this.model = model;
        this.view = view;
        model.addListener(this);
    }

    @Override
    public void onChange(TetrisModel model) {
        view.draw(model);
    }

    @Override
    public void scoreChanged(TetrisModel model) {
        view.showScore(model.state.score);
    }

    @Override
    public void levelChanged(TetrisModel model) {
        view.showLevel(model.state.level);
    }

    @Override
    public void gameOver(TetrisModel model) {
        view.showGameOver(model.state.width, model.state.height);
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
