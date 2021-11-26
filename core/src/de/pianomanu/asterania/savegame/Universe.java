package de.pianomanu.asterania.savegame;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Universe {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    private final List<World> worlds = new ArrayList<>();
    private int worldIndex = 0;

    public Universe() {

    }

    public void load(String saveFileName) {
        //TODO remove getWorldName if Worlds gets loaded somehow else
        //Worlds.HOME.getWorldName();
    }

    public List<World> getWorlds() {
        return this.worlds;
    }

    public int getWorldIndex() {
        return this.worldIndex;
    }

    public void setWorldIndex(int worldIndex) {
        this.worldIndex = worldIndex;
    }

    public World getWorld(int worldIndex) {
        if (worldIndex >= 0 && worldIndex < this.getWorlds().size())
            return this.getWorlds().get(worldIndex);
        LOGGER.severe("Unable to get world with index " + worldIndex + ", as it is not between the bounds of 0 and " + (this.getWorlds().size() - 1) + "!");
        return null;
    }

    public World getCurrentWorld() {
        return getWorld(this.worldIndex);
    }

    public World getNextWorld() {
        this.worldIndex++;
        if (this.worldIndex == this.getWorlds().size())
            this.worldIndex = 0;
        return getCurrentWorld();
    }

    public World getPreviousWorld() {
        this.worldIndex--;
        if (this.worldIndex == -1)
            this.worldIndex = this.getWorlds().size() - 1;
        return getCurrentWorld();
    }
}
