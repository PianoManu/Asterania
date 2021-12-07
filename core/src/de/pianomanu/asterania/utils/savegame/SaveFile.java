package de.pianomanu.asterania.utils.savegame;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.GameConfig;
import de.pianomanu.asterania.utils.DateUtils;
import de.pianomanu.asterania.world.World;

import java.io.File;
import java.util.Random;
import java.util.logging.Logger;

public class SaveFile {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    private final String name;
    private Universe universe;
    private World homeWorld;
    private final Random random;
    private int seed;
    private String dateOfCreation;
    private long totalPlayTime = 0;
    private long startTime;
    private long quitTime;

    public SaveFile(String name) {
        this.name = name;
        this.universe = new Universe();

        if (new File(GameConfig.SAVEGAME_PATH_OFFSET + name).mkdir()) {
            LOGGER.fine("Created directory \"" + name + "\" as save directory!");
        } else {
            LOGGER.finest("Found save directory \"" + name + "\"!");
        }
        if (GameConfig.SEED == 0) {
            this.seed = (int) (Math.random() * Integer.MAX_VALUE);
            this.random = new Random(this.seed);
        } else {
            this.seed = GameConfig.SEED;
            this.random = new Random(GameConfig.SEED);
        }
        this.dateOfCreation = DateUtils.calcDate();
        this.startTime = System.currentTimeMillis();
    }

    public Universe getUniverse() {
        return this.universe;
    }

    public World getHomeWorld() {
        for (World w : this.universe.getWorlds()) {
            if (w.getWorldName().equals("home")) {
                this.homeWorld = w;
                break;
            }
        }
        return this.homeWorld;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "SaveFile{\"" + this.name + "\"" + ", universe:" + this.universe + '}';
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
}
