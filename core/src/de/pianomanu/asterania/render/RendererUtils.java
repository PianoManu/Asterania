package de.pianomanu.asterania.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class RendererUtils {
    public static void rect(ShapeRenderer shapeRenderer, int x, int y, int width, int height, Color color, ShapeRenderer.ShapeType shapeType) {
        shapeRenderer.begin(shapeType);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();
    }

    /**
     * Creates a filled rectangle in the given color with the specified measurements.
     *
     * @param shapeRenderer
     * @param x             x Origin
     * @param y             y Origin
     * @param width         rectangle width
     * @param height        rectangle height
     */
    public static void rect(ShapeRenderer shapeRenderer, int x, int y, int width, int height, Color color) {
        rect(shapeRenderer, x, y, width, height, color, ShapeRenderer.ShapeType.Filled);
    }

    /**
     * Creates a black, unfilled rectangle with the specified measurements.
     *
     * @param shapeRenderer
     * @param x             x Origin
     * @param y             y Origin
     * @param width         rectangle width
     * @param height        rectangle height
     */
    public static void rect(ShapeRenderer shapeRenderer, int x, int y, int width, int height) {
        rect(shapeRenderer, x, y, width, height, Color.BLACK, ShapeRenderer.ShapeType.Line);
    }
}
