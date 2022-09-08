package de.pianomanu.asterania.world.tile.tileutils;

import de.pianomanu.asterania.registry.GameRegistry;

import java.util.HashSet;
import java.util.Set;

public class TileSettings {
    private Set<TileProperty<?>> tileProperties = new HashSet<>();
    private final String name;

    public TileSettings(String name) {
        this.name = name;
    }

    private void initializeTilePropertySet() {
        tileProperties.addAll(GameRegistry.getAllProperties());
    }

    public String getSettingsName() {
        return this.name;
    }

    public <C extends Comparable<?>> C get(String tilePropertyName) {
        TileProperty<?> tileProperty = GameRegistry.getTilePropertyFromString(tilePropertyName);
        if (tileProperty != null) {
            if (this.tileProperties.contains(tileProperty)) {
                for (TileProperty<?> t :
                        this.tileProperties) {
                    if (t.name.equals(tileProperty.name)) {
                        return (C) t.value;
                    }
                }
            }
            return (C) tileProperty.DEFAULT_VALUE;
        }
        return null;
    }

    public <C extends Comparable<?>, T extends TileProperty<?>> C get(T tileProperty) {
        if (GameRegistry.getAllProperties().contains(tileProperty)) {
            for (TileProperty<?> t :
                    this.tileProperties) {
                if (t.name.equals(tileProperty.name)) {
                    return (C) t.value;
                }
            }
            return (C) tileProperty.DEFAULT_VALUE;
        }
        return null;
    }

    public <T extends Comparable<T>, V extends T> TileSettings with(TileProperty<T> tileProperty, V value) {
        this.tileProperties.add(tileProperty.copyThisProperty().setValue(value));
        return this;
    }

    public static class Settings {
        public static TileSettings ACCESSIBLE_TILE = new TileSettings("normal").with(TileProperties.IS_ACCESSIBLE, true);
        public static TileSettings INACCESSIBLE_TILE = new TileSettings("inaccessible");
        public static TileSettings UNBREAKABLE_TILE = new TileSettings("unbreakable").with(TileProperties.BREAK_TIME, Float.MAX_VALUE);
    }
}
