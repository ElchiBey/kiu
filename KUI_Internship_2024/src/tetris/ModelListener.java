package tetris;

public interface ModelListener {
	
	void onChange(TetrisModel model);

	void scoreChanged(TetrisModel model);

	void levelChanged(TetrisModel model);

	void gameOver(TetrisModel model);

}
