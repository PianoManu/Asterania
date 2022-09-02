package de.pianomanu.asterania.inventory.item;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.registry.GameRegistry;

import java.util.logging.Logger;

public class Items {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    public static final Item NONE = r(new Item("none", 0));
    public static final Item GRASS_TILE = r(new Item("grass", 0.2f));
    public static final Item ROCK_TILE = r(new Item("rock", 10));
    public static final Item SOIL_TILE = r(new Item("soil", 0.4f));
    public static final Item DIRTY_STONE_TILE = r(new Item("dirty_stone", 5));
    public static final Item TITANIUM_DIBORIDE_TILE = r(new Item("titanium_diboride", 50));


    public static final Item MINE_LADDER = r(new Item("mine_ladder", 4));
    public static final Item TWIG = r(new Item("twig", 0.2f));
    public static final Item STONE = r(new Item("stone", 6));
    public static final Item FLOWER_YELLOW = r(new Item("flower_yellow", 0.02f));

    private static Item r(Item item) {
        GameRegistry.registerItems(item);
        return item;
    }

    public static void load() {
        LOGGER.info("Loading items...");
    }
}
