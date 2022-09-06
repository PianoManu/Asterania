package de.pianomanu.asterania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.GameConfig;
import de.pianomanu.asterania.config.KeyConfig;
import de.pianomanu.asterania.render.ButtonRenderer;
import de.pianomanu.asterania.render.RendererUtils;
import de.pianomanu.asterania.render.button.Button;
import de.pianomanu.asterania.render.button.Buttons;
import de.pianomanu.asterania.render.text.TextRenderer;
import de.pianomanu.asterania.utils.DateUtils;
import de.pianomanu.asterania.utils.WindowUtils;
import de.pianomanu.asterania.utils.file_utils.SaveGameInfoUtils;
import de.pianomanu.asterania.utils.savegame.Savegame;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class LoadSavesScreen extends ScreenAdapter {
    private static final Logger LOGGER = AsteraniaMain.getLogger();
    private final SpriteBatch batch;

    private final List<Savegame> savegames = new ArrayList<>();
    private int saveFilePointer = 0;
    private Savegame tmpSavegame;

    public LoadSavesScreen() {
        this.batch = new SpriteBatch();
        AsteraniaMain.INSTANCE.reloadRenderers();
        loadAllExistingSaveFiles();

        if (savegames.size() > 0)
            this.tmpSavegame = savegames.get(0);
        else
            this.tmpSavegame = Savegame.loadSavegameInfo("tmp");
    }

    @Override
    public void render(float delta) {
        checkForImportantChanges();
        checkForInput();

        ScreenUtils.clear(0, 0, 0, 1);

        drawLoadSavesScreen();
    }

    private void drawLoadSavesScreen() {
        drawBackground();
        renderSelectedWorld();
        renderButtons();
        drawHovering();
    }

    private void drawBackground() {
        RendererUtils.getInstance().rect(20, 20, Gdx.graphics.getWidth() - 40, Gdx.graphics.getHeight() - 40, new Color(0.2f, 0.3f, 0.1f, 1));
    }

    private void checkForImportantChanges() {
        if (WindowUtils.windowSizeHasChanged()) {
            LOGGER.finest("Window was resized, updated window!");
            WindowUtils.changeScreen(this, new LoadSavesScreen());

        }
    }

    private void renderSelectedWorld() {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        int offset = 8;
        if (savegames.size() > 0) {

            Vector2 dim = TextRenderer.getTextDimensions(savegames.get(saveFilePointer).getName());
            RendererUtils.getInstance().rect(width / 10f, height * 4 / 5f, width * 4 / 5f, height / 10f, Color.OLIVE);
            RendererUtils.getInstance().rect(width / 10f, height / 5f, width * 4 / 5f, (float) (height * 5.5 / 10f), Color.OLIVE);
            RendererUtils.getInstance().rect((width - dim.x) / 2 - offset, (int) (height * 8.5 / 10) - dim.y / 2 - offset, dim.x + 2 * offset, dim.y + 2 * offset, Color.FOREST);

            this.tmpSavegame = savegames.get(saveFilePointer);
            SaveGameInfoUtils.loadInfo(this.tmpSavegame);

            TextRenderer.renderText(width / 2, (int) (height * 8.5 / 10), this.tmpSavegame.getName());

            TextRenderer.renderText(width / 8, (height * 7 / 10), "Date of creation:  " + this.tmpSavegame.getDateOfCreation(), false);
            TextRenderer.renderText(width / 8, (int) (height * 6.5 / 10), "Total playtime:  " + DateUtils.milliToHour(this.tmpSavegame.getTotalPlayTime()), false);
        } else {
            TextRenderer.renderText(width / 2, (int) (height * 8.5 / 10), "NO SAVEGAMES FOUND");
        }
    }

    private void renderButtons() {
        ButtonRenderer.renderButtons(batch, Buttons.LOAD_SAVES_MENU_BUTTONS);
    }

    private void drawHovering() {
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        RendererUtils.enableTransparency();
        RendererUtils.getInstance().begin();
        for (Button b :
                Buttons.LOAD_SAVES_MENU_BUTTONS) {
            if (mouseX >= b.getStart().x && mouseY >= b.getStart().y && mouseX <= b.getEnd().x && mouseY <= b.getEnd().y) {
                RendererUtils.getInstance().rectPlain(b.getStart().x, b.getStart().y, b.getFormat().x, b.getFormat().y, new Color(1, 1, 1, 0.2f));
            }
        }
        RendererUtils.getInstance().end();
        RendererUtils.disableTransparency();
    }

    private void checkForInput() {
        if (Gdx.input.isKeyPressed(KeyConfig.EXIT_KEY_1) && Gdx.input.isKeyPressed(KeyConfig.EXIT_KEY_2)) {
            LOGGER.fine("Stopping the game...");
            Gdx.app.exit();
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            checkIfButtonPressed();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            tryRotateThroughSaves();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            LOGGER.fine("Returning to main menu...");
            WindowUtils.changeScreen(this, new MainMenuScreen());
        }
    }

    private void checkIfButtonPressed() {
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        if (savegames.size() > 0) {
            if (mouseX >= Buttons.START_GAME_BUTTON.getStart().x && mouseY >= Buttons.START_GAME_BUTTON.getStart().y && mouseX <= Buttons.START_GAME_BUTTON.getEnd().x && mouseY <= Buttons.START_GAME_BUTTON.getEnd().y) {
                LOGGER.fine("Starting the game...");
                loadSavegame();
                WindowUtils.changeScreen(this, new GameScreen());
            }
        }
        if (mouseX >= Buttons.BACK_TO_MAIN_MENU_BUTTON.getStart().x && mouseY >= Buttons.BACK_TO_MAIN_MENU_BUTTON.getStart().y && mouseX <= Buttons.BACK_TO_MAIN_MENU_BUTTON.getEnd().x && mouseY <= Buttons.BACK_TO_MAIN_MENU_BUTTON.getEnd().y) {
            LOGGER.fine("Going back to main menu...");
            WindowUtils.changeScreen(this, new MainMenuScreen());
        }
    }

    private void loadSavegame() {
        //TODO fix unstable state
        //====================================================================
        AsteraniaMain.currentActiveSavegame = savegames.get(saveFilePointer);
        GameConfig.SAVEGAME_NAME = savegames.get(saveFilePointer).getName();
        GameConfig.reload();
        //====================================================================
        AsteraniaMain.currentActiveSavegame = Savegame.loadSavegame(savegames.get(saveFilePointer).getName());
        SaveGameInfoUtils.loadInfo();
    }

    private void tryRotateThroughSaves() {
        //no save files: no rotation possible
        //so check whether files are already there
        if (savegames.size() > 0) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                saveFilePointer++;
                if (saveFilePointer >= savegames.size())
                    saveFilePointer = 0;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                saveFilePointer--;
                if (saveFilePointer < 0)
                    saveFilePointer = savegames.size() - 1;
            }
        }
    }

    private void loadAllExistingSaveFiles() {
        File savesFolder = new File(GameConfig.SAVEGAME_PATH_OFFSET);
        if (savesFolder.isDirectory()) {
            File[] files = savesFolder.listFiles();
            if (files != null) {
                for (File f :
                        files) {
                    this.savegames.add(Savegame.loadSavegameInfo(f.getName()));
                }
            }
        } else {
            if (savesFolder.mkdir())
                LOGGER.severe(GameConfig.SAVEGAME_PATH_OFFSET + " must be a directory...");
        }
    }
}
