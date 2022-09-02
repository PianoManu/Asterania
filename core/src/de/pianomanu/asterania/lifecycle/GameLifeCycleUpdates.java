package de.pianomanu.asterania.lifecycle;

import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.world.World;

public class GameLifeCycleUpdates {

    public static void update(World world, Player player) {
        PlayerUpdates.updatePlayer(world, player);
        TileUpdates.updateTiles();
        ChatUpdates.updateChat();
    }
}
