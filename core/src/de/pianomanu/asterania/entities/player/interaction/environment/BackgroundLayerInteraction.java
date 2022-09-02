package de.pianomanu.asterania.entities.player.interaction.environment;

import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.inventory.item.ItemStack;
import de.pianomanu.asterania.registry.GameRegistry;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.coordinates.EntityCoordinates;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;
import de.pianomanu.asterania.world.tile.Tile;
import de.pianomanu.asterania.world.tile.Tiles;

public class BackgroundLayerInteraction {

    public static void replaceTile(World world, Player player, EntityCoordinates mouse) {
        Tile heldTile = GameRegistry.getTile(player.getPlayerInventory().getCurrentIOStack().getItem());
        if (heldTile != Tiles.WHITE)
            replaceTile(world, player, mouse, heldTile);
        else
            //TODO
            System.out.println("Held tile item has no corresponding background tile");
    }

    public static void replaceTile(World world, Player player, EntityCoordinates mouse, Tile newTile) {
        removeTile(world, player, mouse.toTileCoordinates());
        setTile(world, player, mouse.toTileCoordinates(), newTile);
    }

    private static void removeTile(World world, Player player, TileCoordinates tileCoordinates) {
        Tile old = world.getTile(tileCoordinates);
        player.getPlayerInventory().addStack(new ItemStack(GameRegistry.getItem(old)));
        world.setTile(tileCoordinates, null);
    }

    private static void setTile(World world, Player player, TileCoordinates tileCoordinates, Tile newTile) {
        world.setTile(tileCoordinates, newTile);
        world.getTile(tileCoordinates).runPlacementEvents(world, player, tileCoordinates);
    }
}
