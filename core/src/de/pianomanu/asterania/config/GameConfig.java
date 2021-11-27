package de.pianomanu.asterania.config;

import de.pianomanu.asterania.world.Worlds;

public class GameConfig {
    public static final int WORLD_WIDTH = 64;
    public static final int WORLD_HEIGHT = 64;

    public static final String WORLD_FILE_FORMAT = "asw"; //ASterania World
    public static final String SAVEGAME_PATH_OFFSET = "";
    public static final String SAVEGAME_NAME = SAVEGAME_PATH_OFFSET + "game";
    public static final String WORLDS_SAVE_PATH = SAVEGAME_NAME + "\\worlds\\";
    public static String WORLDS_SAVE_PATH_HOME = WORLDS_SAVE_PATH + Worlds.HOME.getWorldName();
    public static final String VERSION_SAVE_PATH = SAVEGAME_NAME + "\\version";
    private static final String versionDescription = "alpha";
    private static final int patchVersion = 0;
    private static final int minorVersion = 1;
    private static final int majorVersion = 0;
    public static final String GAME_VERSION = versionDescription + majorVersion + "." + minorVersion + "." + patchVersion;

    public static void load() {

    }
}
