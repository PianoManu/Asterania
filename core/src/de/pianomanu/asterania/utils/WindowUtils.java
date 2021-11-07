package de.pianomanu.asterania.utils;

import com.badlogic.gdx.Gdx;
import de.pianomanu.asterania.config.DisplayConfig;

public class WindowUtils {
    private static int previousWidth = DisplayConfig.DISPLAY_WIDTH;
    private static int previousHeight = DisplayConfig.DISPLAY_HEIGHT;

    public static boolean windowSizeHasChanged() {
        if (windowSizeHasChanged(previousWidth, previousHeight, Gdx.graphics.getWidth(), Gdx.graphics.getHeight())) {
            System.out.println("Changed!");
            previousWidth = Gdx.graphics.getWidth();
            previousHeight = Gdx.graphics.getHeight();
            return true;
        }
        previousWidth = Gdx.graphics.getWidth();
        previousHeight = Gdx.graphics.getHeight();
        return false;
    }

    private static boolean windowSizeHasChanged(int previousWidth, int previousHeight, int newWidth, int newHeight) {
        return !(previousWidth == newWidth && previousHeight == newHeight);
    }
}
