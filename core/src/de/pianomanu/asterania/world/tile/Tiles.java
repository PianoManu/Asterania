package de.pianomanu.asterania.world.tile;

import de.pianomanu.asterania.registry.GameRegistry;

public class Tiles {
    public static final BasicTile GRASS = r(new BasicTile("grass", TileSettings.Settings.NORMAL_TILE));
    public static final BasicTile ROCK = r(new BasicTile("rock", TileSettings.Settings.INACCESSIBLE_TILE));
    public static final BasicTile WHITE = r(new BasicTile("white", TileSettings.Settings.NORMAL_TILE));

    private static <T extends Tile> T r(T tile) {
        GameRegistry.registerTile(tile);
        return tile;
    }
}
