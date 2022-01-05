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
        settings.filterMin = Texture.TextureFilter.Nearest;
        settings.filterMag = Texture.TextureFilter.Nearest;
        TexturePacker.process(settings, "textures\\tiles", "textures\\tiles\\atlas", "tile_atlas");
        TexturePacker.process(settings, "textures\\decoration", "textures\\decoration\\atlas", "decoration_atlas");
        TexturePacker.process(settings, "textures\\overlay", "textures\\overlay\\atlas", "overlay_atlas");
            TexturePacker.process(settings, "textures\\entities\\player", "textures\\entities\\player\\atlas", "player_atlas");
            TexturePacker.process(settings, "textures\\gui\\buttons", "textures\\gui\\buttons\\atlas", "button_atlas");

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Asterania";
        config.vSyncEnabled = true;
            config.resizable = true;
        //config.addIcon();
        new LwjglApplication(new AsteraniaMain(), config);
    }
}
