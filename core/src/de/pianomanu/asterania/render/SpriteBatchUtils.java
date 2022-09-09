package de.pianomanu.asterania.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.pianomanu.asterania.AsteraniaMain;

public class SpriteBatchUtils {
    private final SpriteBatch batch = AsteraniaMain.INSTANCE.getBatch();

    public static SpriteBatchUtils getInstance() {
        return AsteraniaMain.INSTANCE.getSpriteBatchUtils();
    }

    public void begin() {
        batch.begin();
    }

    public void drawPlain(TextureRegion textureRegion, int x, int y, int width, int height, int rotation, boolean rotateClockwise, int scaleX, int scaleY, int originX, int originY) {
        batch.draw(textureRegion, x, y, originX, originY, width, height, scaleX, scaleY, rotation, rotateClockwise);
    }

    public void drawPlain(TextureRegion textureRegion, int x, int y, int width, int height, int rotation, boolean rotateClockwise, int scaleX, int scaleY) {
        drawPlain(textureRegion, x, y, width, height, rotation, rotateClockwise, scaleX, scaleY, 0, 0);
    }

    public void drawPlain(TextureRegion textureRegion, int x, int y, int width, int height, int rotation, boolean rotateClockwise) {
        drawPlain(textureRegion, x, y, width, height, rotation, rotateClockwise, 1, 1);
    }

    public void drawPlain(TextureRegion textureRegion, int x, int y, int width, int height, int rotation) {
        drawPlain(textureRegion, x, y, width, height, rotation, false);
    }

    public void drawPlain(TextureRegion textureRegion, int x, int y, int width, int height) {
        drawPlain(textureRegion, x, y, width, height, 0);
    }

    public void drawPlain(TextureRegion textureRegion, int x, int y) {
        batch.draw(textureRegion, x, y);
    }

    public void end() {
        batch.end();
    }

    public void draw(TextureRegion textureRegion, int x, int y, int width, int height, int rotation, boolean rotateClockwise, int scaleX, int scaleY, int originX, int originY) {
        begin();
        batch.draw(textureRegion, x, y, originX, originY, width, height, scaleX, scaleY, rotation, rotateClockwise);
        end();
    }

    public void draw(TextureRegion textureRegion, int x, int y, int width, int height, int rotation, boolean rotateClockwise, int scaleX, int scaleY) {
        draw(textureRegion, x, y, width, height, rotation, rotateClockwise, scaleX, scaleY, 0, 0);
    }

    public void draw(TextureRegion textureRegion, int x, int y, int width, int height, int rotation, boolean rotateClockwise) {
        draw(textureRegion, x, y, width, height, rotation, rotateClockwise, 1, 1);
    }

    public void draw(TextureRegion textureRegion, int x, int y, int width, int height, int rotation) {
        draw(textureRegion, x, y, width, height, rotation, false);
    }

    public void draw(TextureRegion textureRegion, int x, int y, int width, int height) {
        draw(textureRegion, x, y, width, height, 0);
    }

    public void draw(TextureRegion textureRegion, int x, int y) {
        begin();
        batch.draw(textureRegion, x, y);
        end();
    }
}
