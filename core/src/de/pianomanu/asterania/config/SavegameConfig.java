package de.pianomanu.asterania.config;

import de.pianomanu.asterania.AsteraniaMain;

public class SavegameConfig {
    public static final int SEED = 0;

    public final String SAVEGAME_NAME;
    public final String SAVEGAME_PATH;
    public final String WORLDS_SAVE_PATH;
    public final String VERSION_SAVE_PATH;
    public final String PLAYER_DATA_SAVE_PATH;
    public final String SAVEGAME_INFO_DATA_PATH;

    public SavegameConfig(String savegameName) {
        this.SAVEGAME_NAME = savegameName;
        SAVEGAME_PATH = GameConfig.SAVEGAME_PATH_OFFSET + SAVEGAME_NAME + "\\";
        WORLDS_SAVE_PATH = SAVEGAME_PATH + GameConfig.WORLDS_SAVE_DIR_NAME + "\\";
        VERSION_SAVE_PATH = SAVEGAME_PATH + GameConfig.SAVEGAME_VERSION;
        PLAYER_DATA_SAVE_PATH = SAVEGAME_PATH + GameConfig.PLAYER_DATA_SAVE_FILE;
        SAVEGAME_INFO_DATA_PATH = SAVEGAME_PATH + GameConfig.SAVEGAME_INFO;
    }

    public static SavegameConfig getInstance() {
        return AsteraniaMain.INSTANCE.getCurrentActiveSavegame().getGameConfig();
    }
}
