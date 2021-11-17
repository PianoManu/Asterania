package de.pianomanu.asterania.world.tile;

public class TileSettings {
    private final String name;
    private final boolean isAccessible;
    private final float breakTime;

    public TileSettings(String name, boolean isAccessible, float breakTime) {
        this.name = name;
        this.isAccessible = isAccessible;
        this.breakTime = breakTime;
    }

    public String getSettingsName() {
        return this.name;
    }

    public boolean isAccessible() {
        return this.isAccessible;
    }

    public float getBreakTime() {
        return this.breakTime;
    }

    public static class Settings {
        public static TileSettings NORMAL_TILE = new TileSettings("normal", true, 0.2f);
        public static TileSettings INACCESSIBLE_TILE = new TileSettings("inaccessible", false, 10f);
        public static TileSettings UNBREAKABLE_TILE = new TileSettings("unbreakable", false, Float.MAX_VALUE);
    }
    //
    //public <V extends Comparable> TileSettings with(V )
}
