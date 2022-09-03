package de.pianomanu.asterania.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.utils.CoordinatesUtils;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.coordinates.EntityCoordinates;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;
import de.pianomanu.asterania.world.coordinates.WorldSectionCoordinates;
import de.pianomanu.asterania.world.tile.LayerType;
import de.pianomanu.asterania.world.tile.Tile;
import de.pianomanu.asterania.world.tile.Tiles;
import de.pianomanu.asterania.world.worldsections.WorldSection;

import java.util.ArrayList;
import java.util.List;

public class WorldRenderer {

    public static void renderAll(World world, Player player, SpriteBatch batch, ShapeRenderer shapeRenderer) {
        renderTerrain(world, player, batch);
        renderHovering(shapeRenderer);
        PlayerRenderer.render(world, batch);
    }

    private static void renderTerrain(World world, Player player, SpriteBatch batch) {
        EntityCoordinates playerCoordinates = player.getPos();
        EntityCoordinates bottomLeftEntityCoordinates = new EntityCoordinates(playerCoordinates.x - 32, playerCoordinates.y - 32);
        EntityCoordinates topRightEntityCoordinates = new EntityCoordinates(playerCoordinates.x + 32, playerCoordinates.y + 32);
        WorldSectionCoordinates bottomLeftWorldSectionCoordinates = bottomLeftEntityCoordinates.toWorldSectionCoordinates();
        WorldSectionCoordinates topRightWorldSectionCoordinates = topRightEntityCoordinates.toWorldSectionCoordinates();

        batch.begin();
        TileCoordinates bottomLeftTile = bottomLeftWorldSectionCoordinates.startToTileCoordinates();
        TileCoordinates topRightTile = topRightWorldSectionCoordinates.endToTileCoordinates();
        for (int x = bottomLeftTile.getX(); x < topRightTile.getX(); x++) {
            for (int y = bottomLeftTile.getY(); y < topRightTile.getY(); y++) {
                int xTile = (int) CoordinatesUtils.transformTileCoordinatesToPixels(new TileCoordinates(x, y), playerCoordinates).x;
                int yTile = (int) CoordinatesUtils.transformTileCoordinatesToPixels(new TileCoordinates(x, y), playerCoordinates).y;
                TileCoordinates tileCoordinates = new TileCoordinates(x, y);
                WorldSection section = world.findSection(tileCoordinates);
                if (isTileVisibleOnScreen(xTile, yTile)) {
                    try {
                        Tile tile = section.getTileAbsoluteCoordinates(x, y);
                        Tile decoration = section.getDecorationLayerTileAbsoluteCoordinates(x, y);
                        if (tile != null) {
                            renderTile(tile, xTile, yTile, x, y, batch);
                            //TODO better way to check for overlay
                            if (shouldHaveOverlay(tile))
                                addOverlay(batch, world, section, x, y, xTile, yTile);
                        }
                        if (decoration != null) {
                            renderTile(decoration, xTile, yTile, x, y, batch);
                        }

                    } catch (NullPointerException e) {
                        //Error trying to find the correct section: preGenerate all adjacent sections
                        world.preGenerateSurroundingWorldSections();
                    }
                }
            }
        }

        batch.end();
    }

    private static boolean isTileVisibleOnScreen(int xTile, int yTile) {
        return xTile >= -DisplayConfig.TILE_SIZE && xTile < Gdx.graphics.getWidth() && yTile >= -DisplayConfig.TILE_SIZE && yTile < Gdx.graphics.getHeight();
    }

    private static void renderTile(Tile tile, int xTile, int yTile, int x, int y, SpriteBatch batch) {
        if (tile.getTileType() == LayerType.BACKGROUND) {
            batch.draw(tile.getTexture(AsteraniaMain.assetManager.get(Atlases.TILE_ATLAS_LOCATION, TextureAtlas.class)), xTile, yTile, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE);
        }
        if (tile.getTileType() == LayerType.DECORATION) {
            DecorationLayerRenderer.addDecorations(batch, tile, xTile, yTile, x, y);
        }
    }

    private static boolean shouldHaveOverlay(Tile tile) {
        List<Tile> tilesWithOverlays = new ArrayList<>();
        tilesWithOverlays.add(Tiles.SOIL_TILE);
        tilesWithOverlays.add(Tiles.ROCK);
        tilesWithOverlays.add(Tiles.WATER_TILE);
        return tilesWithOverlays.contains(tile);
    }

    private static void addOverlay(SpriteBatch batch, World world, WorldSection worldSection, int x, int y, int xTile, int yTile) {
        OverlayRenderer.addOverlay(batch, world, worldSection, x, y, xTile, yTile, Tiles.SOIL_TILE, Tiles.GRASS);
        OverlayRenderer.addOverlay(batch, world, worldSection, x, y, xTile, yTile, Tiles.ROCK, Tiles.GRASS);
        //OverlayRenderer.addOverlay(batch, world, worldSection, x, y, xTile, yTile, Tiles.WATER_TILE, Tiles.SOIL_TILE);

        OverlayRenderer.addStoneCoastOverlay(batch, world, worldSection, x, y, xTile, yTile);
    }

    private static void renderHovering(ShapeRenderer shapeRenderer) {
        Player player = AsteraniaMain.player;
        EntityCoordinates playerPos = player.getPos();
        EntityCoordinates mousePos = CoordinatesUtils.pixelToEntityCoordinates(Gdx.input.getX(), Gdx.input.getY(), playerPos);
        boolean isInReach = player.isInReach(mousePos.toTileCoordinates());
        int hoveringXStart = (int) Math.floor(mousePos.x);
        int hoveringYStart = (int) Math.floor(mousePos.y);
        EntityCoordinates hoveringStart = new EntityCoordinates(hoveringXStart, hoveringYStart);
        Vector2 startPixelPos = CoordinatesUtils.transformEntityCoordinatesToPixels(hoveringStart, playerPos);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if (isInReach)
            shapeRenderer.setColor(0.6f, 1, 0.6f, 0.2f);
        else
            shapeRenderer.setColor(1, 1, 1, 0.2f);
        shapeRenderer.rect(startPixelPos.x, startPixelPos.y, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        if (isInReach) {
            shapeRenderer.setColor(0f, 0.5f, 0f, 1f);
        } else {
            shapeRenderer.setColor(1f, 1f, 1f, 0.2f);
        }

        shapeRenderer.rect(startPixelPos.x, startPixelPos.y, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE);
        shapeRenderer.rect(startPixelPos.x + 1, startPixelPos.y + 1, DisplayConfig.TILE_SIZE - 2, DisplayConfig.TILE_SIZE - 2);
        shapeRenderer.rect(startPixelPos.x + 2, startPixelPos.y + 2, DisplayConfig.TILE_SIZE - 4, DisplayConfig.TILE_SIZE - 4);
        shapeRenderer.rect(startPixelPos.x + 3, startPixelPos.y + 3, DisplayConfig.TILE_SIZE - 6, DisplayConfig.TILE_SIZE - 6);
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

}
