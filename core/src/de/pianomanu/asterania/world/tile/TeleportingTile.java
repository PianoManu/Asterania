package de.pianomanu.asterania.world.tile;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;
import de.pianomanu.asterania.world.tile.teleporter.Teleporter;

public class TeleportingTile extends DecorationTile {
    private Teleporter teleporter;

    public TeleportingTile(String name, TileSettings settings) {
        super(name, settings);
        this.teleporter = new Teleporter();
    }

    @Override
    public boolean performAction(Player player, World world) {
        if (!AsteraniaMain.saveFile.getUniverse().getWorlds().contains(this.teleporter.getDestinyWorld()))
            AsteraniaMain.saveFile.getUniverse().getWorlds().add(this.teleporter.getDestinyWorld());
        player.changeCurrentWorld(this.teleporter.getDestinyWorld(), AsteraniaMain.player.getCharacterPos().toTileCoordinates());
        return true;
    }

    @Override
    public void runPlacementEvents(World world, Player player, TileCoordinates tileCoordinates) {
        //this.teleporter = new Teleporter(Worlds.MINE, tileCoordinates.toEntityCoordinates());
    }

    public Teleporter getTeleporter() {
        return this.teleporter;
    }
}
