package de.pianomanu.asterania.world.worldsections;

import de.pianomanu.asterania.world.WorldType;
import de.pianomanu.asterania.world.tile.Tile;
import de.pianomanu.asterania.world.tile.Tiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorldSectionSettings {
    private final List<Tile> validTiles = new ArrayList<>();
    private final String name;
    private final WorldType correspondingWorldType;

    public WorldSectionSettings(String name, WorldType worldType, Tile... tiles) {
        this.name = name;
        this.correspondingWorldType = worldType;
        Collections.addAll(validTiles, tiles);
    }

    public List<Tile> getValidTiles() {
        return this.validTiles;
    }

    public String getName() {
        return this.name;
    }

    public boolean isValidTile(Tile t) {
        return this.validTiles.contains(t);
    }

    public WorldType getWorldType() {
        return this.correspondingWorldType;
    }

    public static class SettingList {
        public static final WorldSectionSettings GRASSLAND_PLAIN = new WorldSectionSettings("grassland_plain", WorldType.GRASSLAND, Tiles.GRASS);
        public static final WorldSectionSettings MINE_PLAIN = new WorldSectionSettings("grassland_plain", WorldType.GRASSLAND, Tiles.DIRTY_STONE_TILE, Tiles.ROCK);
    }
}
