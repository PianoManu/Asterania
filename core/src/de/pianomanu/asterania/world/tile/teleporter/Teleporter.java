package de.pianomanu.asterania.world.tile.teleporter;

import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.coordinates.EntityCoordinates;

public class Teleporter {
    private World destinyWorld;
    private EntityCoordinates destinyCoordinates = null;

    public Teleporter(World destinyWorld, EntityCoordinates destinyCoordinates) {
        this.destinyWorld = destinyWorld;
        this.destinyCoordinates = destinyCoordinates;
    }

    public Teleporter() {
        this.destinyWorld = null;
    }

    public World getDestinyWorld() {
        return this.destinyWorld;
    }

    public void setDestinyWorld(World destinyWorld) {
        this.destinyWorld = destinyWorld;
        this.destinyCoordinates = destinyWorld.getEntryPoint().toEntityCoordinates();
    }

    public EntityCoordinates getDestinyCoordinates() {
        return this.destinyCoordinates;
    }

    public void setDestinyCoordinates(EntityCoordinates destinyCoordinates) {
        this.destinyCoordinates = destinyCoordinates;
    }
}
