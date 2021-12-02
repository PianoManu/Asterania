package de.pianomanu.asterania.inventory.objects;

import de.pianomanu.asterania.registry.GameRegistry;

public class InventoryObjects {
    public static final InventoryObject NONE = r(new InventoryObject("none", 0));
    public static final InventoryObject GRASS_TILE = r(new InventoryObject("grass", 0.2f));
    public static final InventoryObject ROCK_TILE = r(new InventoryObject("rock", 10));
    public static final InventoryObject SOIL_TILE = r(new InventoryObject("soil", 0.4f));
    public static final InventoryObject DIRTY_STONE_TILE = r(new InventoryObject("dirty_stone", 5));
    public static final InventoryObject TITANIUM_DIBORIDE_TILE = r(new InventoryObject("titanium_diboride", 50));
    public static final InventoryObject MINE_LADDER = r(new InventoryObject("mine_ladder", 4));

    private static InventoryObject r(InventoryObject inventoryObject) {
        GameRegistry.registerInventoryObjects(inventoryObject);
        return inventoryObject;
    }

    public static void setupObjects() {
        //TODO find a better way to load objects...
        NONE.getName();
        GRASS_TILE.getName();
        ROCK_TILE.getName();
        SOIL_TILE.getName();
        DIRTY_STONE_TILE.getName();
        TITANIUM_DIBORIDE_TILE.getName();
        MINE_LADDER.getName();
    }
}
