package de.pianomanu.asterania.world.entities.player.inventory.item;

import de.pianomanu.asterania.world.tile.Tile;
import de.pianomanu.asterania.world.tile.Tiles;

public class Item {
    private final String name;
    private final float weight;

    public Item(String name, float weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return this.name;
    }

    public float getWeight() {
        return this.weight;
    }

    public Tile toTile(String s) {
        if (this.getName().equals("rock"))
            return Tiles.ROCK;
        return Tiles.WHITE;
    }
}
