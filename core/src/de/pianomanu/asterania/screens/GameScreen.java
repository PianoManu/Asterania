package de.pianomanu.asterania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.config.KeyConfig;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.lifecycle.GameLifeCycleUpdates;
import de.pianomanu.asterania.registry.GameRegistry;
import de.pianomanu.asterania.render.DebugScreenRenderer;
import de.pianomanu.asterania.render.WorldRenderer;
import de.pianomanu.asterania.render.ui.InventoryRenderer;
import de.pianomanu.asterania.render.ui.UIRenderer;
import de.pianomanu.asterania.utils.WindowUtils;
import de.pianomanu.asterania.utils.savegame.parsing.WorldWriter;
import de.pianomanu.asterania.world.World;

import java.util.logging.Logger;

public class GameScreen extends ScreenAdapter {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    World world;

    public GameScreen() {
        GameRegistry.setupRegistryGameLoading();
        AsteraniaMain.INSTANCE.reloadRenderers();
        this.resize(DisplayConfig.DISPLAY_WIDTH, DisplayConfig.DISPLAY_HEIGHT);
        this.world = AsteraniaMain.currentActiveSavegame.getHomeWorld();
        AsteraniaMain.currentActiveSavegame.resetStartTime();
        //TODO differentiate between new and old worlds
        Player player = AsteraniaMain.currentActiveSavegame.getCurrentActivePlayer();
        player.changeCurrentWorld(this.world, player.getPos().toTileCoordinates());
    }

    @Override
    public void render(float delta) {
        checkForImportantInput();
        checkForImportantChanges();

        Player player = AsteraniaMain.currentActiveSavegame.getCurrentActivePlayer();
        GameLifeCycleUpdates.update(player.getCurrentWorld(), player);

        ScreenUtils.clear(1, 0, 0, 1);
        WorldRenderer.renderAll(player.getCurrentWorld(), player, AsteraniaMain.INSTANCE.getBatch());
        UIRenderer.renderAll(player, AsteraniaMain.INSTANCE.getBatch());
        if (DisplayConfig.showDebugInfo && !InventoryRenderer.isInventoryOpen()) {
            DebugScreenRenderer.render(player.getCurrentWorld(), player, delta);
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
            WorldWriter.saveGameInfo();
            LOGGER.fine("Saved world data!");
            LOGGER.fine("Returning to main menu...");
            WindowUtils.changeScreen(this, new MainMenuScreen());
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
            WorldWriter.saveGameInfo();
            WindowUtils.changeScreen(this, new GameScreen());
        }
    }
}
