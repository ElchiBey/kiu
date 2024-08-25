package tetris;

public interface GameEventsListener {
	
	void slideDown();
	void moveLeft();
	void moveRight();
	void rotate();
	void drop();
	void scoreChanged(int score);
	void pause();
	void levelChanged(int level);
	void gameOver();
	// TODO: other necessary methods...
}
