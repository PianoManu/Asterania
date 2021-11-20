package de.pianomanu.asterania.render.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.world.World;

public class TileBreakingUI {
    public static boolean renderNoBreakingPossible = false;

    public static void renderTileBreakingUI(World world, ShapeRenderer shapeRenderer) {
        Player player = world.getPlayer();
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

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0.3f, 0.3f, 0.3f, 1);
            shapeRenderer.rect(startX, startY, rectWidth, rectHeight);

            shapeRenderer.setColor(0, 0.8f, 0, 1);
            shapeRenderer.rect(barX, barY, barWidth, barHeight);
            shapeRenderer.end();
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

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0.3f, 0.3f, 0.3f, 1);
            shapeRenderer.rect(startX, startY, rectWidth, rectHeight);

            shapeRenderer.setColor(0.8f, 0f, 0, 1);
            shapeRenderer.rect(barX, barY, barWidth, barHeight);
            shapeRenderer.end();
        }
    }
}
