package de.pianomanu.asterania.world.tile;

public class TileSettings {
    private final String name;
    private final boolean isAccessible;

    public TileSettings(String name, boolean isAccessible) {
        this.name = name;
        this.isAccessible = isAccessible;
    }

    public String getSettingsName() {
        return this.name;
    }

    public boolean isAccessible() {
        return this.isAccessible;
    }

    public static class Settings {
        public static TileSettings NORMAL_TILE = new TileSettings("normal", true);
        public static TileSettings INACCESSIBLE_TILE = new TileSettings("inaccessible", false);
    }
}
