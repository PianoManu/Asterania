package de.pianomanu.asterania.world.tile;

import java.util.Stack;

public class TileStack {
    private final Stack<Tile> tiles = new Stack<>();

    public TileStack(Tile bottomTile) {
        this.tiles.push(bottomTile);
    }

    public TileStack() {
        this.tiles.push(Tiles.TITANIUM_DIBORIDE);
    }

    public Stack<Tile> getTiles() {
        return this.tiles;
    }

    public Tile getTopTile() {
        return this.getTiles().peek();
    }

    public Tile addTile(Tile tile) {
        return this.getTiles().push(tile);
    }

    public Tile removeTopTile() {
        return this.getTiles().pop();
    }

    public Tile replaceTopTile(Tile tile) {
        this.getTiles().pop();
        return this.getTiles().push(tile);
    }
}
