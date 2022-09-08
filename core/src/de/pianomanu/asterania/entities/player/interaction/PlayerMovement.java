package de.pianomanu.asterania.entities.player.interaction;

import com.badlogic.gdx.Gdx;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.coordinates.EntityCoordinates;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;
import de.pianomanu.asterania.world.direction.Direction;
import de.pianomanu.asterania.world.tile.tileutils.TileProperties;

public class PlayerMovement {
    public static boolean move(World world, Player player, Direction direction) {
        player.setMoving();
        if (isTileAccessible(world, player.getPos())) {
            return playerMoves(world, player, direction);
        } else {
            //TODO indicate that no movement is possible
            player.setPlayerFacing(direction);
        }
        return false;
    }

    private static boolean playerMoves(World world, Player player, Direction direction) {
        TileCoordinates adjacentTileCoordinates = switch (direction) {
            case RIGHT -> player.getPlayerHitbox().getBottomRight().copy().moveToRightBorder().toTileCoordinates();
            case LEFT -> player.getPlayerHitbox().getBottomLeft().copy().moveToLeftBorder().toTileCoordinates();
            case UP, DOWN -> player.getPos().toTileCoordinates().copy().move(direction);
        };
        generateSurroundingSections(world, adjacentTileCoordinates);
        if (isAdjacentTileAccessible(world, player, direction) || playerStaysOnTile(player, direction, adjacentTileCoordinates)) {
            player.move(direction, Gdx.graphics.getDeltaTime());
            return true;
        }
        if (!playerStaysOnTile(player, direction, adjacentTileCoordinates)) {
            teleportPlayerToTileBorder(player, direction, adjacentTileCoordinates);
            return true;
        }
        return false;
    }

    private static boolean isAdjacentTileAccessible(World world, Player player, Direction direction) {
        EntityCoordinates moved1 = switch (direction) {
            case RIGHT -> new EntityCoordinates(player.getPlayerHitbox().getBottomRight().copy().moveToRightBorder().x, player.getPlayerHitbox().start.y);
            case LEFT -> new EntityCoordinates(player.getPlayerHitbox().getBottomLeft().copy().moveToLeftBorder().x - 1, player.getPlayerHitbox().start.y);
            case UP -> new EntityCoordinates(player.getPlayerHitbox().start.x, player.getPlayerHitbox().start.y + 1);
            case DOWN -> new EntityCoordinates(player.getPlayerHitbox().start.x, player.getPlayerHitbox().start.y - 1);
        };
        EntityCoordinates moved2 = switch (direction) {
            case RIGHT -> new EntityCoordinates(player.getPlayerHitbox().getBottomRight().copy().moveToRightBorder().x, player.getPlayerHitbox().start.y + player.getPlayerZDepth());
            case LEFT -> new EntityCoordinates(player.getPlayerHitbox().getBottomLeft().copy().moveToLeftBorder().x - 1, player.getPlayerHitbox().start.y + player.getPlayerZDepth());
            case UP -> new EntityCoordinates(player.getPlayerHitbox().end.x, player.getPlayerHitbox().start.y + 1);
            case DOWN -> new EntityCoordinates(player.getPlayerHitbox().end.x, player.getPlayerHitbox().start.y - 1);
        };
        boolean moved1And2areSameTile = switch (direction) {
            case RIGHT, LEFT -> Math.ceil(player.getPlayerHitbox().start.y) == Math.ceil(player.getPlayerHitbox().start.y + player.getPlayerZDepth());
            case UP, DOWN -> Math.ceil(player.getPlayerHitbox().start.x) == Math.ceil(player.getPlayerHitbox().end.x);
        };
        if (moved1And2areSameTile)
            return isTileAccessible(world, moved1);
        return isTileAccessible(world, moved1) && isTileAccessible(world, moved2);
    }

    private static boolean isTileAccessible(World world, EntityCoordinates entityCoordinates) {
        boolean decorationAccessible = isDecorationAccessible(world, entityCoordinates);
        boolean backgroundAccessible = world.findSection(entityCoordinates).getTileAbsoluteCoordinates(entityCoordinates).getSettings().get(TileProperties.IS_ACCESSIBLE);
        return decorationAccessible && backgroundAccessible;
    }

    private static boolean playerStaysOnTile(Player player, Direction direction, TileCoordinates adjacentTileCoordinates) {
        float bottomRightX = player.getPlayerHitbox().getBottomRight().x;
        float bottomLeftX = player.getPlayerHitbox().getBottomLeft().x;
        float playerPosY = player.getPos().y;
        float steps = player.getStepSize() * Gdx.graphics.getDeltaTime();
        float bottomRightBorder = player.getPlayerHitbox().getBottomRight().copy().moveToRightBorder().x;
        float bottomLeftBorder = player.getPlayerHitbox().getBottomLeft().copy().moveToLeftBorder().x;
        return switch (direction) {
            case RIGHT -> bottomRightX + steps < bottomRightBorder;
            case LEFT -> bottomLeftX - steps > bottomLeftBorder;
            case UP -> playerPosY + steps < adjacentTileCoordinates.getY() - player.getPlayerZDepth();
            case DOWN -> playerPosY - steps > adjacentTileCoordinates.getY() + 1;
        };
    }

    private static void teleportPlayerToTileBorder(Player player, Direction direction, TileCoordinates adjacentTileCoordinates) {
        float xHitboxWidth = player.getCharacterSize().x;
        EntityCoordinates pos = player.getPos();
        switch (direction) {
            case RIGHT -> player.setPos(adjacentTileCoordinates.getX() - xHitboxWidth / 2, pos.y);
            case LEFT -> player.setPos(adjacentTileCoordinates.getX() + xHitboxWidth / 2, pos.y);
            case UP -> player.setPos(pos.x, adjacentTileCoordinates.getY() - player.getPlayerZDepth());
            case DOWN -> player.setPos(pos.x, adjacentTileCoordinates.getY() + 1);
        }
    }

    private static void generateSurroundingSections(World world, TileCoordinates coordinatesOfNewSection) {
        if (world.findSection(coordinatesOfNewSection) == null) {
            world.preGenerateSurroundingWorldSections();
        }
    }

    private static boolean isDecorationAccessible(World world, EntityCoordinates entityCoordinates) {
        if (world.findSection(entityCoordinates).getDecorationLayerTileAbsoluteCoordinates(entityCoordinates) != null) {
            return world.findSection(entityCoordinates).getDecorationLayerTileAbsoluteCoordinates(entityCoordinates).getSettings().get(TileProperties.IS_ACCESSIBLE);
        }
        return true;
    }
}
