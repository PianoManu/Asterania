package de.pianomanu.asterania.lifecycle;

import de.pianomanu.asterania.world.World;

public class GameLifeCycleUpdates {

    public static void update(World world, float delta) {
        PlayerUpdates.updatePlayer(world, delta);
    }
}
