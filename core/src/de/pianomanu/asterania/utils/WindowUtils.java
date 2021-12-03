package de.pianomanu.asterania.utils;

import com.badlogic.gdx.Gdx;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.render.DebugScreenRenderer;
import de.pianomanu.asterania.render.button.Buttons;
import de.pianomanu.asterania.render.text.TextInputBoxRenderer;
import de.pianomanu.asterania.render.text.TextRenderer;

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
        DebugScreenRenderer.reloadGridRenderer();
        TextRenderer.reloadTextRenderers();
        Buttons.reloadButtons();
        TextInputBoxRenderer.reloadTextInputBoxRenderers();
    }
}
