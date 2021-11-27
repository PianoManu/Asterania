package de.pianomanu.asterania.world.worldsections;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.world.coordinates.EntityCoordinates;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;
import de.pianomanu.asterania.world.coordinates.WorldSectionCoordinates;
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

    public WorldSection(int xPos, int yPos, WorldSectionSettings settings) {
        this.sectionPos = new WorldSectionCoordinates(xPos, yPos);
        this.start = new TileCoordinates(xPos * SECTION_SIZE, yPos * SECTION_SIZE);
        this.end = new TileCoordinates(xPos * SECTION_SIZE + SECTION_SIZE - 1, yPos * SECTION_SIZE + SECTION_SIZE - 1);
        this.tiles = new Tile[SECTION_SIZE][SECTION_SIZE];
        this.createRockTerrain();
        this.worldSectionSettings = settings;

        this.decorationLayer = new Tile[SECTION_SIZE][SECTION_SIZE];
        this.decorationLayer[8][8] = Tiles.MINE_LADDER;
    }

    public void createTerrain() {
        for (int x = 0; x < SECTION_SIZE; x++) {
            for (int y = 0; y < SECTION_SIZE; y++) {
                this.tiles[x][y] = this.worldSectionSettings.getValidTiles().get(0);
            }
        }
    }

    //TODO createUndiscoveredTerrain
    private void createRockTerrain() {
        for (int x = 0; x < SECTION_SIZE; x++) {
            for (int y = 0; y < SECTION_SIZE; y++) {
                this.tiles[x][y] = Tiles.ROCK;
            }
        }
    }

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

    public Tile getTile(int x, int y) {
        try {
            //relative coordinates inside section
            //0<=x<=64, 0<=y<=64
            return this.tiles[x][y];
        } catch (ArrayIndexOutOfBoundsException e) {
            //global coordinates
            int newX = x - this.start.getX();
            int newY = y - this.start.getY();
            return this.tiles[newX][newY];
        }
    }

    public Tile getTile(EntityCoordinates entityCoordinates) {
        return getTile((int) Math.floor(entityCoordinates.x), (int) Math.floor(entityCoordinates.y));
    }

    public Tile getTile(TileCoordinates tileCoordinates) {
        try {
            //relative coordinates inside section
            return this.tiles[tileCoordinates.getX()][tileCoordinates.getY()];
        } catch (ArrayIndexOutOfBoundsException e) {
            //global coordinates
            return this.tiles[tileCoordinates.getX() - this.start.getX()][tileCoordinates.getY() - this.start.getY()];
            //return Tiles.ROCK;
        } catch (Exception e) {
            LOGGER.warning("Unable to find tile in WorldSection " + this.getSectionPos().toString() + ". Using default tile instead.");
            return this.worldSectionSettings.getValidTiles().get(0);
        }

    }

    public void setTile(int x, int y, Tile tile) {
        try {
            this.tiles[x][y] = tile;
        } catch (ArrayIndexOutOfBoundsException e) {
            int newX = x - this.start.getX();
            int newY = y - this.start.getY();
            this.tiles[newX][newY] = tile;
        }
    }

    public void setTile(EntityCoordinates entityCoordinates, Tile tile) {
        setTile((int) Math.floor(entityCoordinates.x), (int) Math.floor(entityCoordinates.y), tile);
    }

    public void setTile(TileCoordinates tileCoordinates, Tile tile) {
        setTile(tileCoordinates.getX(), tileCoordinates.getY(), tile);
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

    public Tile[][] getDecorationLayerTile() {
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

    public Tile getDecorationLayerTile(int x, int y) {
        try {
            //relative coordinates inside section
            //0<=x<=64, 0<=y<=64
            return this.decorationLayer[x][y];
        } catch (ArrayIndexOutOfBoundsException e) {
            try {
                //global coordinates
                int newX = x - this.start.getX();
                int newY = y - this.start.getY();
                return this.decorationLayer[newX][newY];
            } catch (ArrayIndexOutOfBoundsException e2) {
                e2.printStackTrace();
                return null;
            }
        }
    }

    public Tile getDecorationLayerTile(EntityCoordinates entityCoordinates) {
        return getDecorationLayerTile((int) Math.floor(entityCoordinates.x), (int) Math.floor(entityCoordinates.y));
    }

    public Tile getDecorationLayerTile(TileCoordinates tileCoordinates) {
        try {
            //relative coordinates inside section
            return this.decorationLayer[tileCoordinates.getX()][tileCoordinates.getY()];
        } catch (ArrayIndexOutOfBoundsException e) {
            //global coordinates
            return this.decorationLayer[tileCoordinates.getX() - this.start.getX()][tileCoordinates.getY() - this.start.getY()];
            //return Tiles.ROCK;
        } catch (Exception e) {
            LOGGER.warning("Unable to find tile in WorldSection " + this.getSectionPos().toString() + ". Using default tile instead.");
            return this.worldSectionSettings.getValidTiles().get(0);
        }

    }

    public void setDecorationLayerTile(int x, int y, Tile tile) {
        try {
            this.decorationLayer[x][y] = tile;
        } catch (ArrayIndexOutOfBoundsException e) {
            int newX = x - this.start.getX();
            int newY = y - this.start.getY();
            this.decorationLayer[newX][newY] = tile;
        }
    }

    public void setDecorationLayerTile(EntityCoordinates entityCoordinates, Tile tile) {
        setDecorationLayerTile((int) Math.floor(entityCoordinates.x), (int) Math.floor(entityCoordinates.y), tile);
    }

    public void setDecorationLayerTile(TileCoordinates tileCoordinates, Tile tile) {
        setDecorationLayerTile(tileCoordinates.getX(), tileCoordinates.getY(), tile);
    }
}
