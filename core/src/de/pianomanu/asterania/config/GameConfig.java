package de.pianomanu.asterania.config;

public class GameConfig {
    public static final int WORLD_WIDTH = 64;
    public static final int WORLD_HEIGHT = 64;

    public static final String SAVE_NAME = "world";
    public static final String SAVE_PATH = SAVE_NAME;
    private static final String versionDescription = "alpha";
    private static final int patchVersion = 0;
    private static final int minorVersion = 1;
    private static final int majorVersion = 0;
    public static final String GAME_VERSION = versionDescription + majorVersion + "." + minorVersion + "." + patchVersion;
}
