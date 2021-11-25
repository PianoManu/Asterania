package de.pianomanu.asterania.world;

import de.pianomanu.asterania.registry.GameRegistry;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;

public class Worlds {
    //TODO
    public static final World HOME = r(new World(new TileCoordinates(5, 8), "home"));
    public static final World MINE = r(new World(new TileCoordinates(32, 32), "mine"));

    public Worlds() {
    }

    private static World r(World world) {
        GameRegistry.registerWorld(world);
        return world;
    }
}
