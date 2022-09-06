package de.pianomanu.asterania.render;

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
        this.shapeRenderer.begin(shapeType);
    }

    public void rect(int x, int y, int width, int height, Color color) {
        this.shapeRenderer.setColor(color);
        shapeRenderer.rect(x, y, width, height);
    }

    public void rect(float x, float y, float width, float height, Color color) {
        rect((int) x, (int) y, (int) width, (int) height, color);
    }

    public void end() {
        this.shapeRenderer.end();
    }

    public void rectFull(int x, int y, int width, int height, Color color, ShapeRenderer.ShapeType shapeType) {
        begin(shapeType);
        rect(x, y, width, height, color);
        end();
    }

    /**
     * Creates a filled rectangle in the given color with the specified measurements.
     *
     * @param x      x Origin
     * @param y      y Origin
     * @param width  rectangle width
     * @param height rectangle height
     */
    public void rectFull(int x, int y, int width, int height, Color color) {
        rectFull(x, y, width, height, color, ShapeRenderer.ShapeType.Filled);
    }

    /**
     * Creates a black, unfilled rectangle with the specified measurements.
     *
     * @param x      x Origin
     * @param y      y Origin
     * @param width  rectangle width
     * @param height rectangle height
     */
    public void rectFull(int x, int y, int width, int height) {
        rectFull(x, y, width, height, Color.BLACK, ShapeRenderer.ShapeType.Line);
    }

    public void rectFull(float x, float y, float width, float height, Color color, ShapeRenderer.ShapeType shapeType) {
        begin(shapeType);
        rect(x, y, width, height, color);
        end();
    }

    /**
     * Creates a filled rectangle in the given color with the specified measurements.
     *
     * @param x      x Origin
     * @param y      y Origin
     * @param width  rectangle width
     * @param height rectangle height
     */
    public void rectFull(float x, float y, float width, float height, Color color) {
        rectFull(x, y, width, height, color, ShapeRenderer.ShapeType.Filled);
    }

    /**
     * Creates a black, unfilled rectangle with the specified measurements.
     *
     * @param x      x Origin
     * @param y      y Origin
     * @param width  rectangle width
     * @param height rectangle height
     */
    public void rectFull(float x, float y, float width, float height) {
        rectFull(x, y, width, height, Color.BLACK, ShapeRenderer.ShapeType.Line);
    }

    public void enableTransparency() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void disableTransparency() {
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
