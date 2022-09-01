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

import java.util.Objects;
import java.util.logging.Logger;

public class DecorationLayerInteraction {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    private static TileCoordinates previousTileCoordinates = new TileCoordinates(0, 0);

    public static void changeDecorationTile(World world, Player player, EntityCoordinates mouse) {
        if (haveMouseTileCoordinatesChanged(mouse.toTileCoordinates())) {
            resetBreakingProcessBecauseCoordinatesChanged(world, player, mouse);
        } else {
            Tile oldDecorationTile = world.findSection(mouse).getDecorationLayerTileAbsoluteCoordinates(mouse);
            if (player.isInReach(mouse.toTileCoordinates())) {
                if (oldDecorationTile != null) {

                    //check how much player is already carrying
                    if (playerCanCarryAdditionalTile(player, oldDecorationTile)) {
                        TileBreakingUI.renderNoBreakingPossible = false;
                        if (Gdx.input.isButtonJustPressed(KeyConfig.REMOVE_TILE))
                            player.setBreakingTile(true);
                        changeBreakingProgress(player, oldDecorationTile, oldDecorationTile.getBreakingLevel() + Gdx.graphics.getDeltaTime(), true);
                        LOGGER.finest("Breaking level " + oldDecorationTile.getBreakingLevel() + ", BreakTime" + oldDecorationTile.getSettings().get(TileProperties.BREAK_TIME));
                    } else {
                        //too much weight in inventory
                        TileBreakingUI.renderNoBreakingPossible = true;
                        TileBreakingUI.renderNoBreakingPossibleMessage = "Inventory full";
                    }
                }
            }
        }
    }

    private static void replaceDecorationTile(Player player, Tile newTile) {
        if (player.getPlayerInventory().calcCurrentWeight() >= player.getMaxWeight()) {
            TileBreakingUI.renderNoBreakingPossible = false;
        } else {
            removeDecorationTile(player);
            if (player.getPlayerInventory().getCurrentIOStack().getStackCount() >= 1 && !player.getPlayerInventory().getCurrentIOStack().equals(ItemStack.EMPTY)) {
                setDecorationTile(player, newTile);
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
        Tile newTile = world.findSection(mouse).getDecorationLayerTileAbsoluteCoordinates(mouse);
        if (prevTile != null)
            resetBreakingProgress(player, prevTile);
        if (newTile != null)
            resetBreakingProgress(player, newTile);
        previousTileCoordinates = mouse.toTileCoordinates();
    }

    private static boolean playerCanCarryAdditionalTile(Player player, Tile tile) {
        return player.getPlayerInventory().calcCurrentWeight() + Objects.requireNonNull(GameRegistry.getItem(tile)).getWeight() <= player.getMaxWeight();
    }

    private static void removeDecorationTile(Player player) {
        World world = player.getCurrentWorld();
        EntityCoordinates mouse = CoordinatesUtils.pixelToEntityCoordinates(Gdx.input.getX(), Gdx.input.getY(), player.getPos());
        Tile old = world.findSection(mouse).getDecorationLayerTileAbsoluteCoordinates(mouse);
        resetBreakingProgress(player, old);
        player.getPlayerInventory().addStack(new ItemStack(GameRegistry.getItem(old)));
        world.findSection(mouse).setDecorationLayerTileAbsoluteCoordinates(mouse, null);
    }


    private static void setDecorationTile(Player player, Tile newTile) {
        World world = player.getCurrentWorld();
        EntityCoordinates mouse = CoordinatesUtils.pixelToEntityCoordinates(Gdx.input.getX(), Gdx.input.getY(), player.getPos());

        world.findSection(mouse).setDecorationLayerTileAbsoluteCoordinates(mouse, newTile);
        newTile.runPlacementEvents(world, player, mouse.toTileCoordinates());
    }

    private static void changeBreakingProgress(Player player, Tile tile, float newBreakingLevel, boolean setBreakingTile) {
        float breakingTime = tile.getSettings().get(TileProperties.BREAK_TIME);

        tile.setBreakingLevel(newBreakingLevel);
        player.setCurrentBreakingPercentage(newBreakingLevel / breakingTime);
        player.setBreakingTile(setBreakingTile);

        if (tile.getBreakingLevel() >= breakingTime) {
            //remove decoration, give player item
            DecorationLayerInteraction.removeDecorationTile(player);
        }
    }

    public static void resetBreakingProgress(Player player, Tile tile) {
        changeBreakingProgress(player, tile, 0, false);
    }
}
