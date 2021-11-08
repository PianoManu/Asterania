package de.pianomanu.asterania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.config.KeyConfig;
import de.pianomanu.asterania.lifecycle.GameLifeCycleUpdates;
import de.pianomanu.asterania.render.DebugScreenRenderer;
import de.pianomanu.asterania.render.RenderWorld;
import de.pianomanu.asterania.utils.WindowUtils;
import de.pianomanu.asterania.world.World;

public class GameScreen extends ScreenAdapter {
    SpriteBatch batch;
    TextureAtlas tileAtlas;
    TextureAtlas playerAtlas;
    ShapeRenderer shapeRenderer;
    World world = AsteraniaMain.world;
    private float deltaCounter = 0;
    private int passCounter = 0;
    private int fps = 0;

    public GameScreen() {
        batch = new SpriteBatch();
        tileAtlas = AsteraniaMain.assetManager.get("textures/tiles/atlas/tile_atlas.atlas", TextureAtlas.class);//new TextureAtlas("textures/tiles/atlas/tile_atlas.atlas");
        playerAtlas = AsteraniaMain.assetManager.get("textures/entities/player/atlas/player_atlas.atlas", TextureAtlas.class);

        shapeRenderer = new ShapeRenderer();
        this.resize(DisplayConfig.DISPLAY_WIDTH, DisplayConfig.DISPLAY_HEIGHT);
    }

    @Override
    public void render(float delta) {
        passCounter++;
        deltaCounter += delta;
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            this.dispose();
            AsteraniaMain.INSTANCE.setScreen(new MainMenuScreen());
        }
        if (WindowUtils.windowSizeHasChanged()) {
            this.dispose();
            AsteraniaMain.INSTANCE.setScreen(new GameScreen());
        }
        GameLifeCycleUpdates.update(world, delta);

        ScreenUtils.clear(1, 0, 0, 1);
        RenderWorld.renderTerrain(world, batch);
        RenderWorld.renderHovering(world, shapeRenderer);
        RenderWorld.renderPlayer(world, batch);
        if (DisplayConfig.showDebugInfo) {
            DebugScreenRenderer.renderDebugText(world, fps);
            DebugScreenRenderer.renderHitbox(world, shapeRenderer);
        }

        if (deltaCounter > 1) {
            deltaCounter--;
            fps = passCounter;
            passCounter = 0;
        }

        if (Gdx.input.isKeyJustPressed(KeyConfig.ENABLE_DEBUG_INFO)) {
            DisplayConfig.showDebugInfo = !DisplayConfig.showDebugInfo;
        }
        if (Gdx.input.isKeyJustPressed(KeyConfig.ENABLE_FULLSCREEN)) {
            DisplayConfig.isFullscreen = !DisplayConfig.isFullscreen;
            DisplayConfig.setup();
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public void hide() {
        this.dispose();
    }
}
