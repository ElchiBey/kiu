package tetris;


import java.awt.*;

public interface Graphics {
	void drawBoxAt(int i, int j, int value);

    void fillRect(int i, int i1, int i2, int i3);

    void drawString(String s, int i, int i1);

    void setColor(Color color);

    void setFont(Font arial);

}
