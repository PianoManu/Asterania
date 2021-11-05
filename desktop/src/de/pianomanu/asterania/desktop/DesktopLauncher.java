package de.pianomanu.asterania.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import de.pianomanu.asterania.AsteraniaMain;

public class DesktopLauncher {
	public static void main(String[] arg) {
		TexturePacker.Settings settings = new TexturePacker.Settings();
		settings.maxHeight = 4096;
		settings.maxWidth = 4096;
		settings.edgePadding = false;
		settings.duplicatePadding = false;
		settings.filterMin = Texture.TextureFilter.Linear;
		settings.filterMag = Texture.TextureFilter.Linear;
		TexturePacker.process(settings, "textures\\tiles", "textures\\tiles\\atlas", "tile_atlas");
		TexturePacker.process(settings, "textures\\entities\\player", "textures\\entities\\player\\atlas", "player_atlas");

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Asterania";
		config.vSyncEnabled = true;
		//config.addIcon();
		new LwjglApplication(new AsteraniaMain(), config);
	}
}
