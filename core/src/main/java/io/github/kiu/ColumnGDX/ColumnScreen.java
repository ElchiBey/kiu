package io.github.kiu.ColumnGDX;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import io.github.ColumnGDX.Controller;
import io.github.ColumnGDX.Model;
import io.github.ColumnGDX.View;

public class ColumnScreen implements Screen {
    private ColumnGame game;
    private Model model;
    private View view;
    private Controller controller;

    public ColumnScreen(ColumnGame game, Model model, View view, Controller controller) {
        this.game = game;
        this.model = model;
        this.view = view;
        this.controller = controller;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        model.update(delta);
        view.render(model.newField, model.Fig, model.score, model.level, model.gameOver);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}

