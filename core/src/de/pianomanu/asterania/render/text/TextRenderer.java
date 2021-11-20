package de.pianomanu.asterania.render.text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.pianomanu.asterania.config.DisplayConfig;

public class TextRenderer {
    private static SpriteBatch batch = new SpriteBatch();
    private static BitmapFont font = new BitmapFont(Gdx.files.internal("font/asterania_font.fnt"));
    private static ShapeRenderer shapeRenderer = new ShapeRenderer();
    private static GlyphLayout glyphLayout = new GlyphLayout();

    public static void renderText(int startX, int startY, String content) {
        renderText(startX, startY, content, true, DisplayConfig.TEXT_SIZE, false);
    }

    public static void renderText(int startX, int startY, String content, boolean isCentered, float textSize, boolean addBackgroundRectangle) {
        int xOffset = 0;
        int yOffset = 0;
        if (isCentered) {
            glyphLayout.setText(font, content);
            xOffset = (int) (glyphLayout.width / 2);
            yOffset = (int) (glyphLayout.height / 2);
        }
        if (addBackgroundRectangle) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1, 1, 1, 1);
            shapeRenderer.rect((startX - 4) - (glyphLayout.width / 2), (startY - 4) - (glyphLayout.height / 2), glyphLayout.width + 8, glyphLayout.height + 8);
            shapeRenderer.end();
        }
        batch.begin();
        font.getData().setScale(textSize);
        font.draw(batch, content, startX - xOffset, startY + yOffset);
        batch.end();
    }

    public static void reloadTextRenderers() {
        batch.dispose();
        font.dispose();
        shapeRenderer.dispose();
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font/asterania_font.fnt"));
        shapeRenderer = new ShapeRenderer();
        glyphLayout = new GlyphLayout();
    }
}
