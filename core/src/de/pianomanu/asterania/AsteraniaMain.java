package de.pianomanu.asterania;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import de.pianomanu.asterania.config.GameConfig;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.savegame.SaveFile;
import de.pianomanu.asterania.screens.LoadingScreen;
import de.pianomanu.asterania.utils.file_utils.SaveGameUtils;
import de.pianomanu.asterania.utils.logging.LoggerUtils;
import de.pianomanu.asterania.world.World;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class AsteraniaMain extends Game {

	public static AsteraniaMain INSTANCE;
	public static AssetManager assetManager;

	public static final Level LOG_LEVEL = Level.FINE;
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	//public static World world;
	//TODO find better way to save player information
	public static Player player;
	public static SaveFile saveFile;

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


		//TODO
		saveFile = new SaveFile(GameConfig.SAVE_NAME);
		saveFile.loadUniverse();
		World home = null;
		/*for (World w :
				saveFile.getUniverse().getWorlds()) {
			if (w.getWorldName().equals("home"))
				home = w;
		}*/
		if (home == null) {
			LOGGER.warning("No home world found. Creating a new home world...");
			//home = Worlds.HOME;
		}
		player = new Player();
		//home.joinWorld(player, home.getEntryPoint());

		if (new File(GameConfig.SAVE_PATH_HOME + "." + GameConfig.WORLD_FILE_FORMAT).exists()) {
			SaveGameUtils.loadWorldsFromDirectory(saveFile.getName());
		} else {
			//home.preGenerateSurroundingWorldSections();
		}
		LOGGER.info("Initialization completed!");
	}

	public static Logger getLogger() {
		return LOGGER;
	}
}
