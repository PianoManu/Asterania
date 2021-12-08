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
import de.pianomanu.asterania.utils.CoordinatesUtils;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.coordinates.EntityCoordinates;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;
import de.pianomanu.asterania.world.coordinates.WorldSectionCoordinates;
import de.pianomanu.asterania.world.tile.Tile;
import de.pianomanu.asterania.world.tile.Tiles;
import de.pianomanu.asterania.world.worldsections.WorldSection;

public class WorldRenderer {

    public static void renderAll(World world, SpriteBatch batch, ShapeRenderer shapeRenderer) {
        renderTerrain(world, batch);
        renderHovering(shapeRenderer);
        PlayerRenderer.render(world, batch);
    }

    private static void renderTerrain(World world, SpriteBatch batch) {
        EntityCoordinates playerCoordinates = AsteraniaMain.player.getPos();
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
                if (xTile >= -DisplayConfig.TILE_SIZE && xTile < Gdx.graphics.getWidth() && yTile >= -DisplayConfig.TILE_SIZE && yTile < Gdx.graphics.getHeight()) {
                    try {
                        Tile tile = section.getTileAbsoluteCoordinates(x, y);
                        batch.draw(tile.getTexture(AsteraniaMain.assetManager.get(Atlases.TILE_ATLAS_LOCATION, TextureAtlas.class)), xTile, yTile, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE);
                        //TODO better way to check for overlay
                        if (tile.equals(Tiles.SOIL_TILE) || tile.equals(Tiles.ROCK) || tile.equals(Tiles.WATER_TILE))
                            addOverlay(batch, world, section, x, y, xTile, yTile);
                    } catch (NullPointerException e) {
                        //Error trying to find the correct section: preGenerate all adjacent sections
                        world.preGenerateSurroundingWorldSections();
                    }
                    try {
                        Tile decoration = section.getDecorationLayerTileAbsoluteCoordinates(x, y);
                        if (decoration != null) {
                            batch.draw(decoration.getTexture(AsteraniaMain.assetManager.get(Atlases.TILE_ATLAS_LOCATION, TextureAtlas.class)), xTile, yTile, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE);
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

    private static void addOverlay(SpriteBatch batch, World world, WorldSection worldSection, int x, int y, int xTile, int yTile) {
        addOverlay(batch, world, worldSection, x, y, xTile, yTile, Tiles.SOIL_TILE, Tiles.GRASS);
        addOverlay(batch, world, worldSection, x, y, xTile, yTile, Tiles.ROCK, Tiles.GRASS);
        addOverlay(batch, world, worldSection, x, y, xTile, yTile, Tiles.WATER_TILE, Tiles.SOIL_TILE);
    }

    private static void addOverlay(SpriteBatch batch, World world, WorldSection worldSection, int x, int y, int xTile, int yTile, Tile tileAddOverlayOn, Tile overlayTile) {
        TileCoordinates tileCoordinates = new TileCoordinates(x, y);
        if (worldSection.getTileAbsoluteCoordinates(x, y).equals(tileAddOverlayOn)) {
            boolean leftIsGrass, rightIsGrass, upIsGrass, downIsGrass;
            TileCoordinates tmp = tileCoordinates.copy();
            if (!worldSection.isInsideWorldSectionBounds(x - 1, y)) {
                tmp.moveLeft(64);
                leftIsGrass = world.findSection(tmp).getTileAbsoluteCoordinates(x - 1, y).equals(overlayTile);
            } else {
                leftIsGrass = worldSection.getTileAbsoluteCoordinates(x - 1, y).equals(overlayTile);
            }

            tmp.copy(tileCoordinates);
            if (!worldSection.isInsideWorldSectionBounds(x + 1, y)) {
                tmp.moveRight(64);
                rightIsGrass = world.findSection(tmp).getTileAbsoluteCoordinates(x + 1, y).equals(overlayTile);
            } else
                rightIsGrass = worldSection.getTileAbsoluteCoordinates(x + 1, y).equals(overlayTile);

            tmp.copy(tileCoordinates);
            if (!worldSection.isInsideWorldSectionBounds(x, y + 1)) {
                tmp.moveUp(64);
                upIsGrass = world.findSection(tmp).getTileAbsoluteCoordinates(x, y + 1).equals(overlayTile);
            } else
                upIsGrass = worldSection.getTileAbsoluteCoordinates(x, y + 1).equals(overlayTile);

            tmp.copy(tileCoordinates);
            if (!worldSection.isInsideWorldSectionBounds(x, y - 1)) {
                tmp.moveDown(64);
                downIsGrass = world.findSection(tmp).getTileAbsoluteCoordinates(x, y - 1).equals(overlayTile);
            } else
                downIsGrass = worldSection.getTileAbsoluteCoordinates(x, y - 1).equals(overlayTile);

            TextureRegion overlayRegion = AsteraniaMain.assetManager.get(Atlases.TILE_OVERLAY_ATLAS_LOCATION, TextureAtlas.class).findRegion(overlayTile.getSaveFileString() + "_side");
            if (upIsGrass)
                batch.draw(overlayRegion, xTile, yTile, 0, 0, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE, 1, 1, 0);
            if (downIsGrass)
                batch.draw(overlayRegion, xTile + DisplayConfig.TILE_SIZE, yTile + DisplayConfig.TILE_SIZE, 0, 0, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE, 1, 1, 180);
            if (leftIsGrass)
                batch.draw(overlayRegion, xTile + DisplayConfig.TILE_SIZE, yTile, 0, 0, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE, 1, 1, 90);
            if (rightIsGrass)
                batch.draw(overlayRegion, xTile, yTile + DisplayConfig.TILE_SIZE, 0, 0, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE, 1, 1, 270);
        }
    }

    private static void renderHovering(ShapeRenderer shapeRenderer) {
        EntityCoordinates playerPos = AsteraniaMain.player.getPos();
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
