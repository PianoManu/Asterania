package de.pianomanu.asterania.config;

import de.pianomanu.asterania.world.Worlds;

public class GameConfig {
    public static final int WORLD_WIDTH = 64;
    public static final int WORLD_HEIGHT = 64;

    public static final String WORLDSECTION_FILE_FORMAT = "asws"; //ASterania WorldSection
    public static final String SAVEGAME_PATH_OFFSET = "saves\\";
    public static String SAVEGAME_NAME = "game";
    public static String SAVEGAME_PATH = SAVEGAME_PATH_OFFSET + SAVEGAME_NAME;
    public static String WORLDS_SAVE_DIR_NAME = "worlds";
    public static String WORLDS_SAVE_PATH = SAVEGAME_PATH + "\\" + WORLDS_SAVE_DIR_NAME + "\\";
    public static String WORLDS_SAVE_PATH_HOME = WORLDS_SAVE_PATH; //" + Worlds.HOME.getWorldName();" -> causes NullPointer when initializing
    public static String VERSION_SAVE_PATH = SAVEGAME_PATH + "\\version";
    public static String PLAYER_DATA_SAVE_FILE = "\\playerData";
    public static String PLAYER_DATA_SAVE_PATH = SAVEGAME_PATH + PLAYER_DATA_SAVE_FILE;
    public static String SAVEGAME_INFO = "\\savegameInfo";
    public static String SAVEGAME_INFO_DATA_PATH = SAVEGAME_PATH + SAVEGAME_INFO;
    public static String DECORATION_LAYER_SUFFIX = "_decoration_layer";
    private static final String versionDescription = "alpha";
    private static final int patchVersion = 0;
    private static final int minorVersion = 1;
    private static final int majorVersion = 0;
    public static final String GAME_VERSION = versionDescription + majorVersion + "." + minorVersion + "." + patchVersion;

    public static final int SEED = 0;

    public static void load() {

    }

    //TODO find way to load correctly from start -> non-static?
    public static void reload() {
        SAVEGAME_PATH = SAVEGAME_PATH_OFFSET + SAVEGAME_NAME;
        WORLDS_SAVE_PATH = SAVEGAME_PATH + "\\" + WORLDS_SAVE_DIR_NAME + "\\";
        WORLDS_SAVE_PATH_HOME = WORLDS_SAVE_PATH + Worlds.HOME.getWorldName();
        PLAYER_DATA_SAVE_PATH = SAVEGAME_PATH + PLAYER_DATA_SAVE_FILE;
        VERSION_SAVE_PATH = SAVEGAME_PATH + "\\version";
        SAVEGAME_INFO_DATA_PATH = SAVEGAME_PATH + SAVEGAME_INFO;
    }
}
