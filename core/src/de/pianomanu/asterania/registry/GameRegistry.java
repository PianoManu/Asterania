package de.pianomanu.asterania.registry;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.inventory.objects.InventoryObject;
import de.pianomanu.asterania.inventory.tileproperties.TileProperty;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.tile.Tile;
import de.pianomanu.asterania.world.tile.Tiles;
import de.pianomanu.asterania.world.worldsections.WorldSectionSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GameRegistry {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    private static final List<Tile> TILES = new ArrayList<>();
    private static final List<InventoryObject> OBJECTS = new ArrayList<>();
    private static final List<TileProperty<?>> TILE_PROPERTIES = new ArrayList<>();
    private static final List<World> WORLDS = new ArrayList<>();
    private static final List<WorldSectionSettings> WORLD_SECTION_SETTINGS = new ArrayList<>();

    public static <T extends Tile> void registerTile(T tile) {
        TILES.add(tile);
    }

    public static <I extends InventoryObject> void registerInventoryObjects(I inventoryObject) {
        OBJECTS.add(inventoryObject);
    }

    public static <T extends TileProperty<?>> void registerTileProperty(T tileProperty) {
        TILE_PROPERTIES.add(tileProperty);
    }

    public static <T extends Tile> InventoryObject getInventoryObject(T tile) {
        for (InventoryObject iO :
                OBJECTS) {
            if (iO.getName().equals(tile.getSaveFileString())) {
                return iO;
            }
        }
        return null;
    }

    public static <T extends Tile> T getTile(InventoryObject iO) {
        for (Tile t :
                TILES) {
            if (t.getSaveFileString().equals(iO.getName())) {
                try {
                    return (T) t;
                } catch (ClassCastException e) {
                    LOGGER.warning("An error occurred whilst trying to cast " + t.getClass() + " to Tile");
                    e.printStackTrace();
                    return (T) Tiles.WHITE;
                }

            }
        }
        return (T) Tiles.WHITE;
    }

    public static Tile getTileFromString(String tileName) {
        for (Tile t :
                TILES) {
            if (t.getSaveFileString().equals(tileName)) {
                return t;
            }
        }
        LOGGER.warning("Could not decode a tile from input string \"" + tileName + "\", using default tile " + Tiles.WHITE.getSaveFileString() + " instead!");
        return Tiles.WHITE;
    }

    public static List<TileProperty<?>> getAllProperties() {
        return TILE_PROPERTIES;
    }

    public static TileProperty<?> getTilePropertyByName(String tilePropertyName) {
        for (TileProperty<?> t :
                TILE_PROPERTIES) {
            if (t.name.equals(tilePropertyName))
                return t;
        }
        LOGGER.warning("Could not decode a tile property from input string \"" + tilePropertyName + "\", returning null...");
        return null;
    }

    public static void registerWorld(World world) {
        WORLDS.add(world);
    }

    public static List<World> getWorlds() {
        return WORLDS;
    }

    public static void registerWorldSectionSettings(WorldSectionSettings settings) {
        WORLD_SECTION_SETTINGS.add(settings);
    }

    public static List<WorldSectionSettings> getWorldSectionSettings() {
        return WORLD_SECTION_SETTINGS;
    }
}
