package de.pianomanu.asterania.world.worldsections;

import de.pianomanu.asterania.world.World;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class WorldReader {
    private static List<String> linesOfFile = new ArrayList<>();

    public static void loadWorld(File file, World world) {
        String content = String.valueOf(readFile(file));
        int lowerBound = 0;
        for (int i = 0; i < content.length(); i++) {
            if (content.charAt(i) == '\n') {
                linesOfFile.add(content.substring(lowerBound, i));
                lowerBound = i;
            }
        }
        System.out.println(linesOfFile.size());
        world.loadWorldSections(WorldSectionParser.getWSfromString(linesOfFile));
    }

    /**
     * Reads the content of a file with the name specified in the parameter
     * "file".
     *
     * @param file name of a file to read content from.
     * @return the content of the file with the name given as parameter
     * as byte array.
     */
    private static char[] readFile(File file) {
        /*List<Character> buffer = new ArrayList<>();
        FileReader reader;

        try {
            reader = new FileReader(file.getAbsolutePath());

            //entweder | oder Dateiende
            while (true) {
                int readCharacter = reader.read();
                buffer.add();
                if (readCharacter == 124)
                    System.out.println();
                if (readCharacter == -1)
                    return buffer;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        // Creates an array of character
        char[] array = new char[65536];

        try {
            // Creates a reader using the FileReader
            FileReader input = new FileReader(file.getAbsolutePath());

            // Reads characters
            input.read(array);
            //System.out.println("Data in the file: ");
            //System.out.println(array);

            // Closes the reader
            input.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
        return array;
    }
}
