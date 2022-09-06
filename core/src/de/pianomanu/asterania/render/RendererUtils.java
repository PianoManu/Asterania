package de.pianomanu.asterania.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.pianomanu.asterania.AsteraniaMain;

public class RendererUtils {
    private ShapeRenderer shapeRenderer = AsteraniaMain.INSTANCE.getShapeRenderer();

    public static RendererUtils getInstance() {
        return AsteraniaMain.INSTANCE.getRendererUtils();
    }

    public void rect(int x, int y, int width, int height, Color color, ShapeRenderer.ShapeType shapeType) {
        shapeRenderer.begin(shapeType);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();
    }

    /**
     * Creates a filled rectangle in the given color with the specified measurements.
     *
     * @param x      x Origin
     * @param y      y Origin
     * @param width  rectangle width
     * @param height rectangle height
     */
    public void rect(int x, int y, int width, int height, Color color) {
        rect(x, y, width, height, color, ShapeRenderer.ShapeType.Filled);
    }

    /**
     * Creates a black, unfilled rectangle with the specified measurements.
     *
     * @param x             x Origin
     * @param y             y Origin
     * @param width         rectangle width
     * @param height        rectangle height
     */
    public void rect(int x, int y, int width, int height) {
        rect(x, y, width, height, Color.BLACK, ShapeRenderer.ShapeType.Line);
    }

    public void rect(float x, float y, float width, float height, Color color, ShapeRenderer.ShapeType shapeType) {
        shapeRenderer.begin(shapeType);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();
    }

    /**
     * Creates a filled rectangle in the given color with the specified measurements.
     *
     * @param x             x Origin
     * @param y             y Origin
     * @param width         rectangle width
     * @param height        rectangle height
     */
    public void rect(float x, float y, float width, float height, Color color) {
        rect(x, y, width, height, color, ShapeRenderer.ShapeType.Filled);
    }

    /**
     * Creates a black, unfilled rectangle with the specified measurements.
     *
     * @param x             x Origin
     * @param y             y Origin
     * @param width         rectangle width
     * @param height        rectangle height
     */
    public void rect(float x, float y, float width, float height) {
        rect(x, y, width, height, Color.BLACK, ShapeRenderer.ShapeType.Line);
    }

    public void enableTransparency() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void disableTransparency() {
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
