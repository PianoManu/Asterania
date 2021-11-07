package de.pianomanu.asterania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.DisplayConfig;

public class MainMenuScreen extends ScreenAdapter {

    public MainMenuScreen() {
        this.resize(DisplayConfig.DISPLAY_WIDTH, DisplayConfig.DISPLAY_HEIGHT);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && Gdx.input.isKeyPressed(Input.Keys.W)) {
            Gdx.app.exit();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            this.dispose();
            AsteraniaMain.INSTANCE.setScreen(new GameScreen());
        }
        ScreenUtils.clear(0, 0, 0, 1);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void hide() {
        this.dispose();
    }
}
