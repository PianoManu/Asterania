package de.pianomanu.asterania.entities.player.interaction.environment;

import com.badlogic.gdx.Gdx;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.KeyConfig;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.inventory.item.ItemStack;
import de.pianomanu.asterania.inventory.tileproperties.TileProperties;
import de.pianomanu.asterania.registry.GameRegistry;
import de.pianomanu.asterania.render.ui.TileBreakingUI;
import de.pianomanu.asterania.utils.CoordinatesUtils;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.coordinates.EntityCoordinates;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;
import de.pianomanu.asterania.world.tile.Tile;
import de.pianomanu.asterania.world.tile.Tiles;

import java.util.Objects;
import java.util.logging.Logger;

public class DecorationLayerInteraction {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    private static TileCoordinates previousTileCoordinates = new TileCoordinates(0, 0);

    public static void checkChangeDecorationLayer(World world, Player player, EntityCoordinates mouse) {
        if (Gdx.input.isButtonPressed(KeyConfig.REMOVE_TILE)) {
            checkRemoveDecorationTile(world, player, mouse);
        } else {
            //breaking button released
            resetBreakingProgress(player, world.getDecorationLayerTile(mouse));
        }

        if (Gdx.input.isButtonJustPressed(KeyConfig.PLACE_OR_INTERACT_WITH_TILE)) {
            placeOrInteractWithTile(world, player, mouse);
        }
    }

    private static void checkRemoveDecorationTile(World world, Player player, EntityCoordinates mouse) {
        if (haveMouseTileCoordinatesChanged(mouse.toTileCoordinates())) {
            resetBreakingProcessBecauseCoordinatesChanged(world, player, mouse);
        } else {
            Tile oldDecorationTile = world.getDecorationLayerTile(mouse);
            if (player.isInReach(mouse.toTileCoordinates()) && oldDecorationTile != null) {
                //check how much player is already carrying
                if (playerCanCarryAdditionalTile(player, oldDecorationTile)) {
                    TileBreakingUI.disableNoBreakingPossibleMessage();
                    changeBreakingProgress(player, oldDecorationTile, oldDecorationTile.getBreakingLevel() + Gdx.graphics.getDeltaTime(), true);
                    LOGGER.finest("Breaking level " + oldDecorationTile.getBreakingLevel() + ", BreakTime" + oldDecorationTile.getSettings().get(TileProperties.BREAK_TIME));
                } else {
                    //too much weight in inventory
                    TileBreakingUI.enableNoBreakingPossibleMessage("Inventory full");
                }
            }
        }
    }

    private static boolean haveMouseTileCoordinatesChanged(TileCoordinates mouseTile) {
        boolean sameX = mouseTile.x == previousTileCoordinates.x;
        boolean sameY = mouseTile.y == previousTileCoordinates.y;
        return !(sameX && sameY);
    }

    private static void resetBreakingProcessBecauseCoordinatesChanged(World world, Player player, EntityCoordinates mouse) {
        Tile prevTile = world.findSection(previousTileCoordinates).getDecorationLayerTileAbsoluteCoordinates(previousTileCoordinates);
        Tile newTile = world.getDecorationLayerTile(mouse);
        if (prevTile != null)
            resetBreakingProgress(player, prevTile);
        if (newTile != null)
            resetBreakingProgress(player, newTile);

        previousTileCoordinates = mouse.toTileCoordinates();
        TileBreakingUI.disableNoBreakingPossibleMessage();
    }

    private static boolean playerCanCarryAdditionalTile(Player player, Tile tile) {
        return player.getPlayerInventory().calcCurrentWeight() + Objects.requireNonNull(GameRegistry.getItem(tile)).getWeight() <= player.getMaxWeight();
    }

    private static void removeDecorationTile(Player player) {
        World world = player.getCurrentWorld();
        EntityCoordinates mouse = CoordinatesUtils.pixelToEntityCoordinates(Gdx.input.getX(), Gdx.input.getY(), player.getPos());
        Tile old = world.getDecorationLayerTile(mouse);
        resetBreakingProgress(player, old);
        player.getPlayerInventory().addStack(new ItemStack(GameRegistry.getItem(old)));
        world.setDecorationLayerTile(mouse, null);
    }

    private static void changeBreakingProgress(Player player, Tile tile, float newBreakingLevel, boolean setBreakingTile) {
        if (tile != null) {
            float breakingTime = tile.getSettings().get(TileProperties.BREAK_TIME);

            tile.setBreakingLevel(newBreakingLevel);
            player.setCurrentBreakingPercentage(newBreakingLevel / breakingTime);
            player.setBreakingTile(setBreakingTile);

            if (tile.getBreakingLevel() >= breakingTime) {
                //remove decoration, give player item
                DecorationLayerInteraction.removeDecorationTile(player);
            }
        }
    }

    private static void resetBreakingProgress(Player player, Tile tile) {
        changeBreakingProgress(player, tile, 0, false);
    }

    private static void placeOrInteractWithTile(World world, Player player, EntityCoordinates mouse) {
        if (player.isInReach(mouse.toTileCoordinates())) {
            Tile decorationLayerTile = world.getDecorationLayerTile(mouse);
            if (decorationLayerTile == null) {
                tryPlaceDecorationLayerTile(world, player, mouse);
            } else {
                Tile tile = world.getDecorationLayerTile(mouse);
                tile.performAction(player, world);
            }
        }
    }

    private static void tryPlaceDecorationLayerTile(World world, Player player, EntityCoordinates mouse) {
        if (!player.getPlayerInventory().getCurrentIOStack().equals(ItemStack.EMPTY)) {
            Tile heldTile = GameRegistry.getTile(player.getPlayerInventory().getCurrentIOStack().getItem());
            if (heldTile != Tiles.WHITE && player.getPlayerInventory().getCurrentIOStack().getStackCount() >= 1) {
                world.setDecorationLayerTile(mouse, heldTile);
                player.getPlayerInventory().getCurrentIOStack().decrement();
            }
        }
    }
}
