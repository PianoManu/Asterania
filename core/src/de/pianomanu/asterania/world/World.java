package de.pianomanu.asterania.world;

import de.pianomanu.asterania.config.GameConfig;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.world.tile.Tile;
import de.pianomanu.asterania.world.tile.Tiles;

public class World {
    private final Tile[][] tiles = new Tile[GameConfig.WORLD_WIDTH][GameConfig.WORLD_HEIGHT];
    private Player player = new Player();
    private TileCoordinates entryPoint;

    public World(TileCoordinates entryPoint) {
        createTerrain();
        this.entryPoint = entryPoint;
    }

    private void createTerrain() {
        for (int x = 0; x < GameConfig.WORLD_WIDTH; x++) {
            for (int y = 0; y < GameConfig.WORLD_HEIGHT; y++) {
                this.tiles[x][y] = Tiles.GRASS;
                if (x == y)
                    this.tiles[x][y] = Tiles.ROCK;
            }
        }
    }

    public void joinWorld(Player player, TileCoordinates entryPoint) {
        this.player = player;
        this.player.setCharacterPos(entryPoint.toEntityCoordinates());
        this.player.updateHitbox();
    }

    public Player getPlayer() {
        return this.player;
    }

    public Tile getTile(int x, int y) {
        return this.tiles[x][y];
    }

    public Tile getTile(EntityCoordinates entityCoordinates) {
        return getTile((int) Math.floor(entityCoordinates.x), (int) Math.floor(entityCoordinates.x));
    }

    public Tile getTile(TileCoordinates tileCoordinates) {
        try {
            return this.tiles[tileCoordinates.getX()][tileCoordinates.getY()];
        } catch (ArrayIndexOutOfBoundsException e) {
            return Tiles.ROCK;
        }

    }

    public void setTile(int x, int y, Tile tile) {
        this.tiles[x][y] = tile;
    }

    public void setTile(EntityCoordinates entityCoordinates, Tile tile) {
        setTile((int) Math.floor(entityCoordinates.x), (int) Math.floor(entityCoordinates.y), tile);
    }

    public void setTile(TileCoordinates tileCoordinates, Tile tile) {
        setTile(tileCoordinates.getX(), tileCoordinates.getY(), tile);
    }

    public Tile[][] getTerrain() {
        return this.tiles;
    }

    public TileCoordinates getEntryPoint() {
        return entryPoint;
    }

    public void setEntryPoint(TileCoordinates entryPoint) {
        this.entryPoint = entryPoint;
    }
}
