package de.pianomanu.asterania;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.screens.LoadingScreen;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;

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


		world = new World(new TileCoordinates(5, 8));
		player = new Player();
		world.joinWorld(player, world.getEntryPoint());
	}
}
