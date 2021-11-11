package de.pianomanu.asterania.lifecycle;

import com.badlogic.gdx.Gdx;
import de.pianomanu.asterania.config.KeyConfig;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.utils.CoordinatesUtils;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.coordinates.EntityCoordinates;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;
import de.pianomanu.asterania.world.tile.Tiles;

public class PlayerUpdates extends GameLifeCycleUpdates {
    protected static void updatePlayer(World world, float delta) {
        updateMovement(world, delta);
        changeEnvironment(world);
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
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_UP)) {
            player.setMoving();
            if (world.findSection(playerTile).getTile(playerTile).isAccessible()) {
                if (world.findSection(up) == null) {
                    world.preGenerateSurroundingWorldSections();
                }
                if (world.findSection(up).getTile(up).isAccessible() || playerFootPos.y + player.getStepSize() * delta < up.getY()) {
                    player.moveUp(delta);
                }
                if (!world.findSection(up).getTile(up).isAccessible() && playerFootPos.y + player.getStepSize() * delta > up.getY()) {
                    player.setFootPos(player.getCharacterPos().x, up.getY() - distanceFromInacessibleBlocks);
                }
            }
        }
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_DOWN)) {
            player.setMoving();
            if (world.findSection(playerTile).getTile(playerTile).isAccessible()) {
                if (world.findSection(down) == null) {
                    world.preGenerateSurroundingWorldSections();
                }
                if (world.findSection(down).getTile(down).isAccessible() || playerFootPos.y - player.getStepSize() * delta > up.getY() - 1) {
                    player.moveDown(delta);
                }
                if (!world.findSection(down).getTile(down).isAccessible() && playerFootPos.y - player.getStepSize() * delta < up.getY() - 1) {
                    player.setFootPos(player.getCharacterPos().x, up.getY() - 1);
                }
            }
        }
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_RIGHT)) {
            player.setMoving();
            if (world.findSection(playerTile).getTile(playerTile).isAccessible()) {
                if (world.findSection(right) == null) {
                    world.preGenerateSurroundingWorldSections();
                }
                if (world.findSection(right).getTile(right).isAccessible() || player.getPlayerHitbox().end.x < right.getX()) {
                    player.moveRight(delta);
                }
                if (!world.findSection(right).getTile(right).isAccessible() && player.getPlayerHitbox().end.x + player.getStepSize() * delta > right.getX()) {
                    float xHitboxWidth = player.getCharacterSize().x;
                    player.setFootPos(right.getX() - xHitboxWidth / 2, playerFootPos.y);
                }
            }
        }
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_LEFT)) {
            player.setMoving();
            if (world.findSection(playerTile).getTile(playerTile).isAccessible()) {
                if (world.findSection(left) == null) {
                    world.preGenerateSurroundingWorldSections();
                }
                if (world.findSection(left).getTile(left).isAccessible() || player.getPlayerHitbox().start.x > left.getX() + 1) {
                    player.moveLeft(delta);
                }
                if (!world.findSection(left).getTile(left).isAccessible() && player.getPlayerHitbox().start.x - player.getStepSize() * delta < left.getX() + 1) {
                    float xHitboxWidth = player.getCharacterSize().x;
                    player.setFootPos(left.getX() + 1 + xHitboxWidth / 2, playerFootPos.y);
                }
            }
        }
        if (player.isMoving())
            player.checkForAnimationUpdate(delta);
            if (!Gdx.input.isKeyPressed(KeyConfig.MOVE_RIGHT) && !Gdx.input.isKeyPressed(KeyConfig.MOVE_LEFT) && !Gdx.input.isKeyPressed(KeyConfig.MOVE_UP) && !Gdx.input.isKeyPressed(KeyConfig.MOVE_DOWN))
                player.setStanding();
    }

    public static void changeEnvironment(World world) {
        Player player = world.getPlayer();
        if (Gdx.input.isButtonJustPressed(KeyConfig.SET_TILE)) {
            EntityCoordinates mouse = CoordinatesUtils.pixelToEntityCoordinates(Gdx.input.getX(), Gdx.input.getY(), player.getCharacterPos());
            world.findSection(mouse).setTile(mouse, Tiles.ROCK);
        }

        if (Gdx.input.isButtonJustPressed(KeyConfig.REMOVE_TILE)) {
            EntityCoordinates mouse = CoordinatesUtils.pixelToEntityCoordinates(Gdx.input.getX(), Gdx.input.getY(), player.getCharacterPos());
            world.findSection(mouse).setTile(mouse, Tiles.GRASS);
        }
    }
}
