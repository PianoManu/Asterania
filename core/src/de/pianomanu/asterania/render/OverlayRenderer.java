package de.pianomanu.asterania.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;
import de.pianomanu.asterania.world.direction.Direction;
import de.pianomanu.asterania.world.tile.Tile;
import de.pianomanu.asterania.world.tile.Tiles;

public class OverlayRenderer {
    public static void addOverlay(SpriteBatch batch, World world, int x, int y, int xTile, int yTile, Tile tileAddOverlayOn, Tile overlayTile) {
        if (positionIsTile(world, new TileCoordinates(x, y), tileAddOverlayOn)) {
            boolean leftIsGrass = neighborIsTile(world, overlayTile, x, y, Direction.LEFT);
            boolean rightIsGrass = neighborIsTile(world, overlayTile, x, y, Direction.RIGHT);
            boolean upIsGrass = neighborIsTile(world, overlayTile, x, y, Direction.UP);
            boolean downIsGrass = neighborIsTile(world, overlayTile, x, y, Direction.DOWN);


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

    private static boolean positionIsTile(World world, TileCoordinates position, Tile tile) {
        return world.findSection(position).getTileAbsoluteCoordinates(position).equals(tile);
    }

    private static boolean neighborIsTile(World world, Tile tile, int x, int y, Direction direction) {
        TileCoordinates neighborTileCoordinates = switch (direction) {
            case RIGHT -> new TileCoordinates(x + 1, y);
            case LEFT -> new TileCoordinates(x - 1, y);
            case UP -> new TileCoordinates(x, y + 1);
            case DOWN -> new TileCoordinates(x, y - 1);
        };
        return positionIsTile(world, neighborTileCoordinates, tile);
    }

    public static void addStoneCoastOverlay(SpriteBatch batch, World world, int x, int y, int xTile, int yTile) {
        //pseudo-random selection of stone coast texture using x and y position
        //x*3, y*5+1: prevent patterns
        int textureNumber = (((x * 3 % 4) + (y * 5 + 1 % 4)) % 4) + 4; //add 4 for intervall [1, 4]

        boolean leftIsWater = neighborIsTile(world, Tiles.WATER_TILE, x, y, Direction.LEFT);
        boolean rightIsWater = neighborIsTile(world, Tiles.WATER_TILE, x, y, Direction.RIGHT);
        boolean upIsWater = neighborIsTile(world, Tiles.WATER_TILE, x, y, Direction.UP);
        boolean downIsWater = neighborIsTile(world, Tiles.WATER_TILE, x, y, Direction.DOWN);

        TextureRegion overlayRegion = AsteraniaMain.assetManager.get(Atlases.TILE_OVERLAY_ATLAS_LOCATION, TextureAtlas.class).findRegion("water_stone" + textureNumber);

        //tile is water: check surrounding tiles, if not water -> add overlay
        if (positionIsTile(world, new TileCoordinates(x, y), Tiles.WATER_TILE)) {

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
