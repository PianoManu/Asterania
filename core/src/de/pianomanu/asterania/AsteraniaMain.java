package de.pianomanu.asterania;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.pianomanu.asterania.render.RendererUtils;
import de.pianomanu.asterania.screens.LoadingScreen;
import de.pianomanu.asterania.utils.logging.LoggerUtils;
import de.pianomanu.asterania.utils.savegame.Savegame;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class AsteraniaMain extends Game {

	public static AsteraniaMain INSTANCE;

	private AssetManager assetManager;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private RendererUtils rendererUtils;

	public static final Level LOG_LEVEL = Level.FINE;
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static Savegame currentActiveSavegame;

	public AsteraniaMain() {
		INSTANCE = this;

		LOGGER.setLevel(LOG_LEVEL);


		LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME).setLevel(LOG_LEVEL);
	}

	@Override
	public void create() {
		LoggerUtils.initializeLogger();
		LOGGER.info("Initializing Asterania...");

		initializeInstanceVariables();

		setScreen(new LoadingScreen());
		LOGGER.info("Initialization completed!");
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	private void initializeInstanceVariables() {
		this.assetManager = new AssetManager();
		this.shapeRenderer = new ShapeRenderer();
		this.batch = new SpriteBatch();
		this.rendererUtils = new RendererUtils();
	}

	public AssetManager getAssetManager() {
		return this.assetManager;
	}

	public ShapeRenderer getShapeRenderer() {
		return this.shapeRenderer;
	}

	public SpriteBatch getBatch() {
		return this.batch;
	}

	public RendererUtils getRendererUtils() {
		return this.rendererUtils;
	}
}
