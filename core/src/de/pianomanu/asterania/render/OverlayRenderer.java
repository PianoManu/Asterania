package de.pianomanu.asterania.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;
import de.pianomanu.asterania.world.tile.Tile;
import de.pianomanu.asterania.world.tile.Tiles;
import de.pianomanu.asterania.world.worldsections.WorldSection;

public class OverlayRenderer {
    public static void addOverlay(SpriteBatch batch, World world, WorldSection worldSection, int x, int y, int xTile, int yTile, Tile tileAddOverlayOn, Tile overlayTile) {
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

    public static void addStoneCoastOverlay(SpriteBatch batch, World world, WorldSection worldSection, int x, int y, int xTile, int yTile) {
        TileCoordinates tileCoordinates = new TileCoordinates(x, y);
        //pseudo-random selection of stone coast texture using x and y position
        //x*3, y*5+1: prevent patterns
        int textureNumber = (((x * 3 % 4) + (y * 5 + 1 % 4)) % 4) + 1; //add 1 for intervall [1, 4]

        boolean leftIsWater, rightIsWater, upIsWater, downIsWater;
        TileCoordinates tmp = tileCoordinates.copy();
        if (!worldSection.isInsideWorldSectionBounds(x - 1, y)) {
            tmp.moveLeft(64);
            leftIsWater = world.findSection(tmp).getTileAbsoluteCoordinates(x - 1, y).equals(Tiles.WATER_TILE);
        } else {
            leftIsWater = worldSection.getTileAbsoluteCoordinates(x - 1, y).equals(Tiles.WATER_TILE);
        }

        tmp.copy(tileCoordinates);
        if (!worldSection.isInsideWorldSectionBounds(x + 1, y)) {
            tmp.moveRight(64);
            rightIsWater = world.findSection(tmp).getTileAbsoluteCoordinates(x + 1, y).equals(Tiles.WATER_TILE);
        } else
            rightIsWater = worldSection.getTileAbsoluteCoordinates(x + 1, y).equals(Tiles.WATER_TILE);

        tmp.copy(tileCoordinates);
        if (!worldSection.isInsideWorldSectionBounds(x, y + 1)) {
            tmp.moveUp(64);
            upIsWater = world.findSection(tmp).getTileAbsoluteCoordinates(x, y + 1).equals(Tiles.WATER_TILE);
        } else
            upIsWater = worldSection.getTileAbsoluteCoordinates(x, y + 1).equals(Tiles.WATER_TILE);

        tmp.copy(tileCoordinates);
        if (!worldSection.isInsideWorldSectionBounds(x, y - 1)) {
            tmp.moveDown(64);
            downIsWater = world.findSection(tmp).getTileAbsoluteCoordinates(x, y - 1).equals(Tiles.WATER_TILE);
        } else
            downIsWater = worldSection.getTileAbsoluteCoordinates(x, y - 1).equals(Tiles.WATER_TILE);

        TextureRegion overlayRegion = AsteraniaMain.assetManager.get(Atlases.TILE_OVERLAY_ATLAS_LOCATION, TextureAtlas.class).findRegion("water_stone" + textureNumber);

        //tile is water: check surrounding tiles, if not water -> add overlay
        if (worldSection.getTileAbsoluteCoordinates(x, y).equals(Tiles.WATER_TILE)) {

            if (!upIsWater)
                batch.draw(overlayRegion, xTile, yTile, 0, 0, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE, 1, 1, 0);
            if (!downIsWater)
                batch.draw(overlayRegion, xTile + DisplayConfig.TILE_SIZE, yTile + DisplayConfig.TILE_SIZE, 0, 0, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE, 1, 1, 180);
            if (!leftIsWater)
                batch.draw(overlayRegion, xTile + DisplayConfig.TILE_SIZE, yTile, 0, 0, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE, 1, 1, 90);
            if (!rightIsWater)
                batch.draw(overlayRegion, xTile, yTile + DisplayConfig.TILE_SIZE, 0, 0, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE, 1, 1, 270);
        } else {
            //tile is not water, check surrounding tiles, if water -> add overlay
            if (upIsWater)
                batch.draw(overlayRegion, xTile, yTile, 0, 0, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE, 1, 1, 0);
            if (downIsWater)
                batch.draw(overlayRegion, xTile + DisplayConfig.TILE_SIZE, yTile + DisplayConfig.TILE_SIZE, 0, 0, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE, 1, 1, 180);
            if (leftIsWater)
                batch.draw(overlayRegion, xTile + DisplayConfig.TILE_SIZE, yTile, 0, 0, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE, 1, 1, 90);
            if (rightIsWater)
                batch.draw(overlayRegion, xTile, yTile + DisplayConfig.TILE_SIZE, 0, 0, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE, 1, 1, 270);
        }
    }
}
