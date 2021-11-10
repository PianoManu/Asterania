package de.pianomanu.asterania.lifecycle;

import com.badlogic.gdx.Gdx;
import de.pianomanu.asterania.config.KeyConfig;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.world.EntityCoordinates;
import de.pianomanu.asterania.world.TileCoordinates;
import de.pianomanu.asterania.world.World;

public class PlayerUpdates extends GameLifeCycleUpdates {
    protected static void updatePlayer(World world, float delta) {
        updateMovement(world, delta);
    }

    private static void updateMovement(World world, float delta) {
        Player player = world.getPlayer();
        EntityCoordinates playerFootPos = player.getFootPos();
        TileCoordinates playerTile = playerFootPos.toTileCoordinates();
        TileCoordinates left = playerTile.copy().moveLeft();
        TileCoordinates right = playerTile.copy().moveRight();
        TileCoordinates up = playerTile.copy().moveUp();
        TileCoordinates down = playerTile.copy().moveDown();
        float distanceFromInacessibleBlocks = 0.000001f;
        if (player.isMoving())
            player.updateHitbox();
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_RIGHT)) {
            player.setMoving();
            if (world.getTile(playerTile).isAccessible()) {
                if (world.getTile(right).isAccessible() || player.getPlayerHitbox().end.x < right.getX()) {
                    player.moveRight(delta);
                }
                if (!world.getTile(right).isAccessible() && player.getPlayerHitbox().end.x + player.getStepSize() * delta > right.getX()) {
                    float xHitboxWidth = player.getCharacterSize().x;
                    player.setFootPos(right.getX() - xHitboxWidth / 2, playerFootPos.y);
                }
            }
        }
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_LEFT)) {
            player.setMoving();
            if (world.getTile(playerTile).isAccessible()) {
                if (world.getTile(left).isAccessible() || player.getPlayerHitbox().start.x > left.getX() + 1) {
                    player.moveLeft(delta);
                }
                if (!world.getTile(left).isAccessible() && player.getPlayerHitbox().start.x - player.getStepSize() * delta < left.getX() + 1) {
                    float xHitboxWidth = player.getCharacterSize().x;
                    player.setFootPos(left.getX() + 1 + xHitboxWidth / 2, playerFootPos.y);
                }
            }
        }
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_UP)) {
            player.setMoving();
            if (world.getTile(playerTile).isAccessible()) {
                if (world.getTile(up).isAccessible() || playerFootPos.y + player.getStepSize() * delta < up.getY()) {
                    player.moveUp(delta);
                }
                if (!world.getTile(up).isAccessible() && playerFootPos.y + player.getStepSize() * delta > up.getY()) {
                    player.setFootPos(player.getCharacterPos().x, up.getY() - distanceFromInacessibleBlocks);
                }
            }
        }
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_DOWN)) {
            player.setMoving();
            if (world.getTile(playerTile).isAccessible()) {
                if (world.getTile(down).isAccessible() || playerFootPos.y - player.getStepSize() * delta > up.getY() - 1) {
                    player.moveDown(delta);
                }
                if (!world.getTile(down).isAccessible() && playerFootPos.y - player.getStepSize() * delta < up.getY() - 1) {
                    player.setFootPos(player.getCharacterPos().x, up.getY() - 1);
                }
            }
        }
        System.out.println(world.getTile(playerTile).isAccessible());
        if (player.isMoving())
            if (!Gdx.input.isKeyPressed(KeyConfig.MOVE_RIGHT) && !Gdx.input.isKeyPressed(KeyConfig.MOVE_LEFT) && !Gdx.input.isKeyPressed(KeyConfig.MOVE_UP) && !Gdx.input.isKeyPressed(KeyConfig.MOVE_DOWN))
                player.setStanding();
    }
}
