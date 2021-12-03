package de.pianomanu.asterania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.config.GameConfig;
import de.pianomanu.asterania.registry.GameRegistry;
import de.pianomanu.asterania.render.Atlases;
import de.pianomanu.asterania.render.button.Buttons;
import de.pianomanu.asterania.utils.AsteraniaInputProcessor;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoadingScreen extends ScreenAdapter {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    public LoadingScreen() {
        loadConfigs();

        loadAtlases();

        DisplayConfig.setup();
        Buttons.setup();
        Gdx.input.setInputProcessor(new AsteraniaInputProcessor());

        GameRegistry.setupRegistry();
    }

    private static void loadAtlases() {
        LOGGER.log(Level.FINE, "Loading atlases...");
        AsteraniaMain.assetManager.load(Atlases.TILE_ATLAS_LOCATION, TextureAtlas.class);
        AsteraniaMain.assetManager.load(Atlases.PLAYER_ATLAS_LOCATION, TextureAtlas.class);
        AsteraniaMain.assetManager.load(Atlases.BUTTON_ATLAS_LOCATION, TextureAtlas.class);
        LOGGER.fine("Loaded atlases!");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        if (AsteraniaMain.assetManager.update()) {
            AsteraniaMain.INSTANCE.setScreen(new MainMenuScreen());
        }
    }

    private void loadConfigs() {
        GameConfig.load();
    }

}
