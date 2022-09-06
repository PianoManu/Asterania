package de.pianomanu.asterania.world.tile;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.inventory.tileproperties.TileProperties;
import de.pianomanu.asterania.registry.GameRegistry;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.Worlds;

public class Tiles {
    public static final BasicTile GRASS = r(new BasicTile("grass", TileMaterials.ORGANIC, TileSettings.Settings.ACCESSIBLE_TILE.with(TileProperties.BREAK_TIME, 0.2f)));
    public static final BasicTile ROCK = r(new BasicTile("rock", TileMaterials.ROCK, TileSettings.Settings.ACCESSIBLE_TILE.with(TileProperties.BREAK_TIME, 5f)));
    public static final BasicTile WHITE = r(new BasicTile("white", TileMaterials.NONE, TileSettings.Settings.ACCESSIBLE_TILE.with(TileProperties.BREAK_TIME, 0.2f)));
    public static final BasicTile SOIL_TILE = r(new BasicTile("soil", TileMaterials.DIRT, TileSettings.Settings.ACCESSIBLE_TILE.with(TileProperties.BREAK_TIME, 0.2f)));
    public static final BasicTile DIRTY_STONE_TILE = r(new BasicTile("dirty_stone", TileMaterials.ROCK, TileSettings.Settings.INACCESSIBLE_TILE.with(TileProperties.BREAK_TIME, 5f)));
    public static final BasicTile TITANIUM_DIBORIDE = r(new BasicTile("titanium_diboride", TileMaterials.METAL, TileSettings.Settings.UNBREAKABLE_TILE));
    public static final TeleportingTile MINE_LADDER = r(new TeleportingTile("mine_ladder", TileMaterials.WOOD, TileSettings.Settings.ACCESSIBLE_TILE));

    public static final AnimatedTile WATER_TILE = r(new AnimatedTile("water", TileMaterials.FLUID, TileSettings.Settings.INACCESSIBLE_TILE));

    public static final BasicTile TWIG = r(new BasicTile("twig", TileMaterials.WOOD, TileSettings.Settings.INACCESSIBLE_TILE, LayerType.DECORATION));
    public static final BasicTile STONE = r(new BasicTile("stone", TileMaterials.ROCK, TileSettings.Settings.INACCESSIBLE_TILE, LayerType.DECORATION, 4));
    public static final BasicTile FLOWER_YELLOW = r(new BasicTile("flower_yellow", TileMaterials.ORGANIC, TileSettings.Settings.ACCESSIBLE_TILE.with(TileProperties.BREAK_TIME, 0.1f), LayerType.DECORATION));

    public static final BasicTile DEFAULT_TILE = GRASS;

    private static <T extends Tile> T r(T tile) {
        GameRegistry.registerTile(tile);
        return tile;
    }

    public static void setupTeleportingTiles() {
        boolean mineExists = false;
        for (World w :
                AsteraniaMain.currentActiveSavegame.getUniverse().getWorlds()) {
            if (w.getWorldName().equals(Worlds.MINE.getWorldName())) {
                MINE_LADDER.getTeleporter().setDestinyWorld(w);
                mineExists = true;
            }
        }
        if (!mineExists)
            MINE_LADDER.getTeleporter().setDestinyWorld(Worlds.MINE);
    }
}
