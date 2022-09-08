package de.pianomanu.asterania.utils.fileutils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileIO {
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
