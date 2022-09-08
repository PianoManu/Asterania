package de.pianomanu.asterania.utils.fileutils;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.GameConfig;
import de.pianomanu.asterania.savegame.Savegame;
import de.pianomanu.asterania.world.Worlds;

import java.io.File;
import java.util.logging.Logger;

public class SaveGameUtils {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    public static String getSavegameDirectory(String savegameName) {
        return GameConfig.SAVEGAME_PATH_OFFSET + savegameName;
    }

    public static String getSavegameWorldDirectory(String savegameName) {
        return getSavegameDirectory(savegameName) + "\\" + GameConfig.WORLDS_SAVE_DIR_NAME;
    }

    public static String getSavegameWorldSubdirectory(String savegameName, String worldName) {
        return getSavegameWorldDirectory(savegameName) + "\\" + worldName;
    }

    public static void createNewGame(Savegame newSavegame, String savegameName) {
        createDirectories(savegameName);
        createStartWorldForNewGame(newSavegame);
    }

    private static void createDirectories(String savegameName) {
        File savesDir = new File(GameConfig.SAVEGAME_PATH_OFFSET);
        File mainDir = new File(GameConfig.SAVEGAME_PATH_OFFSET + savegameName);
        File folder = new File(GameConfig.SAVEGAME_PATH_OFFSET + savegameName + "\\" + GameConfig.WORLDS_SAVE_DIR_NAME);
        savesDir.mkdir();
        mainDir.mkdir();
        folder.mkdir();
    }

    private static void createStartWorldForNewGame(Savegame savegame) {
        savegame.getUniverse().getWorlds().add(Worlds.HOME);
    }

    private static String removeExtension(String fileName) {
        if (fileName.endsWith("." + GameConfig.WORLDSECTION_FILE_FORMAT)) {
            return fileName.substring(0, fileName.length() - (GameConfig.WORLDSECTION_FILE_FORMAT.length() + 1));
        }
        LOGGER.severe("File \"" + fileName + "\" does not end with \"" + GameConfig.WORLDSECTION_FILE_FORMAT + "\". This is not good and might lead to a crash");
        return null;
    }

}
