package de.pianomanu.asterania.render.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.render.RendererUtils;
import de.pianomanu.asterania.render.text.TextRenderer;

public class TileBreakingUI {
    public static boolean renderNoBreakingPossible = false;
    public static String renderNoBreakingPossibleMessage = "";

    public static void renderTileBreakingUI(Player player) {
        if (player.isBreakingTile() && !renderNoBreakingPossible) {
            int width = Gdx.graphics.getWidth();
            int height = Gdx.graphics.getHeight();

            int startX = width / 100;
            int startY = height * 4 / 5;
            int rectWidth = width / 50;
            int rectHeight = height / 6;

            int barX = startX + rectWidth / 4;
            int barY = startY + rectHeight / 20;
            int barWidth = rectWidth / 2;
            int barHeight = (int) (player.getCurrentBreakingPercentage() * rectHeight * 9 / 10);

            RendererUtils.getInstance().rect(startX, startY, rectWidth, rectHeight, new Color(0.3f, 0.3f, 0.3f, 1));
            RendererUtils.getInstance().rect(barX, barY, barWidth, barHeight, new Color(0, 0.8f, 0, 1));
        }
        if (renderNoBreakingPossible) {
            int width = Gdx.graphics.getWidth();
            int height = Gdx.graphics.getHeight();

            int startX = width / 100;
            int startY = height * 4 / 5;
            int rectWidth = width / 50;
            int rectHeight = height / 6;

            int barX = startX + rectWidth / 4;
            int barY = startY + rectHeight / 20;
            int barWidth = rectWidth / 2;
            int barHeight = rectHeight * 9 / 10;

            RendererUtils.getInstance().rect(startX, startY, rectWidth, rectHeight, new Color(0.3f, 0.3f, 0.3f, 1));
            RendererUtils.getInstance().rect(barX, barY, barWidth, barHeight, new Color(0.8f, 0f, 0, 1));

            int xOffset = (int) TextRenderer.getTextDimensions(renderNoBreakingPossibleMessage).x;
            int yOffset = (int) TextRenderer.getTextDimensions(renderNoBreakingPossibleMessage).y;
            TextRenderer.renderText(barX, barY - 2 * yOffset, renderNoBreakingPossibleMessage, false, DisplayConfig.TEXT_SIZE, true, new Color(0.8f, 0, 0, 1), new Color(0.3f, 0.3f, 0.3f, 1));
            renderNoBreakingPossibleMessage = "";
        }
    }

    public static void enableNoBreakingPossibleMessage(String message) {
        renderNoBreakingPossible = true;
        renderNoBreakingPossibleMessage = message;
    }

    public static void disableNoBreakingPossibleMessage() {
        renderNoBreakingPossible = false;
    }
}
