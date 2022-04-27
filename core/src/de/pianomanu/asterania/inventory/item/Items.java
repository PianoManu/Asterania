package de.pianomanu.asterania.inventory.item;

import de.pianomanu.asterania.registry.GameRegistry;

public class Items {
    public static final Item NONE = r(new Item("none", 0));
    public static final Item GRASS_TILE = r(new Item("grass", 0.2f));
    public static final Item ROCK_TILE = r(new Item("rock", 10));
    public static final Item SOIL_TILE = r(new Item("soil", 0.4f));
    public static final Item DIRTY_STONE_TILE = r(new Item("dirty_stone", 5));
    public static final Item TITANIUM_DIBORIDE_TILE = r(new Item("titanium_diboride", 50));


    public static final Item MINE_LADDER = r(new Item("mine_ladder", 4));
    public static final Item TWIG = r(new Item("twig", 0.2f));
    public static final Item STONE = r(new Item("stone", 6));

    private static Item r(Item item) {
        GameRegistry.registerItems(item);
        return item;
    }

    public static void setupItems() {
        //TODO find a better way to load items...
        NONE.getName();
        GRASS_TILE.getName();
        ROCK_TILE.getName();
        SOIL_TILE.getName();
        DIRTY_STONE_TILE.getName();
        TITANIUM_DIBORIDE_TILE.getName();
        MINE_LADDER.getName();
    }
}
