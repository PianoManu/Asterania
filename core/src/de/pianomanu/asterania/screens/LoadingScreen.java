package de.pianomanu.asterania.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.render.Atlases;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoadingScreen extends ScreenAdapter {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    public LoadingScreen() {
        loadAtlases();

        DisplayConfig.setup();
    }

    private static void loadAtlases() {
        LOGGER.log(Level.FINE, "Loading atlases...");
        AsteraniaMain.assetManager.load(Atlases.TILE_ATLAS_LOCATION, TextureAtlas.class);
        AsteraniaMain.assetManager.load(Atlases.PLAYER_ATLAS_LOCATION, TextureAtlas.class);
        LOGGER.fine("Loaded atlases!");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        if (AsteraniaMain.assetManager.update()) {
            AsteraniaMain.INSTANCE.setScreen(new MainMenuScreen());
        }
    }

}
