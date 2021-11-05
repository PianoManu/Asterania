package de.pianomanu.asterania.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.render.Atlases;

public class LoadingScreen extends ScreenAdapter {
    public LoadingScreen() {
        loadAtlases();
    }

    private static void loadAtlases() {
        AsteraniaMain.assetManager.load(Atlases.TILE_ATLAS_LOCATION, TextureAtlas.class);
        AsteraniaMain.assetManager.load(Atlases.PLAYER_ATLAS_LOCATION, TextureAtlas.class);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        if (AsteraniaMain.assetManager.update()) {
            AsteraniaMain.INSTANCE.setScreen(new MainMenuScreen());
        }
    }

}
