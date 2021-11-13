package de.pianomanu.asterania;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import de.pianomanu.asterania.config.GameConfig;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.screens.LoadingScreen;
import de.pianomanu.asterania.utils.logging.LoggerUtils;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;
import de.pianomanu.asterania.world.worldsections.WorldReader;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class AsteraniaMain extends Game {

	public static AsteraniaMain INSTANCE;
	public static AssetManager assetManager;

	public static final Level LOG_LEVEL = Level.FINEST;
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static World world;
	public static Player player;

	public AsteraniaMain() {
		INSTANCE = this;
		LOGGER.setLevel(LOG_LEVEL);
		LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME).setLevel(LOG_LEVEL);
	}

	@Override
	public void create() {
		LoggerUtils.initializeLogger();
		LOGGER.info("Initializing Asterania...");

		assetManager = new AssetManager();
		setScreen(new LoadingScreen());


		world = new World(new TileCoordinates(5, 8));
		player = new Player();
		world.joinWorld(player, world.getEntryPoint());

		WorldReader.loadWorld(new File(GameConfig.SAVE_NAME), world);
		LOGGER.info("Initialization completed!");
	}

	public static Logger getLogger() {
		return LOGGER;
	}
}
