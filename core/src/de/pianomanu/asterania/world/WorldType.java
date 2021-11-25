package de.pianomanu.asterania.world;

import de.pianomanu.asterania.world.worldsections.WorldSectionSettings;

public enum WorldType {
    GRASSLAND,
    UNDERGROUND;

    public WorldSectionSettings getSettings() {
        switch (this) {
            case GRASSLAND:
                return WorldSectionSettings.SettingList.GRASSLAND_PLAIN;
            case UNDERGROUND:
                return WorldSectionSettings.SettingList.MINE_PLAIN;
        }
        return WorldSectionSettings.SettingList.GRASSLAND_PLAIN;
    }
}
