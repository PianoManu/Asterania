package de.pianomanu.asterania.world.worldsections;

import de.pianomanu.asterania.config.GameConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WorldWriter {

    public static void saveWorldContent(String worldContent) {
        File file = new File(GameConfig.SAVE_NAME);
        try {
            writeFile(file, worldContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
