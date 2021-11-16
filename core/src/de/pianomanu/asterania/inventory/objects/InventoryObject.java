package de.pianomanu.asterania.inventory.objects;

import de.pianomanu.asterania.world.tile.Tile;
import de.pianomanu.asterania.world.tile.Tiles;

public class InventoryObject {
    private final String name;
    private final float weight;

    public InventoryObject(String name, float weight) {
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
