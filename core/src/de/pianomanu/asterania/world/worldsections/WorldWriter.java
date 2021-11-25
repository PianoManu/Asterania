package de.pianomanu.asterania.world.worldsections;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.GameConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

public class WorldWriter {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    public static void saveWorldContent(String worldContent, String worldName) {
        LOGGER.finest("Got string containing world info, saving it now at " + GameConfig.SAVE_NAME);
        File file = new File(GameConfig.SAVE_NAME + "\\" + worldName);
        try {
            writeFile(file, worldContent);
        } catch (IOException e) {
            LOGGER.severe("An IO error occurred whilst saving the world!");
            e.printStackTrace();
        }
        LOGGER.finest("Got string containing world info, saved it at " + GameConfig.SAVE_NAME);
    }

    /**
     * Writes the given content of a file with the name specified in the
     * parameter "file".
     *
     * @param file    name of a file to write content to.
     * @param content the content to write to the file.
     * @throws IOException if an I/O error occurs.
     */
    private static void writeFile(File file, String content) throws IOException {
        File newFile = new File(file.getAbsolutePath());
        try (FileWriter writer = new FileWriter(newFile.getAbsolutePath())) {
            writer.write(content);
        }
    }
}
