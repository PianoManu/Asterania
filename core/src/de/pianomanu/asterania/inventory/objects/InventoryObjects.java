package de.pianomanu.asterania.inventory.objects;

import de.pianomanu.asterania.registry.GameRegistry;

public class InventoryObjects {
    public static final InventoryObject NONE = r(new InventoryObject("none", 0));
    public static final InventoryObject GRASS_TILE = r(new InventoryObject("grass", 0.2f));
    public static final InventoryObject ROCK_TILE = r(new InventoryObject("rock", 10));

    private static InventoryObject r(InventoryObject inventoryObject) {
        GameRegistry.registerInventoryObjects(inventoryObject);
        return inventoryObject;
    }
}
