package de.pianomanu.asterania.savegame;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.GameConfig;
import de.pianomanu.asterania.config.SavegameConfig;
import de.pianomanu.asterania.utils.fileutils.PlayerSaveUtils;
import de.pianomanu.asterania.utils.fileutils.SaveGameInfoUtils;
import de.pianomanu.asterania.utils.math.DateUtils;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.entities.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class Savegame {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    private final String name;
    private Universe universe;
    private final Random random;
    private int seed;
    private String dateOfCreation;
    private long totalPlayTime = 0;
    private long startTime;
    private long quitTime;
    private final List<Player> playersOfSavegame = new ArrayList<>();
    private Player currentActivePlayer;
    private final SavegameConfig config;

    private Savegame(String name) {
        this.name = name;
        this.universe = new Universe();

        if (SavegameConfig.SEED == 0) {//TODO create seed input
            this.seed = (int) (Math.random() * Integer.MAX_VALUE);
            this.random = new Random(this.seed);
        } else {
            this.seed = SavegameConfig.SEED;
            this.random = new Random(SavegameConfig.SEED);
        }
        this.currentActivePlayer = PlayerSaveUtils.loadPlayerFromSaveFile(name);
        this.dateOfCreation = DateUtils.calcDate();
        this.startTime = System.currentTimeMillis();
        this.config = new SavegameConfig(name);
    }

    public static Savegame loadSavegame(String savegameName) {
        if (savegameExists(savegameName)) {
            Savegame savegame = new Savegame(savegameName);
            return SavegameDeserializer.loadGame(savegame);
        } else {
            return new Savegame(savegameName);
        }
    }

    public static Savegame loadSavegameInfo(String savegameName) {
        Savegame tmp = new Savegame(savegameName);
        SaveGameInfoUtils.loadInfo(tmp);
        return tmp;
    }

    private static boolean savegameExists(String savegameName) {
        if (new File(GameConfig.SAVEGAME_PATH_OFFSET + savegameName).mkdir()) {
            LOGGER.fine("Created directory \"" + savegameName + "\" as save directory!");
            return false;
        }
        LOGGER.finest("Found save directory \"" + savegameName + "\"!");
        return true;
    }

    public Universe getUniverse() {
        return this.universe;
    }

    public World getHomeWorld() {
        for (World w : this.universe.getWorlds()) {
            if (w.getWorldName().equals("home")) {
                return w;
            }
        }
        return null;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "Savegame{\"" + this.name + "\"" + ", universe:" + this.universe + '}';
    }

    public Random getRandomNumberGenerator() {
        return this.random;
    }

    public int getSeed() {
        return this.seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public String getDateOfCreation() {
        return this.dateOfCreation;
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public long calcTotalPlayTime() {
        this.quitTime = System.currentTimeMillis();
        this.totalPlayTime += this.quitTime - this.startTime;
        return this.totalPlayTime;
    }

    public long getTotalPlayTime() {
        return this.totalPlayTime;
    }

    public void setTotalPlayTime(long totalPlayTime) {
        this.totalPlayTime = totalPlayTime;
    }

    public void resetStartTime() {
        this.startTime = System.currentTimeMillis();
    }

    public Player getCurrentActivePlayer() {
        return currentActivePlayer;
    }

    public void setCurrentActivePlayer(Player currentActivePlayer) {
        this.currentActivePlayer = currentActivePlayer;
    }

    public SavegameConfig getGameConfig() {
        return this.config;
    }
}
