package de.pianomanu.asterania.world.tile;

import java.util.EmptyStackException;
import java.util.Stack;

public class TileStack {
    private Stack<Tile> tiles = new Stack<>();

    public TileStack(Tile bottomTile) {
        this.tiles.push(bottomTile);
    }

    public TileStack() {
        this.tiles.push(Tiles.ROCK);
    }

    public Stack<Tile> getTiles() {
        return this.tiles;
    }

    public Tile getTopTile() {
        try {
            return this.getTiles().peek();
        } catch (EmptyStackException e) {
            return null;
        }
    }

    public Tile addTile(Tile tile) {
        return this.getTiles().push(tile);
    }

    public Tile removeTopTile() {
        try {
            return this.getTiles().pop();
        } catch (EmptyStackException e) {
            return null;
        }
    }

    public Tile replaceTopTile(Tile tile) {
        this.getTiles().pop();
        return this.getTiles().push(tile);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder("{S");
        for (Tile t :
                this.tiles) {
            b.append('|').append(t.getSaveFileString());
        }
        return b.append("}").toString();
    }

    public TileStack flip() {
        Stack<Tile> tmp = new Stack<>();
        while (this.tiles.size() != 0) {
            tmp.add(this.tiles.pop());
        }
        this.tiles = tmp;
        return this;
    }

    public Tile removeBottomTile() {
        try {
            this.flip();
            Tile tmp = this.removeTopTile();
            this.flip();
            return tmp;
        } catch (EmptyStackException e) {
            return null;
        }
    }
}
