package de.pianomanu.asterania.utils.savegame.parsing;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.world.World;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class WorldReader {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    private static List<String> linesOfFile = new ArrayList<>();

    public static void loadWorld(File file, World world) {
        LOGGER.fine("Starting world " + world.getWorldName() + " from file " + file.getName());
        String content = String.valueOf(readFile(file));
        int lowerBound = 0;
        for (int i = 0; i < content.length(); i++) {
            if (content.charAt(i) == '\n') {
                linesOfFile.add(content.substring(lowerBound, i));
                lowerBound = i;
            }
        }
        LOGGER.fine("Found " + linesOfFile.size() + " lines of world information");
        world.loadWorldSections(WorldSectionParser.getWSfromString(file.getName(), linesOfFile));
        LOGGER.fine("Started world " + world.getWorldName() + " from file " + file.getName());

        //reinitialize world loading string list
        linesOfFile = new ArrayList<>();
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
