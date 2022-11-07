package de.pianomanu.asterania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.KeyConfig;
import de.pianomanu.asterania.render.ShapeRendererUtils;
import de.pianomanu.asterania.render.button.ButtonRenderer;
import de.pianomanu.asterania.render.button.Buttons;
import de.pianomanu.asterania.render.text.TextInputBoxRenderer;
import de.pianomanu.asterania.savegame.Savegame;
import de.pianomanu.asterania.utils.AsteraniaInputProcessor;
import de.pianomanu.asterania.utils.WindowUtils;
import de.pianomanu.asterania.utils.fileutils.SaveGameUtils;
import de.pianomanu.asterania.utils.text.TextInputBox;

import java.util.logging.Logger;

public class CreateSaveScreen extends ScreenAdapter {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    private String input = "";
    private final TextInputBox box;

    public CreateSaveScreen() {
        AsteraniaMain.INSTANCE.reloadRenderers();

        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        Vector2 start = new Vector2(width / 10f, height * 4 / 5f);
        Vector2 end = new Vector2(width * 9 / 10f, height * 9 / 10f);
        this.box = new TextInputBox(start, end, "Create new Game");
    }

    @Override
    public void render(float delta) {
        checkForImportantChanges();
        checkForInput();

        ScreenUtils.clear(0, 0, 0, 1);
        drawCreateSaveScreen();
    }

    private void drawCreateSaveScreen() {
        drawBackground();
        drawInputBoxes();
        renderButtons();
    }

    private void drawBackground() {
        ShapeRendererUtils.getInstance().rect(20, 20, Gdx.graphics.getWidth() - 40, Gdx.graphics.getHeight() - 40, new Color(0.2f, 0.3f, 0.1f, 1));
    }

    private void drawInputBoxes() {
        this.input = AsteraniaInputProcessor.getTextInput().getInputString();
        TextInputBoxRenderer.getInstance().renderTextInputBox(this.box);
    }

    private void renderButtons() {
        ButtonRenderer.renderButtons(Buttons.LOAD_SAVES_MENU_BUTTONS);
    }

    private void checkForImportantChanges() {
        if (WindowUtils.windowSizeHasChanged()) {
            LOGGER.finest("Window was resized, updated window!");
            WindowUtils.changeScreen(this, new CreateSaveScreen());

        }
    }

    private void checkForInput() {
        if (Gdx.input.isKeyPressed(KeyConfig.EXIT_KEY_1) && Gdx.input.isKeyPressed(KeyConfig.EXIT_KEY_2)) {
            LOGGER.fine("Stopping the game...");
            Gdx.app.exit();
        }

        tryCreateNewGame();
        tryReturnToMainMenu();
    }

    private void tryCreateNewGame() {
        boolean enterPressed = Gdx.input.isKeyJustPressed(Input.Keys.ENTER);
        boolean buttonPressed = Buttons.START_GAME_BUTTON.isPressed();
        if ((enterPressed || buttonPressed) && !this.input.isEmpty()) {
            LOGGER.fine("Creating a new game...");
            changeToGameScreen();
        }
    }

    private void changeToGameScreen() {
        AsteraniaMain.INSTANCE.setCurrentActiveSavegame(Savegame.loadSavegame(this.input));
        SaveGameUtils.createNewGame(AsteraniaMain.INSTANCE.getCurrentActiveSavegame(), this.input);
        WindowUtils.changeScreen(this, new GameScreen());
    }

    private void tryReturnToMainMenu() {
        boolean escPressed = Gdx.input.isKeyPressed(Input.Keys.ESCAPE);
        boolean buttonPressed = Buttons.BACK_TO_MAIN_MENU_BUTTON.isPressed();
        if (escPressed || buttonPressed) {
            LOGGER.fine("Returning to main menu...");
            WindowUtils.changeScreen(this, new MainMenuScreen());
        }
    }
}
