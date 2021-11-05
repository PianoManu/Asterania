package de.pianomanu.asterania.lifecycle;

import de.pianomanu.asterania.world.World;

public class GameLifeCycleUpdates {

    public static void update(World world, float delta) {
        /*for (Player p : world.getPlayers()) {
            PlayerUpdates.updatePlayer(p);
        }*/
        PlayerUpdates.updatePlayer(world.getPlayer(), delta);
    }
}
