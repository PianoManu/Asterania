package de.pianomanu.asterania.utils.file_utils;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.GameConfig;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.world.direction.Direction;
import de.pianomanu.asterania.world.worldsections.WorldReader;
import de.pianomanu.asterania.world.worldsections.WorldWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PlayerSaveUtils {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    public static Player loadPlayerFromSaveFile() {
        Player tmp = new Player();
        File f = new File(GameConfig.PLAYER_DATA_SAVE_PATH);
        if (f.exists()) {
            String playerData = String.valueOf(WorldReader.readFile(f));
            readAndAssignPlayerData(playerData, tmp);

        }

        return tmp;
    }

    public static void savePlayerToSaveFile() {
        LOGGER.finest("Got string containing player data, saving it now at " + GameConfig.PLAYER_DATA_SAVE_PATH);
        try {
            WorldWriter.writeFile(new File(GameConfig.PLAYER_DATA_SAVE_PATH), createPlayerDataString());
            LOGGER.finest("Got string containing player data, saved it  at " + GameConfig.PLAYER_DATA_SAVE_PATH);
        } catch (IOException e) {
            LOGGER.severe("An IO error occurred whilst saving the world!");
            e.printStackTrace();
        }
    }

    private static String createPlayerDataString() {
        Player player = AsteraniaMain.player;
        StringBuilder builder = new StringBuilder();
        builder.append("POSITION ").append(player.getCharacterPos().x).append(" ").append(player.getCharacterPos().y).append("\n");
        builder.append("DIRECTION ").append(player.getPlayerFacing()).append("\n");
        //save Inventory
        builder.append(player.getPlayerInventory().toSaveFileString()).append("\n");
        builder.append("MAXINVENTORYCAPACITY ").append(player.getMaxWeight());

        return builder.toString();
    }

    private static void readAndAssignPlayerData(String playerData, Player player) {
        List<String> lines = splitString(playerData, '\n');
        for (String s : lines) {
            List<String> attributes = splitString(s, ' ');
            String first = attributes.get(0);
            if (first.equals("POSITION")) {
                player.setCharacterPos(Float.parseFloat(attributes.get(1)), Float.parseFloat(attributes.get(2)));
            }
            if (first.equals("DIRECTION")) {
                player.setPlayerFacing(Direction.toDirection(attributes.get(1)));
            }
            if (first.equals("INVENTORY")) {
                //TODO
            }
            if (first.equals("MAXINVENTORYCAPACITY")) {
                player.setMaxWeight(Float.parseFloat(attributes.get(1)));
            }
        }
    }

    private static List<String> splitString(String playerData, char separatingCharacter) {
        List<String> lines = new ArrayList<>();
        int prevLineBreak = 0;
        for (int i = 0; i < playerData.length(); i++) {
            if (playerData.charAt(i) == separatingCharacter) {
                //TODO i correct? or i, i-1?
                lines.add(playerData.substring(prevLineBreak, i));
                prevLineBreak = i + 1;
            }
            if (i == playerData.length() - 1) {
                lines.add(playerData.substring(prevLineBreak, i + 1));
            }
        }
        return lines;
    }

    private static void loadInventory(String inventoryLine, Player player) {

    }
}
