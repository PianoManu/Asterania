package de.pianomanu.asterania.registry;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.inventory.item.Item;
import de.pianomanu.asterania.inventory.item.Items;
import de.pianomanu.asterania.inventory.tileproperties.TileProperty;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.tile.Tile;
import de.pianomanu.asterania.world.tile.TileMaterial;
import de.pianomanu.asterania.world.tile.Tiles;
import de.pianomanu.asterania.world.worldsections.WorldSectionSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GameRegistry {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    private static final List<Tile> TILES = new ArrayList<>();
    private static final List<Item> ITEMS = new ArrayList<>();
    private static final List<TileProperty<?>> TILE_PROPERTIES = new ArrayList<>();
    private static final List<TileMaterial> TILE_MATERIALS = new ArrayList<>();
    private static final List<World> WORLDS = new ArrayList<>();
    private static final List<WorldSectionSettings> WORLD_SECTION_SETTINGS = new ArrayList<>();

    public static <T extends Tile> void registerTile(T tile) {
        TILES.add(tile);
    }

    public static <I extends Item> void registerItems(I item) {
        ITEMS.add(item);
    }

    public static <T extends TileProperty<?>> void registerTileProperty(T tileProperty) {
        TILE_PROPERTIES.add(tileProperty);
    }

    public static <T extends Tile> Item getItem(T tile) {
        for (Item iO :
                ITEMS) {
            if (iO.getName().equals(tile.getSaveFileString())) {
                return iO;
            }
        }
        return null;
    }

    public static Item getItemFromString(String itemName) {
        for (Item io :
                ITEMS) {
            if (io.getName().equals(itemName)) {
                return io;
            }
        }
        LOGGER.warning("Could not decode an item from input string \"" + itemName + "\", using \"null\" instead!");
        return null;
    }

    public static <T extends Tile> T getTile(Item iO) {
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

    public static TileProperty<?> getTilePropertyFromString(String tilePropertyName) {
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

    public static void registerTileMaterial(TileMaterial tileMaterial) {
        TILE_MATERIALS.add(tileMaterial);
    }

    public static List<TileMaterial> getTileMaterials() {
        return TILE_MATERIALS;
    }

    public static TileMaterial getTileMaterialFromString(String tileMaterial) {
        for (TileMaterial t :
                TILE_MATERIALS) {
            if (t.getMaterialName().equals(tileMaterial)) {
                return t;
            }
        }
        LOGGER.warning("Could not decode a tile material from input string \"" + tileMaterial + "\", returning null...");
        return null;
    }

    public static void setupRegistry() {
        Items.load();
    }

    public static void setupRegistryGameLoading() {
        Tiles.setupTeleportingTiles();
    }
}
