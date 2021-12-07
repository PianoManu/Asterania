package de.pianomanu.asterania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.GameConfig;
import de.pianomanu.asterania.config.KeyConfig;
import de.pianomanu.asterania.render.ButtonRenderer;
import de.pianomanu.asterania.render.button.Button;
import de.pianomanu.asterania.render.button.Buttons;
import de.pianomanu.asterania.render.text.TextRenderer;
import de.pianomanu.asterania.utils.DateUtils;
import de.pianomanu.asterania.utils.WindowUtils;
import de.pianomanu.asterania.utils.file_utils.SaveGameInfoUtils;
import de.pianomanu.asterania.utils.file_utils.SaveGameUtils;
import de.pianomanu.asterania.utils.savegame.SaveFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class LoadSavesScreen extends ScreenAdapter {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;

    private List<SaveFile> saveFiles = new ArrayList<>();
    private int saveFilePointer = 0;
    private SaveFile tmpSaveFile;

    public LoadSavesScreen() {
        this.shapeRenderer = new ShapeRenderer();
        this.batch = new SpriteBatch();
        getAllExistingSaveFiles();

        if (saveFiles.size() > 0)
            this.tmpSaveFile = saveFiles.get(0);
        else
            this.tmpSaveFile = new SaveFile("tmp");
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
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.2f, 0.3f, 0.1f, 1);
        shapeRenderer.rect(20, 20, Gdx.graphics.getWidth() - 40, Gdx.graphics.getHeight() - 40);
        shapeRenderer.end();
    }

    private void checkForImportantChanges() {
        if (WindowUtils.windowSizeHasChanged()) {
            LOGGER.finest("Window was resized, updated window!");
            this.dispose();
            AsteraniaMain.INSTANCE.setScreen(new LoadSavesScreen());

        }
    }

    private void renderSelectedWorld() {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        int offset = 8;

        Vector2 dim = TextRenderer.getTextDimensions(saveFiles.get(saveFilePointer).getName());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.OLIVE);
        shapeRenderer.rect(width / 10f, height * 4 / 5f, width * 4 / 5f, height / 10f);
        shapeRenderer.rect(width / 10f, height / 5f, width * 4 / 5f, (float) (height * 5.5 / 10f));
        shapeRenderer.setColor(Color.FOREST);
        shapeRenderer.rect((width - dim.x) / 2 - offset, (int) (height * 8.5 / 10) - dim.y / 2 - offset, dim.x + 2 * offset, dim.y + 2 * offset);
        shapeRenderer.end();

        this.tmpSaveFile = saveFiles.get(saveFilePointer);
        SaveGameInfoUtils.loadInfo(this.tmpSaveFile, GameConfig.SAVEGAME_PATH_OFFSET + this.tmpSaveFile.getName());

        TextRenderer.renderText(width / 2, (int) (height * 8.5 / 10), this.tmpSaveFile.getName());

        TextRenderer.renderText(width / 8, (height * 7 / 10), "Date of creation:  " + this.tmpSaveFile.getDateOfCreation(), false);
        TextRenderer.renderText(width / 8, (int) (height * 6.5 / 10), "Total playtime:  " + DateUtils.milliToHour(this.tmpSaveFile.getTotalPlayTime()), false);
    }

    private void renderButtons() {
        ButtonRenderer.renderButtons(batch, Buttons.LOAD_SAVES_MENU_BUTTONS);
    }

    private void drawHovering() {
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 0.2f);
        for (Button b :
                Buttons.LOAD_SAVES_MENU_BUTTONS) {
            if (mouseX >= b.getStart().x && mouseY >= b.getStart().y && mouseX <= b.getEnd().x && mouseY <= b.getEnd().y) {
                shapeRenderer.rect(b.getStart().x, b.getStart().y, b.getFormat().x, b.getFormat().y);
            }
        }
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
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
    }

    private void checkIfButtonPressed() {
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        if (mouseX >= Buttons.START_GAME_BUTTON.getStart().x && mouseY >= Buttons.START_GAME_BUTTON.getStart().y && mouseX <= Buttons.START_GAME_BUTTON.getEnd().x && mouseY <= Buttons.START_GAME_BUTTON.getEnd().y) {
            LOGGER.fine("Starting the game...");
            this.dispose();
            AsteraniaMain.saveFile = saveFiles.get(saveFilePointer);
            GameConfig.SAVEGAME_NAME = saveFiles.get(saveFilePointer).getName();
            GameConfig.reload();
            SaveGameUtils.loadWorldsFromDirectory();
            SaveGameInfoUtils.loadInfo();
            AsteraniaMain.INSTANCE.setScreen(new GameScreen());
        }
        if (mouseX >= Buttons.BACK_TO_MAIN_MENU_BUTTON.getStart().x && mouseY >= Buttons.BACK_TO_MAIN_MENU_BUTTON.getStart().y && mouseX <= Buttons.BACK_TO_MAIN_MENU_BUTTON.getEnd().x && mouseY <= Buttons.BACK_TO_MAIN_MENU_BUTTON.getEnd().y) {
            LOGGER.fine("Going back to main menu...");
            this.dispose();
            AsteraniaMain.INSTANCE.setScreen(new MainMenuScreen());
        }
    }

    private void tryRotateThroughSaves() {
        //no save files: no rotation possible
        //so check whether files are already there
        if (saveFiles.size() > 0) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                saveFilePointer++;
                if (saveFilePointer >= saveFiles.size())
                    saveFilePointer = 0;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                saveFilePointer--;
                if (saveFilePointer < 0)
                    saveFilePointer = saveFiles.size() - 1;
            }
        }
    }

    private List<SaveFile> getAllExistingSaveFiles() {
        File savesFolder = new File(GameConfig.SAVEGAME_PATH_OFFSET);
        if (savesFolder.isDirectory()) {
            File[] files = savesFolder.listFiles();
            if (files != null) {
                for (File f :
                        files) {
                    this.saveFiles.add(new SaveFile(f.getName()));
                }
            }
        } else {
            LOGGER.severe(GameConfig.SAVEGAME_PATH_OFFSET + " must be a directory...");
            savesFolder.mkdir();
            return new ArrayList<>();
        }
        return this.saveFiles;
    }
}
