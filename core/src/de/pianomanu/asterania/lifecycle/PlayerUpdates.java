package de.pianomanu.asterania.lifecycle;

import com.badlogic.gdx.Gdx;
import de.pianomanu.asterania.config.KeyConfig;
import de.pianomanu.asterania.entities.Player;

public class PlayerUpdates extends GameLifeCycleUpdates {
    protected static void updatePlayer(Player player, float delta) {
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_RIGHT)) {
            player.setMoving();
            player.moveRight(delta);
        }
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_LEFT)) {
            player.setMoving();
            player.moveLeft(delta);
        }
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_UP)) {
            player.setMoving();
            player.moveUp(delta);
        }
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_DOWN)) {
            player.setMoving();
            player.moveDown(delta);
        }
        if (player.isMoving())
            if (!Gdx.input.isKeyPressed(KeyConfig.MOVE_RIGHT) && !Gdx.input.isKeyPressed(KeyConfig.MOVE_LEFT) && !Gdx.input.isKeyPressed(KeyConfig.MOVE_UP) && !Gdx.input.isKeyPressed(KeyConfig.MOVE_DOWN))
                player.setStanding();
    }
}
