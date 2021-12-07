package de.pianomanu.asterania.world;

import de.pianomanu.asterania.registry.GameRegistry;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;

public class Worlds {
    //TODO
    public static final World HOME = r(new World("home", new TileCoordinates(32, 32)));
    public static final World MINE = r(new World("mine", new TileCoordinates(32, 32), WorldType.UNDERGROUND));

    public Worlds() {
    }

    private static World r(World world) {
        GameRegistry.registerWorld(world);
        return world;
    }
}
