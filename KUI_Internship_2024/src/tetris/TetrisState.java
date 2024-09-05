package tetris;

public class TetrisState {
	public int width;
	public int height;
	public int[][] field;
	public int[][] figure;
	public Pair position;
	public boolean gameOver = false;
	public int score;
	public int level = 1;

	public TetrisState() {
	}
}