package de.pianomanu.asterania.savegame;

import de.pianomanu.asterania.registry.GameRegistry;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.Worlds;

import java.util.ArrayList;
import java.util.List;

public class Universe {
    private final List<World> worlds = new ArrayList<>();

    public Universe() {

    }

    public void load() {
        //TODO
        Worlds.HOME.getWorldName();
        this.worlds.addAll(GameRegistry.getWorlds());
    }

    public List<World> getWorlds() {
        return this.worlds;
    }
}
