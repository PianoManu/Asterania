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
import de.pianomanu.asterania.config.LanguageConfig;
import de.pianomanu.asterania.render.ShapeRendererUtils;
import de.pianomanu.asterania.render.button.Button;
import de.pianomanu.asterania.render.button.ButtonRenderer;
import de.pianomanu.asterania.render.button.Buttons;
import de.pianomanu.asterania.render.text.TextRenderer;
import de.pianomanu.asterania.savegame.Savegame;
import de.pianomanu.asterania.utils.WindowUtils;
import de.pianomanu.asterania.utils.fileutils.SaveGameInfoUtils;
import de.pianomanu.asterania.utils.math.DateUtils;
import de.pianomanu.asterania.utils.text.language.LanguageFileUtils;

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
        ShapeRendererUtils.getInstance().rect(20, 20, Gdx.graphics.getWidth() - 40, Gdx.graphics.getHeight() - 40, new Color(0.2f, 0.3f, 0.1f, 1));
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

            Vector2 dim = TextRenderer.getInstance().getTextDimensions(savegames.get(saveFilePointer).getName());
            ShapeRendererUtils.getInstance().rect(width / 10f, height * 4 / 5f, width * 4 / 5f, height / 10f, Color.OLIVE);
            ShapeRendererUtils.getInstance().rect(width / 10f, height / 5f, width * 4 / 5f, (float) (height * 5.5 / 10f), Color.OLIVE);
            ShapeRendererUtils.getInstance().rect((width - dim.x) / 2 - offset, (int) (height * 8.5 / 10) - dim.y / 2 - offset, dim.x + 2 * offset, dim.y + 2 * offset, Color.FOREST);

            this.tmpSavegame = savegames.get(saveFilePointer);
            SaveGameInfoUtils.loadInfo(this.tmpSavegame);

            TextRenderer.getInstance().renderText(width / 2, (int) (height * 8.5 / 10), this.tmpSavegame.getName());

            TextRenderer.getInstance().renderText(width / 8, (height * 7 / 10), formattedText("creation_date", this.tmpSavegame.getDateOfCreation()), false);
            TextRenderer.getInstance().renderText(width / 8, (int) (height * 6.5 / 10), formattedText("playtime", DateUtils.milliToHour(this.tmpSavegame.getTotalPlayTime())), false);
        } else {
            TextRenderer.getInstance().renderText(width / 2, (int) (height * 8.5 / 10), formattedText("no_saves"));
        }
    }

    private void renderButtons() {
        ButtonRenderer.renderButtons(batch, Buttons.LOAD_SAVES_MENU_BUTTONS);
    }

    private void drawHovering() {
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        ShapeRendererUtils.enableTransparency();
        ShapeRendererUtils.getInstance().begin();
        for (Button b :
                Buttons.LOAD_SAVES_MENU_BUTTONS) {
            if (mouseX >= b.getStart().x && mouseY >= b.getStart().y && mouseX <= b.getEnd().x && mouseY <= b.getEnd().y) {
                ShapeRendererUtils.getInstance().rectPlain(b.getStart().x, b.getStart().y, b.getFormat().x, b.getFormat().y, new Color(1, 1, 1, 0.2f));
            }
        }
        ShapeRendererUtils.getInstance().end();
        ShapeRendererUtils.disableTransparency();
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
        AsteraniaMain.INSTANCE.setCurrentActiveSavegame(savegames.get(saveFilePointer));
        //====================================================================
        AsteraniaMain.INSTANCE.setCurrentActiveSavegame(Savegame.loadSavegame(savegames.get(saveFilePointer).getName()));
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

    private String formattedText(String key, String... parameters) {
        return String.format(LanguageFileUtils.getLanguageString(LanguageConfig.LOAD_SAVES + "." + key), (Object[]) parameters);
    }
}
