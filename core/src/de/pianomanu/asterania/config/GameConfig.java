package de.pianomanu.asterania.config;

public class GameConfig {
    public static final int WORLD_WIDTH = 64;
    public static final int WORLD_HEIGHT = 64;
    public static final String WORLDSECTION_FILE_FORMAT = "asws"; //ASterania WorldSection
    public static final String SAVEGAME_PATH_OFFSET = "saves\\";
    public static final String WORLDS_SAVE_DIR_NAME = "worlds";
    public static final String PLAYER_DATA_SAVE_FILE = "\\playerData";
    public static final String SAVEGAME_INFO = "\\savegameInfo";
    public static final String SAVEGAME_VERSION = "\\version";
    public static final String DECORATION_LAYER_SUFFIX = "_decoration_layer";
    private static final String versionDescription = "alpha";
    private static final int patchVersion = 0;
    private static final int minorVersion = 1;
    private static final int majorVersion = 0;
    public static final String GAME_VERSION = versionDescription + majorVersion + "." + minorVersion + "." + patchVersion;
}
