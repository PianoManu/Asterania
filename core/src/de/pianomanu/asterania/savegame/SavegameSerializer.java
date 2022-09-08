package de.pianomanu.asterania.savegame;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.GameConfig;
import de.pianomanu.asterania.utils.fileutils.FileIO;
import de.pianomanu.asterania.utils.fileutils.PlayerSaveUtils;
import de.pianomanu.asterania.utils.fileutils.SaveGameInfoUtils;
import de.pianomanu.asterania.utils.fileutils.SaveGameUtils;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.entities.Player;
import de.pianomanu.asterania.world.tile.Tile;
import de.pianomanu.asterania.world.tile.tileutils.LayerType;
import de.pianomanu.asterania.world.worldsections.WorldSection;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

//Savegame -> String
public class SavegameSerializer {
    //sC = separatingCharacter ... for better readability
    private static final char S_C = '|';
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    public static void saveGame() {
        saveGame(AsteraniaMain.currentActiveSavegame);
    }

    private static void saveWorldContent(List<String> worldContents, String savegameDirectory, String worldName) {
        int sectionNumber = 0;
        for (String s : worldContents) {
            saveWorldContent(s, savegameDirectory, worldName + sectionNumber);
            sectionNumber++;
        }
    }

    private static void saveWorldContent(String worldContent, String savegameDirectory, String worldName) {
        LOGGER.finest("Got string containing world info, saving it now at " + GameConfig.SAVEGAME_PATH);
        File file = new File(savegameDirectory + "\\" + worldName + "." + GameConfig.WORLDSECTION_FILE_FORMAT);
        try {
            FileIO.writeFile(file, worldContent);
        } catch (IOException e) {
            LOGGER.severe("An IO error occurred whilst saving the world!");
            e.printStackTrace();
        }
        LOGGER.finest("Got string containing world info, saved it at " + GameConfig.SAVEGAME_PATH);
    }

    private static void saveGame(Savegame savegame) {
        Player player = savegame.getCurrentActivePlayer();
        createVersionFile();
        saveAllWorlds();
        PlayerSaveUtils.savePlayerToSaveFile(player);
        SaveGameInfoUtils.saveInfo();
    }

    private static void saveAllWorlds() {
        for (World w :
                AsteraniaMain.currentActiveSavegame.getUniverse().getWorlds()) {
            //Welt in eigenem Order speichern
            File savegameDirectory = new File(SaveGameUtils.getSavegameWorldSubdirectory(AsteraniaMain.currentActiveSavegame.getName(), w.getWorldName()));
            savegameDirectory.mkdir();
            SavegameSerializer.saveWorldContent(createWorldContentString(w, LayerType.BACKGROUND), savegameDirectory.getAbsolutePath(), w.getWorldName());
            SavegameSerializer.saveWorldContent(createWorldContentString(w, LayerType.DECORATION), savegameDirectory.getAbsolutePath(), w.getWorldName() + GameConfig.DECORATION_LAYER_SUFFIX);
        }
    }

    private static void createVersionFile() {
        LOGGER.finest("Got string containing version number, saving it now at " + GameConfig.VERSION_SAVE_PATH);
        File file = new File(GameConfig.VERSION_SAVE_PATH);
        StringBuilder builder = new StringBuilder("VERSION ").append(GameConfig.GAME_VERSION);
        try {
            FileIO.writeFile(file, builder.toString());
        } catch (IOException e) {
            LOGGER.severe("An IO error occurred whilst creating the version file!");
            e.printStackTrace();
        }
        LOGGER.finest("Got string containing version number, saved it at " + GameConfig.VERSION_SAVE_PATH);
    }

    private static List<String> createWorldContentString(World world, LayerType layerType) {
        LOGGER.fine("Creating WorldSection string for layer " + layerType.toString() + " out of " + world.getSections().size() + " world sections...");
        List<String> sections = new ArrayList<>();

        for (WorldSection s :
                world.getSections()) {
            if (s != null)
                sections.add(createWorldSectionString(s, layerType));
        }
        LOGGER.fine("Created WorldSection string for layer " + layerType.toString() + " out of " + world.getSections().size() + " world sections!");
        return sections;
    }

    private static String createWorldSectionString(WorldSection section, LayerType layerType) {
        StringBuilder builder = new StringBuilder();
        //World section position
        builder.append(section.sectionPos.x).append(S_C).append(section.sectionPos.y).append("\n");

        Tile previousTile = switch (layerType) {
            case DECORATION -> section.getDecorationLayerTileRelativeCoordinates(0, 0);
            case BACKGROUND -> section.getTileRelativeCoordinates(0, 0);
        };
        Tile newTile;
        int tileCounter = 0;
        for (int x = 0; x < WorldSection.SECTION_SIZE; x++) {
            for (int y = 0; y < WorldSection.SECTION_SIZE; y++) {
                newTile = switch (layerType) {
                    case DECORATION -> section.getDecorationLayerTileRelativeCoordinates(x, y);
                    case BACKGROUND -> section.getTileRelativeCoordinates(x, y);
                };
                if (newTile == previousTile) {
                    tileCounter++;
                } else {
                    if (previousTile != null)
                        builder.append(tileCounter).append("*").append(previousTile.getSaveFileString()).append(S_C);
                    else
                        builder.append(tileCounter).append("*").append("null").append(S_C);
                    previousTile = newTile;
                    tileCounter = 1;
                }
                if (x == WorldSection.SECTION_SIZE - 1 && y == WorldSection.SECTION_SIZE - 1) {
                    if (previousTile != null)
                        builder.append(tileCounter).append("*").append(previousTile.getSaveFileString());
                    else
                        builder.append(tileCounter).append("*").append("null");
                }
            }
        }
        return builder.append("\n").toString();
    }
}
