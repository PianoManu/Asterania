package de.pianomanu.asterania.registry;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.inventory.objects.InventoryObject;
import de.pianomanu.asterania.world.tile.Tile;
import de.pianomanu.asterania.world.tile.Tiles;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GameRegistry {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    private static final List<Tile> TILES = new ArrayList<>();
    private static final List<InventoryObject> OBJECTS = new ArrayList<>();

    public static <T extends Tile> void registerTile(T tile) {
        TILES.add(tile);
    }

    public static <I extends InventoryObject> void registerInventoryObjects(I inventoryObject) {
        OBJECTS.add(inventoryObject);
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
}
