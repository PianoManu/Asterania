package de.pianomanu.asterania.utils.file_utils;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.GameConfig;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.inventory.objects.InventoryObject;
import de.pianomanu.asterania.inventory.objects.InventoryObjectStack;
import de.pianomanu.asterania.registry.GameRegistry;
import de.pianomanu.asterania.utils.savegame.parsing.WorldReader;
import de.pianomanu.asterania.utils.savegame.parsing.WorldWriter;
import de.pianomanu.asterania.world.Worlds;
import de.pianomanu.asterania.world.direction.Direction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PlayerSaveUtils {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    public static Player loadPlayerFromSaveFile() {
        GameConfig.reload();
        Player tmp = new Player();
        File f = new File(GameConfig.PLAYER_DATA_SAVE_PATH);
        if (f.exists()) {
            String playerData = String.valueOf(WorldReader.readFile(f));
            readAndAssignPlayerData(playerData, tmp);

        } else {
            tmp.setCharacterPos(Worlds.HOME.getEntryPoint().toEntityCoordinates());
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
                loadInventory(attributes, player);
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
                String sub = playerData.substring(prevLineBreak, i);
                if (!sub.isEmpty())
                    lines.add(sub);
                prevLineBreak = i + 1;
            }
            if (i == playerData.length() - 1) {
                String sub = playerData.substring(prevLineBreak, i + 1);
                if (!sub.isEmpty())
                    lines.add(sub);
            }
        }
        return lines;
    }

    private static void loadInventory(List<String> inventoryLine, Player player) {
        int iOSSize = inventoryLine.size() - 1; //subtract 1, because first object in list is "INVENTORY"
        for (int i = 0; i < iOSSize; i++) {
            try {
                player.getPlayerInventory().addStackToInventory(parseIOS(inventoryLine.get(i + 1)));
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    private static InventoryObjectStack parseIOS(String iOSLine) {
        //sC = separatingCharacter, used when saving how many objects of one kind player has in inventory
        String sC = "*";
        int asteriskIndex = iOSLine.indexOf(sC);

        //asterisk found: string before asterisk equals number of inventory objects
        //                string after asterisk is name of inventory object
        if (asteriskIndex > 0) {
            String countS = iOSLine.substring(0, asteriskIndex);
            String inventoryObjectS = iOSLine.substring(asteriskIndex + 1);
            if (inventoryObjectS.equals("none"))
                return InventoryObjectStack.EMPTY;
            int count = Integer.parseInt(countS);
            InventoryObject inventoryObject = GameRegistry.getInventoryObjectFromString(inventoryObjectS);
            if (inventoryObject != null) {
                return new InventoryObjectStack(inventoryObject, count);
            } else {
                return InventoryObjectStack.EMPTY;
            }
        } else if (asteriskIndex == -1) {
            throw new IndexOutOfBoundsException("Malformed Inventory Object String: no \"*\" found");
        } else {
            throw new IndexOutOfBoundsException("Malformed Inventory Object String: \"*\" is not at a valid place");
        }
    }
}
