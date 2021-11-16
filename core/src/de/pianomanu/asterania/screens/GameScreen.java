package de.pianomanu.asterania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.config.KeyConfig;
import de.pianomanu.asterania.lifecycle.GameLifeCycleUpdates;
import de.pianomanu.asterania.render.DebugScreenRenderer;
import de.pianomanu.asterania.render.WorldRenderer;
import de.pianomanu.asterania.render.ui.UIRenderer;
import de.pianomanu.asterania.utils.WindowUtils;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.worldsections.WorldSectionParser;
import de.pianomanu.asterania.world.worldsections.WorldWriter;

import java.util.logging.Logger;

public class GameScreen extends ScreenAdapter {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    SpriteBatch batch;
    //TextureAtlas tileAtlas;
    //TextureAtlas playerAtlas;
    ShapeRenderer shapeRenderer;
    World world = AsteraniaMain.world;

    public GameScreen() {
        batch = new SpriteBatch();
        //tileAtlas = AsteraniaMain.assetManager.get("textures/tiles/atlas/tile_atlas.atlas", TextureAtlas.class);//new TextureAtlas("textures/tiles/atlas/tile_atlas.atlas");
        //playerAtlas = AsteraniaMain.assetManager.get("textures/entities/player/atlas/player_atlas.atlas", TextureAtlas.class);

        shapeRenderer = new ShapeRenderer();
        this.resize(DisplayConfig.DISPLAY_WIDTH, DisplayConfig.DISPLAY_HEIGHT);
    }

    @Override
    public void render(float delta) {
        checkForImportantInput();
        checkForImportantChanges();

        GameLifeCycleUpdates.update(world, delta);

        ScreenUtils.clear(1, 0, 0, 1);
        WorldRenderer.renderAll(world, batch, shapeRenderer);
        UIRenderer.renderAll(world, shapeRenderer);
        if (DisplayConfig.showDebugInfo) {
            DebugScreenRenderer.render(world, shapeRenderer, delta);
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public void hide() {
        this.dispose();
    }

    private void checkForImportantInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            LOGGER.fine("Saving world data...");
            WorldWriter.saveWorldContent(WorldSectionParser.createWSString(world));
            LOGGER.fine("Saved world data!");
            this.dispose();
            AsteraniaMain.INSTANCE.setScreen(new MainMenuScreen());
        }
        if (Gdx.input.isKeyJustPressed(KeyConfig.ENABLE_DEBUG_INFO)) {
            DisplayConfig.showDebugInfo = !DisplayConfig.showDebugInfo;
            LOGGER.finest("Toggled debug info!");
        }
        if (Gdx.input.isKeyJustPressed(KeyConfig.ENABLE_FULLSCREEN)) {
            DisplayConfig.isFullscreen = !DisplayConfig.isFullscreen;
            DisplayConfig.setup();
            LOGGER.finest("Toggled fullscreen settings!");
        }
    }

    private void checkForImportantChanges() {
        if (WindowUtils.windowSizeHasChanged()) {
            LOGGER.finest("Window was resized, updated window!");
            this.dispose();
            AsteraniaMain.INSTANCE.setScreen(new GameScreen());
        }
    }
}
