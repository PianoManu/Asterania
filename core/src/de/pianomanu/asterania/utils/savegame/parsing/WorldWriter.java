package de.pianomanu.asterania.utils.savegame.parsing;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.GameConfig;
import de.pianomanu.asterania.utils.file_utils.PlayerSaveUtils;
import de.pianomanu.asterania.utils.file_utils.SaveGameInfoUtils;
import de.pianomanu.asterania.utils.file_utils.SaveGameUtils;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.tile.TileType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class WorldWriter {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    public static void saveWorldContent(List<String> worldContents, String savegameDirectory, String worldName) {
        int sectionNumber = 0;
        for (String s : worldContents) {
            saveWorldContent(s, savegameDirectory, worldName + sectionNumber);
            sectionNumber++;
        }
    }

    public static void saveWorldContent(String worldContent, String savegameDirectory, String worldName) {
        LOGGER.finest("Got string containing world info, saving it now at " + GameConfig.SAVEGAME_PATH);
        File file = new File(savegameDirectory + "\\" + worldName + "." + GameConfig.WORLDSECTION_FILE_FORMAT);
        try {
            writeFile(file, worldContent);
        } catch (IOException e) {
            LOGGER.severe("An IO error occurred whilst saving the world!");
            e.printStackTrace();
        }
        LOGGER.finest("Got string containing world info, saved it at " + GameConfig.SAVEGAME_PATH);
    }

    /**
     * Writes the given content of a file with the name specified in the
     * parameter "file".
     *
     * @param file    name of a file to write content to.
     * @param content the content to write to the file.
     * @throws IOException if an I/O error occurs.
     */
    public static void writeFile(File file, String content) throws IOException {
        File newFile = new File(file.getAbsolutePath());
        try (FileWriter writer = new FileWriter(newFile.getAbsolutePath())) {
            writer.write(content);
        }
    }

    public static void saveGameInfo() {
        createVersionFile();
        saveAllWorlds();
        PlayerSaveUtils.savePlayerToSaveFile();
        SaveGameInfoUtils.saveInfo();
    }

    private static void saveAllWorlds() {
        for (World w :
                AsteraniaMain.currentActiveSavegame.getUniverse().getWorlds()) {
            //Welt in eigenem Order speichern
            File savegameDirectory = new File(SaveGameUtils.getSavegameWorldSubdirectory(AsteraniaMain.currentActiveSavegame.getName(), w.getWorldName()));
            savegameDirectory.mkdir();
            WorldWriter.saveWorldContent(WorldSectionComposer.createWorldContentString(w, TileType.BACKGROUND), savegameDirectory.getAbsolutePath(), w.getWorldName());
            WorldWriter.saveWorldContent(WorldSectionComposer.createWorldContentString(w, TileType.DECORATION), savegameDirectory.getAbsolutePath(), w.getWorldName() + GameConfig.DECORATION_LAYER_SUFFIX);
        }
    }

    private static void createVersionFile() {
        LOGGER.finest("Got string containing version number, saving it now at " + GameConfig.VERSION_SAVE_PATH);
        File file = new File(GameConfig.VERSION_SAVE_PATH);
        StringBuilder builder = new StringBuilder("VERSION ").append(GameConfig.GAME_VERSION);
        try {
            writeFile(file, builder.toString());
        } catch (IOException e) {
            LOGGER.severe("An IO error occurred whilst creating the version file!");
            e.printStackTrace();
        }
        LOGGER.finest("Got string containing version number, saved it at " + GameConfig.VERSION_SAVE_PATH);
    }
}
