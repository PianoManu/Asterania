package de.pianomanu.asterania.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.utils.CoordinatesUtils;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.coordinates.EntityCoordinates;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;
import de.pianomanu.asterania.world.coordinates.WorldSectionCoordinates;

public class WorldRenderer {

    public static void renderAll(World world, SpriteBatch batch, ShapeRenderer shapeRenderer) {
        renderTerrain(world, batch);
        renderHovering(world, shapeRenderer);
        PlayerRenderer.render(world, batch);
    }

    private static void renderTerrain(World world, SpriteBatch batch) {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        int startRenderingX = AsteraniaMain.player.getCharacterPos().toTileCoordinates().getX() - 3 * width / (2 * DisplayConfig.TILE_SIZE);
        int stopRenderingX = AsteraniaMain.player.getCharacterPos().toTileCoordinates().getX() + 3 * width / (2 * DisplayConfig.TILE_SIZE);
        int startRenderingY = AsteraniaMain.player.getCharacterPos().toTileCoordinates().getY() - 3 * height / (2 * DisplayConfig.TILE_SIZE);
        int stopRenderingY = AsteraniaMain.player.getCharacterPos().toTileCoordinates().getY() + 3 * height / (2 * DisplayConfig.TILE_SIZE);
        TileCoordinates startRendering = new TileCoordinates(startRenderingX, startRenderingY);
        TileCoordinates stopRendering = new TileCoordinates(stopRenderingX, stopRenderingY);
        EntityCoordinates playerCoordinates = AsteraniaMain.player.getCharacterPos();
        batch.begin();

        /*for (int x = startRendering.getX(); x < stopRendering.getX(); x++) {
            for (int y = startRendering.getY(); y < stopRendering.getY(); y++) {
                int xTile = (int) CoordinatesUtils.transformTileCoordinatesToPixels(new TileCoordinates(x, y), playerCoordinates).x;
                int yTile = (int) CoordinatesUtils.transformTileCoordinatesToPixels(new TileCoordinates(x, y), playerCoordinates).y;
                try {
                    batch.draw(world.findSection(AsteraniaMain.player.getCharacterPos()).getTile(x, y).getTexture(AsteraniaMain.assetManager.get(Atlases.TILE_ATLAS_LOCATION, TextureAtlas.class)), xTile, yTile, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE);
                } catch (ArrayIndexOutOfBoundsException e) {
                    batch.draw(Tiles.ROCK.getTexture(AsteraniaMain.assetManager.get(Atlases.TILE_ATLAS_LOCATION, TextureAtlas.class)), xTile, yTile, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE);
                } catch (NullPointerException ignored) {

                }
                if (DisplayConfig.showDebugInfo) {
                    gridRenderer.line(0, yTile, width, yTile);
                    gridRenderer.line(xTile, 0, xTile, height);
                }
            }
        }*/
        WorldSectionCoordinates centerSection = playerCoordinates.toWorldSectionCoordinates();
        WorldSectionCoordinates bottomleftSection = new WorldSectionCoordinates(centerSection.x - 1, centerSection.y - 1);
        WorldSectionCoordinates topRightSection = new WorldSectionCoordinates(centerSection.x + 1, centerSection.y + 1);
        TileCoordinates bottomLeftTile = bottomleftSection.startToTileCoordinates();
        TileCoordinates topRightTile = topRightSection.endToTileCoordinates();
        for (int x = bottomLeftTile.getX(); x < topRightTile.getX(); x++) {
            for (int y = bottomLeftTile.getY(); y < topRightTile.getY(); y++) {
                int xTile = (int) CoordinatesUtils.transformTileCoordinatesToPixels(new TileCoordinates(x, y), playerCoordinates).x;
                int yTile = (int) CoordinatesUtils.transformTileCoordinatesToPixels(new TileCoordinates(x, y), playerCoordinates).y;
                //try {
                if (x >= centerSection.startToTileCoordinates().getX() && x <= centerSection.endToTileCoordinates().getX() && y >= centerSection.startToTileCoordinates().getY() && y <= centerSection.endToTileCoordinates().getY())
                    batch.draw(world.findSection(AsteraniaMain.player.getCharacterPos()).getTile(x, y).getTexture(AsteraniaMain.assetManager.get(Atlases.TILE_ATLAS_LOCATION, TextureAtlas.class)), xTile, yTile, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE);
                else {
                    TileCoordinates tileCoordinates = new TileCoordinates(x, y);
                    try {
                        batch.draw(world.findSection(tileCoordinates).getTile(x, y).getTexture(AsteraniaMain.assetManager.get(Atlases.TILE_ATLAS_LOCATION, TextureAtlas.class)), xTile, yTile, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE);
                    } catch (NullPointerException e) {
                        //Error trying to find the correct section: preGenerate all adjacent sections
                        world.preGenerateSurroundingWorldSections();
                    }
                }
            }
        }

        batch.end();
    }

    private static void renderHovering(World world, ShapeRenderer shapeRenderer) {
        EntityCoordinates playerPos = AsteraniaMain.player.getCharacterPos();
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
