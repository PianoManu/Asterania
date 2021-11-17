package de.pianomanu.asterania.config;

import com.badlogic.gdx.Gdx;

public class DisplayConfig {
    public static final int ZOOM = 2;
    public static final int DISPLAY_WIDTH = 800; //1920
    public static final int DISPLAY_HEIGHT = 600; //1080
    public static boolean isFullscreen = false;
    public static final int TILE_SIZE = 32 * ZOOM;
    public static boolean showDebugInfo = false;
    public static final boolean ENABLE_TRANSPARENT_INVENTORY = true;

    public static void setup() {
        if (isFullscreen)
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        else
            Gdx.graphics.setWindowedMode(DISPLAY_WIDTH, DISPLAY_HEIGHT);
    }
}
