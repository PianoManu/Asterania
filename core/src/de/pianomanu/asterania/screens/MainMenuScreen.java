package de.pianomanu.asterania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.config.KeyConfig;
import de.pianomanu.asterania.render.ButtonRenderer;
import de.pianomanu.asterania.render.button.Buttons;
import de.pianomanu.asterania.render.text.TextRenderer;
import de.pianomanu.asterania.utils.WindowUtils;

import java.util.Locale;
import java.util.logging.Logger;

public class MainMenuScreen extends ScreenAdapter {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;

    public MainMenuScreen() {
        LOGGER.fine("Starting the main menu screen...");
        this.resize(DisplayConfig.DISPLAY_WIDTH, DisplayConfig.DISPLAY_HEIGHT);
        this.shapeRenderer = new ShapeRenderer();
        this.batch = new SpriteBatch();
        LOGGER.fine("Started the main menu screen!");
    }

    @Override
    public void render(float delta) {
        checkForImportantChanges();
        checkForInput();

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
        this.drawButtons();
        this.drawText();
        this.drawHovering();
    }

    private void drawHovering() {
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 0.2f);
        if (mouseX >= Buttons.EXIT_BUTTON.getStart().x && mouseY >= Buttons.EXIT_BUTTON.getStart().y && mouseX <= Buttons.EXIT_BUTTON.getEnd().x && mouseY <= Buttons.EXIT_BUTTON.getEnd().y) {
            shapeRenderer.rect(Buttons.EXIT_BUTTON.getStart().x, Buttons.EXIT_BUTTON.getStart().y, Buttons.EXIT_BUTTON.getFormat().x, Buttons.EXIT_BUTTON.getFormat().y);
        }

        if (mouseX >= Buttons.START_BUTTON.getStart().x && mouseY >= Buttons.START_BUTTON.getStart().y && mouseX <= Buttons.START_BUTTON.getEnd().x && mouseY <= Buttons.START_BUTTON.getEnd().y) {
            shapeRenderer.rect(Buttons.START_BUTTON.getStart().x, Buttons.START_BUTTON.getStart().y, Buttons.START_BUTTON.getFormat().x, Buttons.START_BUTTON.getFormat().y);
        }
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void drawBackground() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.2f, 0.3f, 0.1f, 1);
        shapeRenderer.rect(20, 20, Gdx.graphics.getWidth() - 40, Gdx.graphics.getHeight() - 40);
        shapeRenderer.end();
    }

    private void drawButtons() {
        ButtonRenderer.renderMainMenuButtons(batch);
    }

    private void drawText() {
        TextRenderer.renderText(40, Gdx.graphics.getHeight() - 40, "Main Menu");
        TextRenderer.renderText(40, Gdx.graphics.getHeight() - 80, "Press " + Input.Keys.toString(KeyConfig.START).toUpperCase(Locale.ROOT) + " to Start the game");
        TextRenderer.renderText(40, Gdx.graphics.getHeight() - 120, "Press " + Input.Keys.toString(KeyConfig.EXIT_KEY_1).toUpperCase(Locale.ROOT) + " + " + Input.Keys.toString(KeyConfig.EXIT_KEY_2).toUpperCase(Locale.ROOT) + " to Exit the game");
    }

    private void checkForImportantChanges() {
        if (WindowUtils.windowSizeHasChanged()) {
            LOGGER.finest("Window was resized, updated window!");
            this.dispose();
            AsteraniaMain.INSTANCE.setScreen(new MainMenuScreen());

        }
    }

    private void checkForInput() {
        if (Gdx.input.isKeyPressed(KeyConfig.EXIT_KEY_1) && Gdx.input.isKeyPressed(KeyConfig.EXIT_KEY_2)) {
            LOGGER.fine("Stopping the game...");
            Gdx.app.exit();
        }
        if (Gdx.input.isKeyJustPressed(KeyConfig.START)) {
            LOGGER.fine("Starting the game...");
            this.dispose();
            AsteraniaMain.INSTANCE.setScreen(new GameScreen());
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            checkIfButtonPressed();
        }
    }

    private void checkIfButtonPressed() {
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        if (mouseX >= Buttons.EXIT_BUTTON.getStart().x && mouseY >= Buttons.EXIT_BUTTON.getStart().y && mouseX <= Buttons.EXIT_BUTTON.getEnd().x && mouseY <= Buttons.EXIT_BUTTON.getEnd().y) {
            LOGGER.fine("Stopping the game...");
            Gdx.app.exit();
        }

        if (mouseX >= Buttons.START_BUTTON.getStart().x && mouseY >= Buttons.START_BUTTON.getStart().y && mouseX <= Buttons.START_BUTTON.getEnd().x && mouseY <= Buttons.START_BUTTON.getEnd().y) {
            LOGGER.fine("Starting the game...");
            this.dispose();
            AsteraniaMain.INSTANCE.setScreen(new GameScreen());
        }
    }
}
