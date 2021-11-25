package de.pianomanu.asterania.savegame;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.world.World;

import java.io.File;
import java.util.logging.Logger;

public class SaveFile {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    private final String name;
    private Universe universe = null;
    private World homeWorld;

    public SaveFile(String name) {
        this.name = name;

        if (new File(name).mkdir()) {
            LOGGER.info("Created directory " + name + " as save directory!");
        } else {
            LOGGER.severe("Could not create directory " + name + ". Does it exist already?");
            LOGGER.severe("You should change the name or move the directory before. Otherwise, your existing files might become corrupted or destroyed");
        }
    }

    public void loadUniverse() {
        //TODO: load from file
        this.universe = new Universe();
        this.universe.load();

        for (World w : this.universe.getWorlds()) {
            if (w.getWorldName().equals("home"))
                this.homeWorld = w;
        }
    }

    public Universe getUniverse() {
        return this.universe;
    }

    public World getHomeWorld() {
        return this.homeWorld;
    }

    public String getName() {
        return name;
    }
}
