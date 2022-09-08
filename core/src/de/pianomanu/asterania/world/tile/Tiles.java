package de.pianomanu.asterania.world.tile;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.registry.GameRegistry;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.Worlds;
import de.pianomanu.asterania.world.tile.tileutils.TileMaterials;
import de.pianomanu.asterania.world.tile.tileutils.TileProperties;
import de.pianomanu.asterania.world.tile.tileutils.TileSettings;

public class Tiles {
    public static final BasicTile GRASS = r(new BasicTile("grass", TileMaterials.ORGANIC, TileSettings.Settings.ACCESSIBLE_TILE.with(TileProperties.BREAK_TIME, 0.2f)));
    public static final BasicTile ROCK = r(new BasicTile("rock", TileMaterials.ROCK, TileSettings.Settings.ACCESSIBLE_TILE.with(TileProperties.BREAK_TIME, 5f)));
    public static final BasicTile WHITE = r(new BasicTile("white", TileMaterials.NONE, TileSettings.Settings.ACCESSIBLE_TILE.with(TileProperties.BREAK_TIME, 0.2f)));
    public static final BasicTile SOIL_TILE = r(new BasicTile("soil", TileMaterials.DIRT, TileSettings.Settings.ACCESSIBLE_TILE.with(TileProperties.BREAK_TIME, 0.2f)));
    public static final BasicTile DIRTY_STONE_TILE = r(new BasicTile("dirty_stone", TileMaterials.ROCK, TileSettings.Settings.INACCESSIBLE_TILE.with(TileProperties.BREAK_TIME, 5f)));
    public static final BasicTile TITANIUM_DIBORIDE = r(new BasicTile("titanium_diboride", TileMaterials.METAL, TileSettings.Settings.UNBREAKABLE_TILE));
    public static final TeleportingTile MINE_LADDER = r(new TeleportingTile("mine_ladder", TileMaterials.WOOD, TileSettings.Settings.ACCESSIBLE_TILE));

    public static final AnimatedTile WATER_TILE = r(new AnimatedTile("water", TileMaterials.FLUID, TileSettings.Settings.INACCESSIBLE_TILE));

    public static final BasicDecorationLayerTile TWIG = r(new BasicDecorationLayerTile("twig", TileMaterials.WOOD, TileSettings.Settings.INACCESSIBLE_TILE));
    public static final BasicDecorationLayerTile STONE = r(new BasicDecorationLayerTile("stone", TileMaterials.ROCK, TileSettings.Settings.INACCESSIBLE_TILE, 4));
    public static final PlantTile FLOWER_YELLOW = r(new PlantTile("flower_yellow", TileMaterials.ORGANIC, TileSettings.Settings.ACCESSIBLE_TILE.with(TileProperties.BREAK_TIME, 0.1f)));

    public static final BasicTile DEFAULT_TILE = GRASS;

    private static <T extends Tile> T r(T tile) {
        GameRegistry.registerTile(tile);
        return tile;
    }

    public static void setupTeleportingTiles() {
        boolean mineExists = false;
        for (World w :
                AsteraniaMain.INSTANCE.getCurrentActiveSavegame().getUniverse().getWorlds()) {
            if (w.getWorldName().equals(Worlds.MINE.getWorldName())) {
                MINE_LADDER.getTeleporter().setDestinyWorld(w);
                mineExists = true;
            }
        }
        if (!mineExists)
            MINE_LADDER.getTeleporter().setDestinyWorld(Worlds.MINE);
    }
}
