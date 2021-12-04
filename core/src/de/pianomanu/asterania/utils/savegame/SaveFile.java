package de.pianomanu.asterania.utils.savegame;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.GameConfig;
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

    public SaveFile(String name) {
        this.name = name;
        this.universe = new Universe();

        if (new File(GameConfig.SAVEGAME_PATH_OFFSET + name).mkdir()) {
            LOGGER.fine("Created directory \"" + name + "\" as save directory!");
        } else {
            LOGGER.finest("Found save directory \"" + name + "\"!");
        }
        if (GameConfig.SEED != 0) {
            this.random = new Random();
        } else {
            this.random = new Random(GameConfig.SEED);
        }
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
}
