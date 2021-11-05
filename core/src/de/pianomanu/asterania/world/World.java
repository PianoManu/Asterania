package de.pianomanu.asterania.world;

import de.pianomanu.asterania.config.GameConfig;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.world.tile.Tile;
import de.pianomanu.asterania.world.tile.Tiles;

public class World {
    private final Tile[][] tiles = new Tile[GameConfig.WORLD_WIDTH][GameConfig.WORLD_HEIGHT];
    //private final List<Player> players = new ArrayList<>();
    private Player player = new Player();

    public World() {
        createTerrain();
    }

    private void createTerrain() {
        for (int x = 0; x < GameConfig.WORLD_WIDTH; x++) {
            for (int y = 0; y < GameConfig.WORLD_HEIGHT; y++) {
                this.tiles[x][y] = Tiles.GRASS;
            }
        }
    }

    /*private boolean addPlayer(Player player) {
        return this.players.add(player);
    }*/

    /*public boolean joinWorld(Player player) {
        return this.addPlayer(player);
    }*/

    public void joinWorld(Player player) {
        this.player = player;
    }

    /*public List<Player> getPlayers() {
        return players;
    }*/

    public Player getPlayer() {
        return this.player;
    }

    public Tile getTile(int x, int y) {
        return this.tiles[x][y];
    }

    public Tile[][] getTerrain() {
        return this.tiles;
    }
}
