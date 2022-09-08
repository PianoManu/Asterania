package de.pianomanu.asterania.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Null;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.render.button.Buttons;

public class WindowUtils {
    private static int previousWidth = DisplayConfig.DISPLAY_WIDTH;
    private static int previousHeight = DisplayConfig.DISPLAY_HEIGHT;

    public static boolean windowSizeHasChanged() {
        if (windowSizeHasChanged(previousWidth, previousHeight, Gdx.graphics.getWidth(), Gdx.graphics.getHeight())) {
            previousWidth = Gdx.graphics.getWidth();
            previousHeight = Gdx.graphics.getHeight();

            reloadAllRenderers();
            return true;
        }
        previousWidth = Gdx.graphics.getWidth();
        previousHeight = Gdx.graphics.getHeight();
        return false;
    }

    private static boolean windowSizeHasChanged(int previousWidth, int previousHeight, int newWidth, int newHeight) {
        return !(previousWidth == newWidth && previousHeight == newHeight);
    }

    private static void reloadAllRenderers() {
        Buttons.reloadButtons();
    }//TODO get rid of this

    public static void changeScreen(@Null Screen oldScreen, Screen newScreen) {
        if (oldScreen != null)
            oldScreen.dispose();
        AsteraniaMain.INSTANCE.setScreen(newScreen);
        AsteraniaMain.INSTANCE.reloadRenderers();
    }
}
