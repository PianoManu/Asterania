package de.pianomanu.asterania.world.worldsections;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.world.coordinates.EntityCoordinates;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;
import de.pianomanu.asterania.world.coordinates.WorldSectionCoordinates;
import de.pianomanu.asterania.world.gen.HomeWorldGeneration;
import de.pianomanu.asterania.world.tile.Tile;
import de.pianomanu.asterania.world.tile.Tiles;

import java.util.logging.Logger;

public class WorldSection {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    public static final int SECTION_SIZE = 64;

    public final WorldSectionCoordinates sectionPos;
    public final TileCoordinates start;
    public final TileCoordinates end;
    private final Tile[][] tiles;
    private final Tile[][] decorationLayer;
    private final WorldSectionSettings worldSectionSettings;
    private final float[][] data;

    public WorldSection(int xPos, int yPos, WorldSectionSettings settings) {
        this.sectionPos = new WorldSectionCoordinates(xPos, yPos);
        this.start = new TileCoordinates(xPos * SECTION_SIZE, yPos * SECTION_SIZE);
        this.end = new TileCoordinates(xPos * SECTION_SIZE + SECTION_SIZE - 1, yPos * SECTION_SIZE + SECTION_SIZE - 1);
        this.tiles = new Tile[SECTION_SIZE][SECTION_SIZE];
        //this.createRockTerrain();
        this.worldSectionSettings = settings;

        this.decorationLayer = new Tile[SECTION_SIZE][SECTION_SIZE];
        //TODO remove ladder later
        this.decorationLayer[8][7] = Tiles.MINE_LADDER;

        this.data = HomeWorldGeneration.main(xPos, yPos);
        this.generateTerrain();
    }

    public void generateTerrain() {
        for (int x = 0; x < SECTION_SIZE; x++) {
            for (int y = 0; y < SECTION_SIZE; y++) {
                float heightValue = data[x][y];
                if (heightValue < -0.3)
                    this.tiles[x][y] = Tiles.WATER_TILE;
                else if (heightValue < -0.1)
                    this.tiles[x][y] = Tiles.SOIL_TILE;
                else if (heightValue < 0.4)
                    this.tiles[x][y] = Tiles.GRASS;
                else
                    this.tiles[x][y] = Tiles.ROCK; //TODO
                //this.tiles[x][y] = this.worldSectionSettings.getValidTiles().get(0);
            }
        }
        WorldSectionGeneration.addDecorations(this);
    }

    //TODO createUndiscoveredTerrain
    /*private void createRockTerrain() {
        for (int x = 0; x < SECTION_SIZE; x++) {
            for (int y = 0; y < SECTION_SIZE; y++) {
                this.tiles[x][y] = Tiles.ROCK;
            }
        }
    }*/

    public Tile[][] getTiles() {
        return this.tiles;
    }

    public void setTiles(Tile[][] tiles) throws ArrayIndexOutOfBoundsException {
        if (tiles.length == SECTION_SIZE && tiles[0].length == SECTION_SIZE) {
            for (int x = 0; x < SECTION_SIZE; x++) {
                for (int y = 0; y < SECTION_SIZE; y++) {
                    this.tiles[x][y] = tiles[x][y];
                }
            }
        } else
            throw new ArrayIndexOutOfBoundsException("Input Tile Array must be " + SECTION_SIZE + "x" + SECTION_SIZE + ", but is " + tiles.length + "x" + tiles[0].length + "!");
    }

    public Tile getTileAbsoluteCoordinates(int x, int y) {
        //global coordinates
        int newX = x - this.start.getX();
        int newY = y - this.start.getY();
        return this.tiles[newX][newY];
    }

    public Tile getTileRelativeCoordinates(int x, int y) {
        //relative coordinates inside section
        //0<=x<=64, 0<=y<=64
        return this.tiles[x][y];
    }

    public Tile getTileAbsoluteCoordinates(EntityCoordinates entityCoordinates) {
        return getTileAbsoluteCoordinates((int) Math.floor(entityCoordinates.x), (int) Math.floor(entityCoordinates.y));
    }

    public Tile getTileRelativeCoordinates(EntityCoordinates entityCoordinates) {
        return getTileRelativeCoordinates((int) Math.floor(entityCoordinates.x), (int) Math.floor(entityCoordinates.y));
    }

    public Tile getTileAbsoluteCoordinates(TileCoordinates tileCoordinates) {
        try {
            return this.tiles[tileCoordinates.getX() - this.start.getX()][tileCoordinates.getY() - this.start.getY()];
        } catch (Exception e) {
            LOGGER.warning("Unable to find tile in WorldSection " + this.getSectionPos().toString() + ". Using default tile instead.");
            return this.worldSectionSettings.getValidTiles().get(0);
        }
    }

    public Tile getTileRelativeCoordinates(TileCoordinates tileCoordinates) {
        try {
            return this.tiles[tileCoordinates.getX()][tileCoordinates.getY()];
        } catch (Exception e) {
            LOGGER.warning("Unable to find tile in WorldSection " + this.getSectionPos().toString() + ". Using default tile instead.");
            return this.worldSectionSettings.getValidTiles().get(0);
        }
    }

    public void setTileAbsoluteCoordinates(int x, int y, Tile tile) {
        int newX = x - this.start.getX();
        int newY = y - this.start.getY();
        this.tiles[newX][newY] = tile;
    }

    public void setTileRelativeCoordinates(int x, int y, Tile tile) {
        this.tiles[x][y] = tile;
    }

