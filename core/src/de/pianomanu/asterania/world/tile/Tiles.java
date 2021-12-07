package de.pianomanu.asterania.world.tile;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.inventory.tileproperties.TileProperties;
import de.pianomanu.asterania.registry.GameRegistry;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.Worlds;

public class Tiles {
    public static final BasicTile GRASS = r(new BasicTile("grass", TileSettings.Settings.ACCESSIBLE_TILE.with(TileProperties.BREAK_TIME, 0.2f)));
    public static final BasicTile ROCK = r(new BasicTile("rock", TileSettings.Settings.ACCESSIBLE_TILE.with(TileProperties.BREAK_TIME, 5f)));
    public static final BasicTile WHITE = r(new BasicTile("white", TileSettings.Settings.ACCESSIBLE_TILE.with(TileProperties.BREAK_TIME, 0.2f)));
    public static final BasicTile SOIL_TILE = r(new BasicTile("soil", TileSettings.Settings.ACCESSIBLE_TILE.with(TileProperties.BREAK_TIME, 0.2f)));
    public static final BasicTile DIRTY_STONE_TILE = r(new BasicTile("dirty_stone", TileSettings.Settings.INACCESSIBLE_TILE.with(TileProperties.BREAK_TIME, 5f)));
    public static final BasicTile TITANIUM_DIBORIDE = r(new BasicTile("titanium_diboride", TileSettings.Settings.UNBREAKABLE_TILE));
    public static final TeleportingTile MINE_LADDER = r(new TeleportingTile("mine_ladder", TileSettings.Settings.ACCESSIBLE_TILE));

    public static final BasicTile DEFAULT_TILE = GRASS;

    private static <T extends Tile> T r(T tile) {
        GameRegistry.registerTile(tile);
        return tile;
    }

    public static void setupTeleportingTiles() {
        boolean mineExists = false;
        for (World w :
                AsteraniaMain.saveFile.getUniverse().getWorlds()) {
            if (w.getWorldName().equals(Worlds.MINE.getWorldName())) {
                MINE_LADDER.getTeleporter().setDestinyWorld(w);
                mineExists = true;
            }
        }
        if (!mineExists)
            MINE_LADDER.getTeleporter().setDestinyWorld(Worlds.MINE);
    }
}
