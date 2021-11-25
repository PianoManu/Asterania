package de.pianomanu.asterania.config;

import de.pianomanu.asterania.world.Worlds;

public class GameConfig {
    public static final int WORLD_WIDTH = 64;
    public static final int WORLD_HEIGHT = 64;

    public static final String WORLD_FILE_FORMAT = "asw"; //ASterania World
    public static final String SAVE_PATH_OFFSET = "";
    public static final String SAVE_NAME = SAVE_PATH_OFFSET + "game";
    public static final String SAVE_PATH = SAVE_NAME + "\\";
    public static String SAVE_PATH_HOME = SAVE_PATH + Worlds.HOME.getWorldName();
    private static final String versionDescription = "alpha";
    private static final int patchVersion = 0;
    private static final int minorVersion = 1;
    private static final int majorVersion = 0;
    public static final String GAME_VERSION = versionDescription + majorVersion + "." + minorVersion + "." + patchVersion;
}
