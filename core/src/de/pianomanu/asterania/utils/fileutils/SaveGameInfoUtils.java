package de.pianomanu.asterania.utils.fileutils;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.GameConfig;
import de.pianomanu.asterania.config.SavegameConfig;
import de.pianomanu.asterania.savegame.Savegame;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class SaveGameInfoUtils {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    public static void saveInfo() {
        LOGGER.finest("Got string containing savegame data, saving it now at " + SavegameConfig.getInstance().SAVEGAME_INFO_DATA_PATH);
        try {
            FileIO.writeFile(new File(SavegameConfig.getInstance().SAVEGAME_INFO_DATA_PATH), createSaveGameInfoString());
            LOGGER.finest("Got string containing savegame data, saved it  at " + SavegameConfig.getInstance().SAVEGAME_INFO_DATA_PATH);
        } catch (IOException e) {
            LOGGER.severe("An IO error occurred whilst saving the world!");
            e.printStackTrace();
        }
    }

    private static String createSaveGameInfoString() {
        Savegame s = AsteraniaMain.INSTANCE.getCurrentActiveSavegame();
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
        File f = new File(SavegameConfig.getInstance().SAVEGAME_INFO_DATA_PATH);
        if (f.exists()) {
            String saveGameInfo = String.valueOf(FileIO.readFile(f));
            addInfoToSaveGame(saveGameInfo);

        } else {
            LOGGER.warning("No Savegame info file found! This may lead to corrupt worlds!");
        }
    }

    public static void loadInfo(Savegame savegame) {
        String path = GameConfig.SAVEGAME_PATH_OFFSET + savegame.getName() + "\\" + GameConfig.SAVEGAME_INFO;
        File f = new File(path);
        if (f.exists()) {
            File info = new File(path);
            String saveGameInfo = String.valueOf(FileIO.readFile(info));
            addInfoToSaveGame(savegame, saveGameInfo);

        } else {
            LOGGER.warning("No Savegame info file found! This may lead to corrupt worlds!");
        }
    }

    private static void addInfoToSaveGame(Savegame savegame, String info) {
        List<String> lines = ParserUtils.splitString(info, '\n');
        for (String line : lines) {
            List<String> attributes = ParserUtils.splitString(line, ' ');
            if (attributes.get(0).equals("SEED")) {
                savegame.setSeed(Integer.parseInt(attributes.get(1)));
            }
            if (attributes.get(0).equals("DATE-OF-CREATION")) {
                savegame.setDateOfCreation(attributes.get(1));
            }
            if (attributes.get(0).equals("TOTAL-PLAYTIME")) {
                savegame.setTotalPlayTime(Long.parseLong(attributes.get(1)));
            }
        }
    }

    private static void addInfoToSaveGame(String info) {
        addInfoToSaveGame(AsteraniaMain.INSTANCE.getCurrentActiveSavegame(), info);
    }

    /*public static long getTotalPlaytime(Savegame file) {
        String info = loadInfo(SavegameConfig.SAVEGAME_PATH_OFFSET+file.getName());
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
