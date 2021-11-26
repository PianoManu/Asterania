package de.pianomanu.asterania.savegame;

import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.Worlds;

import java.util.ArrayList;
import java.util.List;

public class Universe {
    private final List<World> worlds = new ArrayList<>();

    public Universe() {

    }

    public void load(String saveFileName) {
        //TODO remove getWorldName if Worlds gets loaded somehow else
        Worlds.HOME.getWorldName();
        //SaveGameUtils.loadWorldsFromDirectory(saveFileName);
        //this.worlds.addAll(GameRegistry.getWorlds());
        //this.worlds.addAll(SaveGameUtils.SAVE_GAME_WORLD);
    }

    public List<World> getWorlds() {
        return this.worlds;
    }
}
