package io.github.kiu.ColumnGDX;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.ColumnGDX.Controller;
import io.github.ColumnGDX.LibGDXGraphics;
import io.github.ColumnGDX.Model;
import io.github.ColumnGDX.View;

public class ColumnGame extends Game {
    private SpriteBatch batch;
    private LibGDXGraphics graphics;
    private Controller controller;
    private Model model;
    private View view;

    @Override
    public void create() {
        batch = new SpriteBatch();
        graphics = new LibGDXGraphics();
        graphics.initialize(batch);

        model = new Model();
        view = new View(graphics);
        controller = new Controller(model);

        Gdx.input.setInputProcessor(controller);

        setScreen(new ColumnScreen(this, model, view, controller));
    }
}
