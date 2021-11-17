package de.pianomanu.asterania.inventory.objects;

import de.pianomanu.asterania.registry.GameRegistry;

public class InventoryObjects {
    public static final InventoryObject NONE = r(new InventoryObject("none", 0));
    public static final InventoryObject GRASS_TILE = r(new InventoryObject("grass", 0.2f));
    public static final InventoryObject ROCK_TILE = r(new InventoryObject("rock", 10));
    public static final InventoryObject SOIL_TILE = r(new InventoryObject("soil", 0.4f));
    public static final InventoryObject DIRTY_STONE_TILE = r(new InventoryObject("dirty_stone", 5));
    public static final InventoryObject TITANIUM_DIBORIDE_TILE = r(new InventoryObject("titanium_diboride", 50));

    private static InventoryObject r(InventoryObject inventoryObject) {
        GameRegistry.registerInventoryObjects(inventoryObject);
        return inventoryObject;
    }
}
