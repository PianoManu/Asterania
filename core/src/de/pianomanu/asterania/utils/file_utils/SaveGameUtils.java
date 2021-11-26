package de.pianomanu.asterania.utils.file_utils;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.GameConfig;
import de.pianomanu.asterania.registry.GameRegistry;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.worldsections.WorldReader;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SaveGameUtils {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    public static final List<File> SAVE_GAME_WORLD_FILES = new ArrayList<>();
    public static final List<World> SAVE_GAME_WORLD = new ArrayList<>();

    public static void loadWorldsFromDirectory(String saveGameName) {
        File folder = new File(GameConfig.SAVE_PATH_OFFSET + saveGameName);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Asterania World File Filter", "asw");
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File f :
                    listOfFiles) {
                if (f.isFile()) {
                    if (filter.accept(f)) {
                        SAVE_GAME_WORLD_FILES.add(f);
                    }
                }
            }
            loadWorlds();
        }
    }

    private static void loadWorlds() {
        for (File f :
                SAVE_GAME_WORLD_FILES) {
            String worldName = removeExtension(f.getName());
            for (World w :
                    GameRegistry.getWorlds()) {
                if (w.getWorldName().equals(worldName)) {
                    WorldReader.loadWorld(f, w);
                    SAVE_GAME_WORLD.add(w);
                }
            }
        }
        AsteraniaMain.saveFile.getUniverse().getWorlds().addAll(SAVE_GAME_WORLD);
        AsteraniaMain.saveFile.getHomeWorld();
    }

    private static String removeExtension(String fileName) {
        if (fileName.endsWith(".asw")) {
            return fileName.substring(0, fileName.length() - 4);
        }
        LOGGER.severe("File \"" + fileName + "\" does not end with \".asw\". This is not good and might lead to a crash");
        return null;
    }
}
