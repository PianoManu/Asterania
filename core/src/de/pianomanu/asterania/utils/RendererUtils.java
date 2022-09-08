package de.pianomanu.asterania.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.pianomanu.asterania.AsteraniaMain;

public class RendererUtils {
    private final ShapeRenderer shapeRenderer = AsteraniaMain.INSTANCE.getShapeRenderer();

    public static RendererUtils getInstance() {
        return AsteraniaMain.INSTANCE.getRendererUtils();
    }

    public void begin() {
        begin(ShapeRenderer.ShapeType.Filled);
    }

    public void begin(ShapeRenderer.ShapeType shapeType) {
        shapeRenderer.begin(shapeType);
    }

    public void rectPlain(int x, int y, int width, int height, Color color) {
        shapeRenderer.setColor(color);
        shapeRenderer.rect(x, y, width, height);
    }

    public void rectPlain(float x, float y, float width, float height, Color color) {
        rectPlain((int) x, (int) y, (int) width, (int) height, color);
    }

    public void ellipsePlain(float x, float y, float width, float height, Color color) {
        shapeRenderer.setColor(color);
        shapeRenderer.ellipse(x, y, width, height);
    }

    public void linePlain(int xStart, int yStart, int xEnd, int yEnd, Color color) {
        shapeRenderer.setColor(color);
        shapeRenderer.line(xStart, yStart, xEnd, yEnd);
    }

    public void end() {
        shapeRenderer.end();
    }

    public void rect(int x, int y, int width, int height, Color color, ShapeRenderer.ShapeType shapeType) {
        shapeRenderer.begin(shapeType);
        rectPlain(x, y, width, height, color);
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
        rectPlain(x, y, width, height, color);
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

    public void ellipse(float x, float y, float width, float height, Color color) {
        ellipse(x, y, width, height, color, ShapeRenderer.ShapeType.Filled);
    }

    public void ellipse(float x, float y, float width, float height, Color color, ShapeRenderer.ShapeType shapeType) {
        begin(shapeType);
        ellipsePlain(x, y, width, height, color);
        end();
    }

    public static void enableTransparency() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void disableTransparency() {
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public void line(int xStart, int yStart, int xEnd, int yEnd, Color color) {
        begin(ShapeRenderer.ShapeType.Line);
        linePlain(xStart, yStart, xEnd, yEnd, color);
        end();
    }
}
