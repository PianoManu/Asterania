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
import de.pianomanu.asterania.render.RenderWorld;
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
        GameLifeCycleUpdates.update(world, delta);
        //System.out.println(world.getPlayer().getCharacterPos().x+", "+world.getPlayer().getCharacterPos().y+ ",      "+1/delta);
        ScreenUtils.clear(1, 0, 0, 1);
        RenderWorld.renderTerrain(world, batch);
        RenderWorld.renderHovering(world, shapeRenderer);
        RenderWorld.renderPlayer(world, batch);
        if (DisplayConfig.showDebugInfo) {
            RenderWorld.renderDebugText(world, fps);
        }

        if (deltaCounter > 1) {
            deltaCounter--;
            fps = passCounter;
            passCounter = 0;
        }
        /*shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.NAVY);
        shapeRenderer.rect(player.getCharacterPos().x - player.getCharacterSize().x/2f, player.getCharacterPos().y - player.getCharacterSize().y/2f, player.getCharacterSize().x,player.getCharacterSize().y);
        shapeRenderer.end();*/
        if (Gdx.input.isKeyJustPressed(KeyConfig.ENABLE_DEBUG_INFO)) {
            DisplayConfig.showDebugInfo = !DisplayConfig.showDebugInfo;
        }

        //if (DisplayConfig.showDebugInfo)
        //    System.out.println(1 / delta);
    }

    @Override
    public void dispose() {
        //batch.dispose();
        //img.dispose();
    }

    @Override
    public void hide() {
        this.dispose();
    }
}
