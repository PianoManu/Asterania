package de.pianomanu.asterania.config;

import de.pianomanu.asterania.world.Worlds;

public class GameConfig {
    public static final int WORLD_WIDTH = 64;
    public static final int WORLD_HEIGHT = 64;

    public static final String WORLD_FILE_FORMAT = "asw"; //ASterania World
    public static final String SAVEGAME_PATH_OFFSET = "saves\\";
    public static String SAVEGAME_NAME = "game";
    public static String SAVEGAME_PATH = SAVEGAME_PATH_OFFSET + SAVEGAME_NAME;
    public static String WORLDS_SAVE_PATH = SAVEGAME_PATH + "\\worlds\\";
    public static String WORLDS_SAVE_PATH_HOME = WORLDS_SAVE_PATH + Worlds.HOME.getWorldName();
    public static String VERSION_SAVE_PATH = SAVEGAME_PATH + "\\version";
    public static String PLAYER_DATA_SAVE_PATH = SAVEGAME_PATH + "\\playerdata";
    private static final String versionDescription = "alpha";
    private static final int patchVersion = 0;
    private static final int minorVersion = 1;
    private static final int majorVersion = 0;
    public static final String GAME_VERSION = versionDescription + majorVersion + "." + minorVersion + "." + patchVersion;

    public static final int SEED = 0;

    public static void load() {

    }

    //TODO find way to load correctly from start
    public static void reload() {
        SAVEGAME_PATH = SAVEGAME_PATH_OFFSET + SAVEGAME_NAME;
        WORLDS_SAVE_PATH = SAVEGAME_PATH + "\\worlds\\";
        WORLDS_SAVE_PATH_HOME = WORLDS_SAVE_PATH + Worlds.HOME.getWorldName();
        PLAYER_DATA_SAVE_PATH = SAVEGAME_PATH + "\\playerdata";
        VERSION_SAVE_PATH = SAVEGAME_PATH + "\\version";
    }
}
