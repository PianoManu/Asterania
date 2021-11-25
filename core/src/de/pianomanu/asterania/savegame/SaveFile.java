package de.pianomanu.asterania.savegame;

import de.pianomanu.asterania.world.World;

import java.io.File;

public class SaveFile {
    private final String name;
    private Universe universe = null;
    private World homeWorld;

    public SaveFile(String name) {
        this.name = name;

        new File(name).mkdir();
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
