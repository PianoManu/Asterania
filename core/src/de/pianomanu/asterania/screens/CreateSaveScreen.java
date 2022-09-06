package de.pianomanu.asterania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.GameConfig;
import de.pianomanu.asterania.config.KeyConfig;
import de.pianomanu.asterania.render.RendererUtils;
import de.pianomanu.asterania.render.text.TextInputBoxRenderer;
import de.pianomanu.asterania.utils.AsteraniaInputProcessor;
import de.pianomanu.asterania.utils.TextInputBox;
import de.pianomanu.asterania.utils.WindowUtils;
import de.pianomanu.asterania.utils.file_utils.SaveGameUtils;
import de.pianomanu.asterania.utils.savegame.Savegame;

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
    }

    private void drawBackground() {
        RendererUtils.getInstance().rect(20, 20, Gdx.graphics.getWidth() - 40, Gdx.graphics.getHeight() - 40, new Color(0.2f, 0.3f, 0.1f, 1));
    }

    private void drawInputBoxes() {
        this.input = AsteraniaInputProcessor.getTextInput().getInputString();
        TextInputBoxRenderer.renderTextInputBox(this.box);
    }

    private void checkForImportantChanges() {
        if (WindowUtils.windowSizeHasChanged()) {
            LOGGER.finest("Window was resized, updated window!");
            this.dispose();
            AsteraniaMain.INSTANCE.setScreen(new CreateSaveScreen());

        }
    }

    private void checkForInput() {
        if (Gdx.input.isKeyPressed(KeyConfig.EXIT_KEY_1) && Gdx.input.isKeyPressed(KeyConfig.EXIT_KEY_2)) {
            LOGGER.fine("Stopping the game...");
            Gdx.app.exit();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && !this.input.isEmpty()) {
            LOGGER.fine("Creating a new game...");
            this.dispose();
            changeToGameScreen();
        }
    }

    private void changeToGameScreen() {
        AsteraniaMain.currentActiveSavegame = Savegame.loadSavegame(this.input);
        GameConfig.SAVEGAME_NAME = this.input;
        GameConfig.reload();
        SaveGameUtils.createNewGame(AsteraniaMain.currentActiveSavegame, this.input);
        AsteraniaMain.INSTANCE.setScreen(new GameScreen());
    }
}
