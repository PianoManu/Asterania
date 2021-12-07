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
        builder.append(i("TOTAL-PLAYTIME", s.getTotalPlayTime() + ""));

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

    private static void addInfoToSaveGame(String info) {
        SaveFile s = AsteraniaMain.saveFile;
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
}
