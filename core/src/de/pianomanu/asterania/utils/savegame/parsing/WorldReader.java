package de.pianomanu.asterania.utils.savegame.parsing;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.worldsections.WorldSection;

import java.io.File;
import java.io.FileReader;
import java.util.logging.Logger;

public class WorldReader {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    public static WorldSection loadWorldSection(File backgroundLayer, File decorationLayer, World world) {
        LOGGER.finest("Adding content of file " + backgroundLayer.getName() + " to world " + world.getWorldName());
        String backgroundContent = String.valueOf(readFile(backgroundLayer));
        String decorationContent = String.valueOf(readFile(decorationLayer));

        LOGGER.finest("Added content of file " + backgroundLayer.getName() + " to world " + world.getWorldName());
        return WorldSectionParser.getWSfromString(backgroundContent, decorationContent);
    }

    /**
     * Reads the content of a file with the name specified in the parameter
     * "file".
     *
     * @param file name of a file to read content from.
     * @return the content of the file with the name given as parameter
     * as byte array.
     */
    public static char[] readFile(File file) {
        // Creates an array of character
        char[] array = new char[65536];

        try {
            // Creates a reader using the FileReader
            FileReader input = new FileReader(file.getAbsolutePath());

            // Reads characters
            input.read(array);

            // Closes the reader
            input.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
        return array;
    }
}
