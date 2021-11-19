package de.pianomanu.asterania.inventory.tileproperties;

import de.pianomanu.asterania.registry.GameRegistry;

public class TileProperties {
    public static final TileProperty<Boolean> IS_ACCESSIBLE = r(new TileProperty<>("is_accessible", false, true, false));
    public static final TileProperty<Float> BREAK_TIME = r(new TileProperty<>("break_time", 0f, Float.MAX_VALUE, 0.5f));

    private static <T extends TileProperty<?>> T r(T tileProperty) {
        GameRegistry.registerTileProperty(tileProperty);
        return tileProperty;
    }
}
