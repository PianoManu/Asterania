package de.pianomanu.asterania;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.screens.GameScreen;
import de.pianomanu.asterania.screens.LoadingScreen;
import de.pianomanu.asterania.screens.MainMenuScreen;
import de.pianomanu.asterania.world.EntityCoordinates;
import de.pianomanu.asterania.world.World;

public class AsteraniaMain extends Game {

	public static AsteraniaMain INSTANCE;
	public static AssetManager assetManager;

	public static LoadingScreen loadingScreen;
	public static MainMenuScreen mainMenuScreen;
	public static GameScreen gameScreen;

	public static World world;
	public static Player player;

	public AsteraniaMain() {
		INSTANCE = this;
	}

	@Override
	public void create() {
		assetManager = new AssetManager();
		setScreen(new LoadingScreen());

		//this.resize(DisplayConfig.DISPLAY_WIDTH, DisplayConfig.DISPLAY_HEIGHT);
		DisplayConfig.setup();
		world = new World();
		player = new Player();
		player.setCharacterPos(new EntityCoordinates(0, 0));
		world.joinWorld(player);


		//setScreen(new MainMenuScreen());
	}
}
