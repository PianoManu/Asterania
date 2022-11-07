package de.pianomanu.asterania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.config.KeyConfig;
import de.pianomanu.asterania.config.LanguageConfig;
import de.pianomanu.asterania.render.ShapeRendererUtils;
import de.pianomanu.asterania.render.button.ButtonRenderer;
import de.pianomanu.asterania.render.button.Buttons;
import de.pianomanu.asterania.render.text.TextRenderer;
import de.pianomanu.asterania.utils.WindowUtils;
import de.pianomanu.asterania.utils.text.language.LanguageFileUtils;

import java.util.Locale;
import java.util.logging.Logger;

public class MainMenuScreen extends ScreenAdapter {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    public MainMenuScreen() {
        LOGGER.fine("Starting the main menu screen...");
        AsteraniaMain.INSTANCE.reloadRenderers();
        this.resize(DisplayConfig.DISPLAY_WIDTH, DisplayConfig.DISPLAY_HEIGHT);
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
    }

    private void drawBackground() {
        ShapeRendererUtils.getInstance().rect(20, 20, Gdx.graphics.getWidth() - 40, Gdx.graphics.getHeight() - 40, new Color(0.2f, 0.3f, 0.1f, 1));
    }

    private void drawButtons() {
        ButtonRenderer.renderButtons(Buttons.MAIN_MENU_BUTTONS);
    }

    private void drawText() {
        TextRenderer.getInstance().renderText(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 40, formattedText("title"));
        TextRenderer.getInstance().renderText(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 80, formattedText("start_info", Input.Keys.toString(KeyConfig.START).toUpperCase(Locale.ROOT)));
        TextRenderer.getInstance().renderText(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 120, formattedText("exit_info", Input.Keys.toString(KeyConfig.EXIT_KEY_1).toUpperCase(Locale.ROOT), Input.Keys.toString(KeyConfig.EXIT_KEY_2).toUpperCase(Locale.ROOT)));
    }

    private void checkForImportantChanges() {
        if (WindowUtils.windowSizeHasChanged()) {
            LOGGER.finest("Window was resized, updated window!");
            WindowUtils.changeScreen(this, new MainMenuScreen());
        }
    }

    private void checkForInput() {
        if (Gdx.input.isKeyPressed(KeyConfig.EXIT_KEY_1) && Gdx.input.isKeyPressed(KeyConfig.EXIT_KEY_2)) {
            LOGGER.fine("Stopping the game...");
            Gdx.app.exit();
        }
        checkIfButtonPressed();
    }

    private void checkIfButtonPressed() {

        if (Buttons.EXIT_BUTTON.isPressed()) {
            LOGGER.fine("Stopping the game...");
            Gdx.app.exit();
        }

        if (Buttons.CREATE_NEW_GAME_BUTTON.isPressed()) {
            LOGGER.fine("Opening game creation...");
            WindowUtils.changeScreen(this, new CreateSaveScreen());
        }

        if (Buttons.LOAD_BUTTON.isPressed()) {
            LOGGER.fine("Loading all existing worlds...");
            WindowUtils.changeScreen(this, new LoadSavesScreen());
        }
    }

    private String formattedText(String key, String... parameters) {
        return String.format(LanguageFileUtils.getLanguageString(LanguageConfig.MAIN_MENU + "." + key), (Object[]) parameters);
    }
}
