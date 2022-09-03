package de.pianomanu.asterania.utils.file_utils;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.GameConfig;
import de.pianomanu.asterania.utils.savegame.Savegame;
import de.pianomanu.asterania.utils.savegame.parsing.WorldReader;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.Worlds;
import de.pianomanu.asterania.world.worldsections.WorldSection;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SaveGameUtils {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    public static final List<World> SAVE_GAME_WORLD = new ArrayList<>();

    public static String getSavegameDirectory(String savegameName) {
        return GameConfig.SAVEGAME_PATH_OFFSET + savegameName;
    }

    public static String getSavegameWorldDirectory(String savegameName) {
        return getSavegameDirectory(savegameName) + "\\" + GameConfig.WORLDS_SAVE_DIR_NAME;
    }

    public static String getSavegameWorldSubdirectory(String savegameName, String worldName) {
        return getSavegameWorldDirectory(savegameName) + "\\" + worldName;
    }

    @Deprecated
    public static void createNewGame() {
        File savesDir = new File(GameConfig.SAVEGAME_PATH_OFFSET);
        savesDir.mkdir();
        File mainDir = new File(GameConfig.SAVEGAME_PATH);
        mainDir.mkdir();

        createStartWorldForNewGame();
    }

    private static void createStartWorldForNewGame() {
        File folder = new File(GameConfig.WORLDS_SAVE_PATH);
        folder.mkdir();
        SAVE_GAME_WORLD.add(Worlds.HOME);
        AsteraniaMain.currentActiveSavegame.getUniverse().getWorlds().addAll(SAVE_GAME_WORLD);
    }

    private static String removeExtension(String fileName) {
        if (fileName.endsWith("." + GameConfig.WORLDSECTION_FILE_FORMAT)) {
            return fileName.substring(0, fileName.length() - (GameConfig.WORLDSECTION_FILE_FORMAT.length() + 1));
        }
        LOGGER.severe("File \"" + fileName + "\" does not end with \"" + GameConfig.WORLDSECTION_FILE_FORMAT + "\". This is not good and might lead to a crash");
        return null;
    }

    public static Savegame loadSavegame(String savegameName) {
        Savegame savegame = new Savegame(savegameName);
        loadWorldsFromSavegame(savegame);

        return savegame;
    }

    public static void loadWorldsFromSavegame(Savegame savegame) {
        File folder = new File(SaveGameUtils.getSavegameWorldDirectory(savegame.getName()));
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File f :
                    listOfFiles) {
                if (f.isDirectory()) {
                    World world = Worlds.getWorldByName(f.getName());
                    if (world != null) {
                        loadWorldSectionsIntoSavegame(savegame, f, world);
                    }
                }
            }
        }
    }

    private static void loadWorldSectionsIntoSavegame(Savegame savegame, File worldFolder, World world) {
        savegame.getUniverse().getWorlds().add(world);
        File[] listOfFiles = worldFolder.listFiles();
        List<File> backgroundLayerFiles = new ArrayList<>();
        List<File> decorationLayerFiles = new ArrayList<>();
        if (listOfFiles != null) {
            sortLayerFiles(listOfFiles, backgroundLayerFiles, decorationLayerFiles);
            world.loadWorldSections(findMatchingLayersAndMerge(backgroundLayerFiles, decorationLayerFiles, world));
        } else {
            //TODO
        }
    }

    private static void sortLayerFiles(File[] listOfFiles, List<File> backgroundLayerFiles, List<File> decorationLayerFiles) {
        for (File f :
                listOfFiles) {
            if (f.getName().contains(GameConfig.DECORATION_LAYER_SUFFIX)) {
                decorationLayerFiles.add(f);
            } else {
                backgroundLayerFiles.add(f);
            }
        }
    }

    private static List<WorldSection> findMatchingLayersAndMerge(List<File> backgroundLayerFiles, List<File> decorationLayerFiles, World world) {
        List<WorldSection> sections = new ArrayList<>();
        for (File deco :
                decorationLayerFiles) {
            if (deco.getName().contains(GameConfig.DECORATION_LAYER_SUFFIX)) {
                String namePlain = deco.getName().replace(GameConfig.DECORATION_LAYER_SUFFIX, "");
                for (File background :
                        backgroundLayerFiles) {
                    if (background.getName().contains(namePlain)) {
                        sections.add(WorldReader.loadWorldSection(background, deco, world));
                    }
                }
            }
        }
        return sections;
    }
}
