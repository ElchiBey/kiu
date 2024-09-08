package io.github.ColumnGDX;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;

public interface MyGraphics {

    // Initializes the graphics context
    void initialize(SpriteBatch batch);

    // Draws a texture at the given position
    void drawTexture(Texture texture, float x, float y);

    // Draws a rectangle (can be used for blocks, etc.)
    void drawRect(float x, float y, float width, float height, Color color);

    // Draws text at the given position
    void drawText(String text, float x, float y, Color color);

    // Clears the screen
    void clearScreen(Color color);
}
