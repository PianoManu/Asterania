package de.pianomanu.asterania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.DisplayConfig;

public class MainMenuScreen extends ScreenAdapter {
    SpriteBatch batch;
    Texture img;

    public MainMenuScreen() {
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        this.resize(DisplayConfig.DISPLAY_WIDTH, DisplayConfig.DISPLAY_HEIGHT);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && Gdx.input.isKeyPressed(Input.Keys.W)) {
            Gdx.app.exit();
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            System.out.println("Click!");
            Gdx.input.setCursorCatched(true);
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            System.out.println("Click!");
            Gdx.input.setCursorCatched(false);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            //this.pause();
            this.dispose();
            AsteraniaMain.INSTANCE.setScreen(new GameScreen());
            //this.resume();
        }
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();
    }

    @Override
    public void dispose() {
        //batch.dispose();
        //img.dispose();
    }

    @Override
    public void hide() {
        this.dispose();
    }
}
