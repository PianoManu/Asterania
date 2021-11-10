package de.pianomanu.asterania.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.render.atlas.PlayerAtlas;
import de.pianomanu.asterania.utils.CoordinatesUtils;
import de.pianomanu.asterania.world.EntityCoordinates;
import de.pianomanu.asterania.world.TileCoordinates;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.tile.Tiles;

public class RenderWorld {
    private static ShapeRenderer gridRenderer = new ShapeRenderer();

    public static void reloadGridRenderer() {
        gridRenderer.dispose();
        gridRenderer = new ShapeRenderer();
    }

    public static void renderTerrain(World world, SpriteBatch batch) {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        int startRenderingX = world.getPlayer().getCharacterPos().toTileCoordinates().getX() - 3 * width / (2 * DisplayConfig.TILE_SIZE);
        int stopRenderingX = world.getPlayer().getCharacterPos().toTileCoordinates().getX() + 3 * width / (2 * DisplayConfig.TILE_SIZE);
        int startRenderingY = world.getPlayer().getCharacterPos().toTileCoordinates().getY() - 3 * height / (2 * DisplayConfig.TILE_SIZE);
        int stopRenderingY = world.getPlayer().getCharacterPos().toTileCoordinates().getY() + 3 * height / (2 * DisplayConfig.TILE_SIZE);
        TileCoordinates startRendering = new TileCoordinates(startRenderingX, startRenderingY);
        TileCoordinates stopRendering = new TileCoordinates(stopRenderingX, stopRenderingY);
        EntityCoordinates playerCoordinates = world.getPlayer().getCharacterPos();
        batch.begin();
        gridRenderer.begin(ShapeRenderer.ShapeType.Line);
        gridRenderer.setColor(0, 0, 0, 1);
        for (int x = startRendering.getX(); x < stopRendering.getX(); x++) {
            for (int y = startRendering.getY(); y < stopRendering.getY(); y++) {
                int xTile = (int) CoordinatesUtils.transformTileCoordinatesToPixels(new TileCoordinates(x, y), playerCoordinates).x;
                int yTile = (int) CoordinatesUtils.transformTileCoordinatesToPixels(new TileCoordinates(x, y), playerCoordinates).y;
                try {
                    batch.draw(world.getTile(x, y).getTexture(AsteraniaMain.assetManager.get(Atlases.TILE_ATLAS_LOCATION, TextureAtlas.class)), xTile, yTile, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE);
                } catch (ArrayIndexOutOfBoundsException e) {
                    batch.draw(Tiles.ROCK.getTexture(AsteraniaMain.assetManager.get(Atlases.TILE_ATLAS_LOCATION, TextureAtlas.class)), xTile, yTile, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE);
                } catch (NullPointerException ignored) {

                }
                if (DisplayConfig.showDebugInfo) {
                    gridRenderer.line(0, yTile, width, yTile);
                    gridRenderer.line(xTile, 0, xTile, height);
                }
            }
        }
        batch.end();
        gridRenderer.end();
    }

    public static void renderPlayer(World world, SpriteBatch batch) {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        Player player = world.getPlayer();
        TextureRegion playerTexture = AsteraniaMain.assetManager.get(Atlases.PLAYER_ATLAS_LOCATION, TextureAtlas.class).findRegion(PlayerAtlas.STANDING_FRONT);
        batch.begin();
        batch.draw(playerTexture, width / 2f - player.getCharacterSizeInPixels().x / 2f, height / 2f - player.getCharacterSizeInPixels().y / 2f, player.getCharacterSizeInPixels().x, player.getCharacterSizeInPixels().y);
        batch.end();
    }

    public static void renderHovering(World world, ShapeRenderer shapeRenderer) {
        EntityCoordinates playerPos = world.getPlayer().getCharacterPos();
        EntityCoordinates mousePos = CoordinatesUtils.pixelToEntityCoordinates(Gdx.input.getX(), Gdx.input.getY(), playerPos);
        int hoveringXStart = (int) Math.floor(mousePos.x);
        int hoveringYStart = (int) Math.floor(mousePos.y);
        EntityCoordinates hoveringStart = new EntityCoordinates(hoveringXStart, hoveringYStart);
        Vector2 startPixelPos = CoordinatesUtils.transformEntityCoordinatesToPixels(hoveringStart, playerPos);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 0.2f);
        shapeRenderer.rect(startPixelPos.x, startPixelPos.y, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

}
