package de.pianomanu.asterania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.config.KeyConfig;
import de.pianomanu.asterania.render.text.TextRenderer;

import java.util.Locale;
import java.util.logging.Logger;

public class MainMenuScreen extends ScreenAdapter {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    private ShapeRenderer shapeRenderer;

    public MainMenuScreen() {
        LOGGER.fine("Starting the main menu screen...");
        this.resize(DisplayConfig.DISPLAY_WIDTH, DisplayConfig.DISPLAY_HEIGHT);
        this.shapeRenderer = new ShapeRenderer();
        LOGGER.fine("Started the main menu screen!");
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(KeyConfig.EXIT_KEY_1) && Gdx.input.isKeyPressed(KeyConfig.EXIT_KEY_2)) {
            LOGGER.fine("Stopping the game...");
            Gdx.app.exit();
        }
        if (Gdx.input.isKeyJustPressed(KeyConfig.START)) {
            LOGGER.fine("Starting the game...");
            this.dispose();
            AsteraniaMain.INSTANCE.setScreen(new GameScreen());
        }
        ScreenUtils.clear(0, 0, 0, 1);

        this.drawMainMenuScreen();
    }

    @Override
    public void dispose() {
    }

    @Override
    public void hide() {
        this.dispose();
    }

    private void drawMainMenuScreen() {
        this.drawBackground();
        this.drawText();
    }

    private void drawBackground() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.2f, 0.3f, 0.1f, 1);
        shapeRenderer.rect(20, 20, Gdx.graphics.getWidth() - 40, Gdx.graphics.getHeight() - 40);
        shapeRenderer.end();
    }

    private void drawText() {
        TextRenderer.renderText(40, Gdx.graphics.getHeight() - 40, "Main Menu");
        TextRenderer.renderText(40, Gdx.graphics.getHeight() - 80, "Press " + Input.Keys.toString(KeyConfig.START).toUpperCase(Locale.ROOT) + " to Start the game");
        TextRenderer.renderText(40, Gdx.graphics.getHeight() - 120, "Press " + Input.Keys.toString(KeyConfig.EXIT_KEY_1).toUpperCase(Locale.ROOT) + " + " + Input.Keys.toString(KeyConfig.EXIT_KEY_2).toUpperCase(Locale.ROOT) + " to Exit the game");
    }
}
