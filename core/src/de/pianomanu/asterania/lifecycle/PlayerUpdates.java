package de.pianomanu.asterania.lifecycle;

import com.badlogic.gdx.Gdx;
import de.pianomanu.asterania.config.KeyConfig;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.world.TileCoordinates;
import de.pianomanu.asterania.world.World;

public class PlayerUpdates extends GameLifeCycleUpdates {
    protected static void updatePlayer(World world, float delta) {
        updateMovement(world, delta);
    }

    private static void updateMovement(World world, float delta) {
        Player player = world.getPlayer();
        TileCoordinates playerTile = player.getFootPos().toTileCoordinates();
        TileCoordinates left = playerTile.copy().moveLeft();
        TileCoordinates right = playerTile.copy().moveRight();
        TileCoordinates up = playerTile.copy().moveUp();
        TileCoordinates down = playerTile.copy().moveDown();
        if (player.isMoving())
            player.updateHitbox();
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_RIGHT)) {
            player.setMoving();
            if (world.getTile(right).isAccessible())
                player.moveRight(delta);
            else {
                if (world.getTile(playerTile).isAccessible()) {
                    if (player.getPlayerHitbox().end.x < right.getX())
                        player.moveRight(delta);
                }
            }
        }
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_LEFT)) {
            player.setMoving();
            if (world.getTile(left).isAccessible())
                player.moveLeft(delta);
            else {
                if (world.getTile(playerTile).isAccessible()) {
                    if (player.getPlayerHitbox().start.x > left.getX() + 1)
                        player.moveLeft(delta);
                }
            }
        }
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_UP)) {
            player.setMoving();
            if (world.getTile(up).isAccessible() && world.getTile(playerTile).isAccessible())
                player.moveUp(delta);
            else {
                //oben tile nicht accessible, player tile accessible
                if (world.getTile(playerTile).isAccessible()) {
                    if (playerTile.getY() < up.getY())
                        player.moveUp(delta);
                }
            }
        }
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_DOWN)) {
            player.setMoving();
            if (world.getTile(down).isAccessible() && world.getTile(playerTile).isAccessible())
                player.moveDown(delta);
            else {
                //unten tile nicht accessible, player tile accessible
                if (world.getTile(playerTile).isAccessible()) {
                    if (playerTile.getY() > down.getY())
                        player.moveDown(delta);
                }
            }
        }
        System.out.println(world.getTile(playerTile).isAccessible());
        if (player.isMoving())
            if (!Gdx.input.isKeyPressed(KeyConfig.MOVE_RIGHT) && !Gdx.input.isKeyPressed(KeyConfig.MOVE_LEFT) && !Gdx.input.isKeyPressed(KeyConfig.MOVE_UP) && !Gdx.input.isKeyPressed(KeyConfig.MOVE_DOWN))
                player.setStanding();
    }
}
