package de.pianomanu.asterania.world.tile;

import de.pianomanu.asterania.inventory.tileproperties.TileProperties;
import de.pianomanu.asterania.registry.GameRegistry;

public class Tiles {
    public static final BasicTile GRASS = r(new BasicTile("grass", TileSettings.Settings.ACCESSIBLE_TILE.with(TileProperties.BREAK_TIME, 0.2f)));
    public static final BasicTile ROCK = r(new BasicTile("rock", TileSettings.Settings.INACCESSIBLE_TILE.with(TileProperties.BREAK_TIME, 5f)));
    public static final BasicTile WHITE = r(new BasicTile("white", TileSettings.Settings.ACCESSIBLE_TILE.with(TileProperties.BREAK_TIME, 0.2f)));
    public static final BasicTile SOIL_TILE = r(new BasicTile("soil", TileSettings.Settings.ACCESSIBLE_TILE.with(TileProperties.BREAK_TIME, 0.2f)));
    public static final BasicTile DIRTY_STONE_TILE = r(new BasicTile("dirty_stone", TileSettings.Settings.INACCESSIBLE_TILE.with(TileProperties.BREAK_TIME, 5f)));
    public static final BasicTile TITANIUM_DIBORIDE = r(new BasicTile("titanium_diboride", TileSettings.Settings.UNBREAKABLE_TILE));

    private static <T extends Tile> T r(T tile) {
        GameRegistry.registerTile(tile);
        return tile;
    }
}
