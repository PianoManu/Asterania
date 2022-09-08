package de.pianomanu.asterania.world.tile;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.entities.Player;
import de.pianomanu.asterania.world.tile.teleporter.Teleporter;
import de.pianomanu.asterania.world.tile.tileutils.LayerType;
import de.pianomanu.asterania.world.tile.tileutils.TileMaterial;
import de.pianomanu.asterania.world.tile.tileutils.TileSettings;

public class TeleportingTile extends Tile {
    private Teleporter teleporter;

    public TeleportingTile(String name, TileMaterial tileMaterial, TileSettings settings) {
        super(name, tileMaterial, settings, LayerType.DECORATION);
        this.teleporter = new Teleporter();
    }

    @Override
    public boolean performAction(Player player, World world) {
        if (!AsteraniaMain.currentActiveSavegame.getUniverse().getWorlds().contains(this.teleporter.getDestinyWorld()))
            AsteraniaMain.currentActiveSavegame.getUniverse().getWorlds().add(this.teleporter.getDestinyWorld());
        player.changeCurrentWorld(this.teleporter.getDestinyWorld(), player.getPos().toTileCoordinates());
        return true;
    }

    /*@Override
    public boolean runPostPlacementEvents(World world, Player player, TileCoordinates tileCoordinates) {
        //this.teleporter = new Teleporter(Worlds.MINE, tileCoordinates.toEntityCoordinates());
    }*/

    public Teleporter getTeleporter() {
        return this.teleporter;
    }
}