    public void setTileAbsoluteCoordinates(EntityCoordinates entityCoordinates, Tile tile) {
        setTileAbsoluteCoordinates((int) Math.floor(entityCoordinates.x), (int) Math.floor(entityCoordinates.y), tile);
    }

    public void setTileRelativeCoordinates(EntityCoordinates entityCoordinates, Tile tile) {
        setTileRelativeCoordinates((int) Math.floor(entityCoordinates.x), (int) Math.floor(entityCoordinates.y), tile);
    }

    public void setTileAbsoluteCoordinates(TileCoordinates tileCoordinates, Tile tile) {
        setTileAbsoluteCoordinates(tileCoordinates.getX(), tileCoordinates.getY(), tile);
    }

    public void setTileRelativeCoordinates(TileCoordinates tileCoordinates, Tile tile) {
        setTileRelativeCoordinates(tileCoordinates.getX(), tileCoordinates.getY(), tile);
    }

    public TileCoordinates getStart() {
        return this.start;
    }

    public TileCoordinates getEnd() {
        return this.end;
    }

    public WorldSectionCoordinates getSectionPos() {
        return this.sectionPos;
    }

    public Tile[][] getDecorationLayerTiles() {
        return this.decorationLayer;
    }

    public void setDecorationLayerTile(Tile[][] tiles) throws ArrayIndexOutOfBoundsException {
        if (tiles.length == SECTION_SIZE && tiles[0].length == SECTION_SIZE) {
            for (int x = 0; x < SECTION_SIZE; x++) {
                for (int y = 0; y < SECTION_SIZE; y++) {
                    this.decorationLayer[x][y] = tiles[x][y];
                }
            }
        } else
            throw new ArrayIndexOutOfBoundsException("Input Tile Array must be " + SECTION_SIZE + "x" + SECTION_SIZE + ", but is " + tiles.length + "x" + tiles[0].length + "!");
    }

    public Tile getDecorationLayerTileAbsoluteCoordinates(int x, int y) {
        try {
            //global coordinates
            int newX = x - this.start.getX();
            int newY = y - this.start.getY();
            return this.decorationLayer[newX][newY];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Tile getDecorationLayerTileRelativeCoordinates(int x, int y) {
        try {
            //relative coordinates inside section
            //0<=x<=64, 0<=y<=64
            return this.decorationLayer[x][y];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Tile getDecorationLayerTileAbsoluteCoordinates(EntityCoordinates entityCoordinates) {
        return getDecorationLayerTileAbsoluteCoordinates((int) Math.floor(entityCoordinates.x), (int) Math.floor(entityCoordinates.y));
    }

    public Tile getDecorationLayerTileRelativeCoordinates(EntityCoordinates entityCoordinates) {
        return getDecorationLayerTileRelativeCoordinates((int) Math.floor(entityCoordinates.x), (int) Math.floor(entityCoordinates.y));
    }

    public Tile getDecorationLayerTileAbsoluteCoordinates(TileCoordinates tileCoordinates) {
        try {
            //global coordinates
            return this.decorationLayer[tileCoordinates.getX() - this.start.getX()][tileCoordinates.getY() - this.start.getY()];
        } catch (Exception e) {
            LOGGER.warning("Unable to find tile in WorldSection " + this.getSectionPos().toString() + ". Using default tile instead.");
            return this.worldSectionSettings.getValidTiles().get(0);
        }

    }

    public Tile getDecorationLayerTileRelativeCoordinates(TileCoordinates tileCoordinates) {
        try {
            //relative coordinates inside section
            return this.decorationLayer[tileCoordinates.getX()][tileCoordinates.getY()];
        } catch (Exception e) {
            LOGGER.warning("Unable to find tile in WorldSection " + this.getSectionPos().toString() + ". Using default tile instead.");
            return this.worldSectionSettings.getValidTiles().get(0);
        }
    }

    public void setDecorationLayerTileAbsoluteCoordinates(int x, int y, Tile tile) {
        int newX = x - this.start.getX();
        int newY = y - this.start.getY();
        this.decorationLayer[newX][newY] = tile;
    }

    public void setDecorationLayerTileRelativeCoordinates(int x, int y, Tile tile) {
        this.decorationLayer[x][y] = tile;
    }

    public void setDecorationLayerTileAbsoluteCoordinates(EntityCoordinates entityCoordinates, Tile tile) {
        setDecorationLayerTileAbsoluteCoordinates((int) Math.floor(entityCoordinates.x), (int) Math.floor(entityCoordinates.y), tile);
    }

    public void setDecorationLayerTileRelativeCoordinates(EntityCoordinates entityCoordinates, Tile tile) {
        setDecorationLayerTileRelativeCoordinates((int) Math.floor(entityCoordinates.x), (int) Math.floor(entityCoordinates.y), tile);
    }

    public void setDecorationLayerTileAbsoluteCoordinates(TileCoordinates tileCoordinates, Tile tile) {
        setDecorationLayerTileAbsoluteCoordinates(tileCoordinates.getX(), tileCoordinates.getY(), tile);
    }

    public void setDecorationLayerTileRelativeCoordinates(TileCoordinates tileCoordinates, Tile tile) {
        setDecorationLayerTileRelativeCoordinates(tileCoordinates.getX(), tileCoordinates.getY(), tile);
    }

    public boolean isInsideWorldSectionBounds(int x, int y) {
        return x >= this.start.getX() && x <= this.end.getX() && y >= this.start.getY() && y <= this.end.getY();
    }

    public boolean isInsideWorldSectionBounds(TileCoordinates tileCoordinates) {
        return this.isInsideWorldSectionBounds(tileCoordinates.getX(), tileCoordinates.getY());
    }
}
