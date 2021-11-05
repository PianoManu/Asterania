package de.pianomanu.asterania.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.config.KeyConfig;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.lifecycle.GameLifeCycleUpdates;
import de.pianomanu.asterania.render.RenderWorld;
import de.pianomanu.asterania.world.World;

public class GameScreen extends ScreenAdapter {
    SpriteBatch batch;
    TextureRegion character;
    //Texture grass;
    TextureAtlas tileAtlas;
    TextureAtlas playerAtlas;
    TextureRegion grass;
    ShapeRenderer shapeRenderer;
    World world = AsteraniaMain.world;
    Player player = AsteraniaMain.player;

    public GameScreen() {
        batch = new SpriteBatch();
        //grass = new Texture("textures\\tiles\\grass.png");
        tileAtlas = new TextureAtlas("textures\\tiles\\atlas\\tile_atlas.atlas");
        playerAtlas = new TextureAtlas("textures\\entities\\player\\atlas\\player_atlas.atlas");

        grass = tileAtlas.findRegion("grass");
        character = playerAtlas.findRegion("standing_front");

        shapeRenderer = new ShapeRenderer();
        this.resize(DisplayConfig.DISPLAY_WIDTH, DisplayConfig.DISPLAY_HEIGHT);
    }

    @Override
    public void render(float delta) {
        //batch.dispose();
        //batch = new SpriteBatch();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            this.dispose();
            AsteraniaMain.INSTANCE.setScreen(new MainMenuScreen());
        }
        GameLifeCycleUpdates.update(world, delta);
        //System.out.println(world.getPlayer().getCharacterPos().x+", "+world.getPlayer().getCharacterPos().y+ ",      "+1/delta);
        ScreenUtils.clear(1, 0, 0, 1);
        RenderWorld.renderTerrain(world, batch, tileAtlas);
        RenderWorld.renderHovering(world, shapeRenderer);
        RenderWorld.renderPlayer(world, batch, character);
        /*shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.NAVY);
        shapeRenderer.rect(player.getCharacterPos().x - player.getCharacterSize().x/2f, player.getCharacterPos().y - player.getCharacterSize().y/2f, player.getCharacterSize().x,player.getCharacterSize().y);
        shapeRenderer.end();*/
        if (Gdx.input.isKeyJustPressed(KeyConfig.ENABLE_DEBUG_INFO)) {
            DisplayConfig.showDebugInfo = !DisplayConfig.showDebugInfo;
        }

        if (DisplayConfig.showDebugInfo)
            System.out.println(1 / delta);
        System.out.println(batch.totalRenderCalls);
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
