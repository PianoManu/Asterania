package de.pianomanu.asterania;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.screens.LoadingScreen;
import de.pianomanu.asterania.utils.CoordinatesUtils;
import de.pianomanu.asterania.world.EntityCoordinates;
import de.pianomanu.asterania.world.TileCoordinates;
import de.pianomanu.asterania.world.World;

public class AsteraniaMain extends Game {

	public static AsteraniaMain INSTANCE;
	public static AssetManager assetManager;

	public static World world;
	public static Player player;

	public AsteraniaMain() {
		INSTANCE = this;
	}

	@Override
	public void create() {
		assetManager = new AssetManager();
		setScreen(new LoadingScreen());


		world = new World(new TileCoordinates(5, 5));
		player = new Player();
		player.setCharacterPos(new EntityCoordinates(0, 0));
		world.joinWorld(player);

		CoordinatesUtils.transformEntityCoordinatesToPixels(new EntityCoordinates(3, 3), new EntityCoordinates(4, 4));
		System.out.println();
	}
}
