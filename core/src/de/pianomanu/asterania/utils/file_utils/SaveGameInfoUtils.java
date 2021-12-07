package de.pianomanu.asterania.utils.file_utils;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.GameConfig;
import de.pianomanu.asterania.utils.savegame.SaveFile;
import de.pianomanu.asterania.utils.savegame.parsing.WorldReader;
import de.pianomanu.asterania.utils.savegame.parsing.WorldWriter;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class SaveGameInfoUtils {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    public static void saveInfo() {
        LOGGER.finest("Got string containing savegame data, saving it now at " + GameConfig.SAVEGAMEINFO_DATA_PATH);
        try {
            WorldWriter.writeFile(new File(GameConfig.SAVEGAMEINFO_DATA_PATH), createSaveGameInfoString());
            LOGGER.finest("Got string containing savegame data, saved it  at " + GameConfig.SAVEGAMEINFO_DATA_PATH);
        } catch (IOException e) {
            LOGGER.severe("An IO error occurred whilst saving the world!");
            e.printStackTrace();
        }
    }

    private static String createSaveGameInfoString() {
        SaveFile s = AsteraniaMain.saveFile;
        StringBuilder builder = new StringBuilder();

        builder.append(i("SEED", s.getSeed() + ""));
        builder.append(i("DATE-OF-CREATION", s.getDateOfCreation()));
        builder.append(i("TOTAL-PLAYTIME", s.calcTotalPlayTime() + ""));

        return builder.toString();
    }

    //i: info ->
    private static String i(String key, String value) {
        return key + " " + value + '\n';
    }


    public static void loadInfo() {
        File f = new File(GameConfig.SAVEGAMEINFO_DATA_PATH);
        if (f.exists()) {
            String saveGameInfo = String.valueOf(WorldReader.readFile(f));
            addInfoToSaveGame(saveGameInfo);

        } else {
            LOGGER.warning("No Savegame info file found! This may lead to corrupt worlds!");
        }
    }

    public static void loadInfo(SaveFile file, String path) {
        File f = new File(path);
        if (f.exists()) {
            File info = new File(path + "\\savegameinfo");
            String saveGameInfo = String.valueOf(WorldReader.readFile(info));
            addInfoToSaveGame(file, saveGameInfo);

        } else {
            LOGGER.warning("No Savegame info file found! This may lead to corrupt worlds!");
        }
    }

    private static void addInfoToSaveGame(SaveFile s, String info) {
        List<String> lines = ParserUtils.splitString(info, '\n');
        for (String line : lines) {
            List<String> attributes = ParserUtils.splitString(line, ' ');
            if (attributes.get(0).equals("SEED")) {
                s.setSeed(Integer.parseInt(attributes.get(1)));
            }
            if (attributes.get(0).equals("DATE-OF-CREATION")) {
                s.setDateOfCreation(attributes.get(1));
            }
            if (attributes.get(0).equals("TOTAL-PLAYTIME")) {
                s.setTotalPlayTime(Long.parseLong(attributes.get(1)));
            }
        }
    }

    private static void addInfoToSaveGame(String info) {
        addInfoToSaveGame(AsteraniaMain.saveFile, info);
    }

    /*public static long getTotalPlaytime(SaveFile file) {
        String info = loadInfo(GameConfig.SAVEGAME_PATH_OFFSET+file.getName());
        List<String> lines = ParserUtils.splitString(info, '\n');
        for (String line : lines) {
            List<String> attributes = ParserUtils.splitString(line, ' ');
            if (attributes.get(0).equals("TOTAL-PLAYTIME")) {
                return Long.parseLong(attributes.get(1));
            }
        }
        return 0;
    }*/
}
