package de.pianomanu.asterania;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.pianomanu.asterania.config.LanguageConfig;
import de.pianomanu.asterania.render.ShapeRendererUtils;
import de.pianomanu.asterania.render.SpriteBatchUtils;
import de.pianomanu.asterania.render.text.TextInputBoxRenderer;
import de.pianomanu.asterania.render.text.TextRenderer;
import de.pianomanu.asterania.savegame.Savegame;
import de.pianomanu.asterania.screens.LoadingScreen;
import de.pianomanu.asterania.utils.WindowUtils;
import de.pianomanu.asterania.utils.logging.LoggerUtils;
import de.pianomanu.asterania.utils.text.language.LanguageFileUtils;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class AsteraniaMain extends Game {

	public static AsteraniaMain INSTANCE;

	private AssetManager assetManager;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private BitmapFont font;
	private GlyphLayout glyphLayout;
	private ShapeRendererUtils shapeRendererUtils;
	private SpriteBatchUtils spriteBatchUtils;
	private TextRenderer textRenderer;
	private TextInputBoxRenderer textInputBoxRenderer;

	private Savegame currentActiveSavegame;

	public static final Level LOG_LEVEL = Level.FINE;
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private LanguageFileUtils languageFileUtils;

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

		WindowUtils.changeScreen(null, new LoadingScreen());
		LOGGER.info("Initialization completed!");
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	private void initializeInstanceVariables() {
		this.assetManager = new AssetManager();
		this.languageFileUtils = new LanguageFileUtils(LanguageConfig.LANGUAGE_ABBREVIATION); //TODO change language ingame
		loadRenderers();
	}

	private void loadRenderers() {
		this.shapeRenderer = new ShapeRenderer();
		this.batch = new SpriteBatch();
		this.font = new BitmapFont(Gdx.files.internal("font/asteraniafont.fnt"));
		this.glyphLayout = new GlyphLayout();
		this.shapeRendererUtils = new ShapeRendererUtils();
		this.spriteBatchUtils = new SpriteBatchUtils();
		this.textRenderer = new TextRenderer();
		this.textInputBoxRenderer = new TextInputBoxRenderer();
	}

	private void disposeRenderers() {
		this.shapeRenderer.dispose();
		this.batch.dispose();
		this.font.dispose();
		this.glyphLayout.reset();
	}

	public void reloadRenderers() {
		disposeRenderers();
		loadRenderers();
	}

	public Savegame getCurrentActiveSavegame() {
		return this.currentActiveSavegame;
	}

	public void setCurrentActiveSavegame(Savegame currentActiveSavegame) {
		this.currentActiveSavegame = currentActiveSavegame;
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

	public BitmapFont getFont() {
		return this.font;
	}

	public GlyphLayout getGlyphLayout() {
		return this.glyphLayout;
	}

	public ShapeRendererUtils getShapeRendererUtils() {
		return this.shapeRendererUtils;
	}

	public SpriteBatchUtils getSpriteBatchUtils() {
		return this.spriteBatchUtils;
	}

	public TextRenderer getTextRenderer() {
		return this.textRenderer;
	}

	public TextInputBoxRenderer getTextInputBoxRenderer() {
		return this.textInputBoxRenderer;
	}

	public LanguageFileUtils getLanguageFileUtils() {
		return this.languageFileUtils;
	}
}
