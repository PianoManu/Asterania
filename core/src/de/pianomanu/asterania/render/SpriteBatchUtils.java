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

    public void drawPlain(TextureRegion textureRegion, int x, int y, int width, int height, int rotation, int scaleX, int scaleY, int originX, int originY) {
        batch.draw(textureRegion, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
    }

    public void drawPlain(TextureRegion textureRegion, int x, int y, int width, int height, int rotation, int scaleX, int scaleY) {
        drawPlain(textureRegion, x, y, width, height, rotation, scaleX, scaleY, 0, 0);
    }

    public void drawPlain(TextureRegion textureRegion, int x, int y, int width, int height, int rotation) {
        drawPlain(textureRegion, x, y, width, height, rotation, 1, 1);
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

    public void draw(TextureRegion textureRegion, int x, int y, int width, int height, int rotation, int scaleX, int scaleY, int originX, int originY) {
        begin();
        drawPlain(textureRegion, x, y, width, height, rotation, scaleX, scaleY, originX, originY);
        end();
    }

    public void draw(TextureRegion textureRegion, int x, int y, int width, int height, int rotation, int scaleX, int scaleY) {
        draw(textureRegion, x, y, width, height, rotation, scaleX, scaleY, 0, 0);
    }

    public void draw(TextureRegion textureRegion, int x, int y, int width, int height, int rotation) {
        draw(textureRegion, x, y, width, height, rotation, 1, 1);
    }

    public void draw(TextureRegion textureRegion, int x, int y, int width, int height) {
        begin();
        drawPlain(textureRegion, x, y, width, height);
        end();
    }

    public void draw(TextureRegion textureRegion, int x, int y) {
        begin();
        batch.draw(textureRegion, x, y);
        end();
    }
}
