package de.pianomanu.asterania.render.text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import de.pianomanu.asterania.config.DisplayConfig;

public class TextRenderer {
    private static SpriteBatch batch = new SpriteBatch();
    private static BitmapFont font = new BitmapFont(Gdx.files.internal("font/asteraniafont.fnt"));
    private static ShapeRenderer shapeRenderer = new ShapeRenderer();
    private static GlyphLayout glyphLayout = new GlyphLayout();

    public static void renderText(int startX, int startY, String content) {
        renderText(startX, startY, content, true, DisplayConfig.TEXT_SIZE, false, Color.BLACK, new Color(0.3f, 0.3f, 0.3f, 0.4f));
    }

    public static void renderText(int startX, int startY, String content, Color textColor) {
        renderText(startX, startY, content, true, DisplayConfig.TEXT_SIZE, false, textColor, new Color(0.3f, 0.3f, 0.3f, 0.4f));
    }

    public static void renderText(int startX, int startY, String content, Color textColor, Color rectangleColor) {
        renderText(startX, startY, content, true, DisplayConfig.TEXT_SIZE, true, textColor, rectangleColor);
    }

    public static void renderText(int startX, int startY, String content, boolean isCentered, float textSize, boolean addBackgroundRectangle, Color textColor, Color rectangleColor) {
        int xOffset = 0;
        int yOffset = 0;
        font.getData().setScale(textSize);
        glyphLayout.setText(font, content);
        if (isCentered) {
            xOffset = (int) (glyphLayout.width / 2);
            yOffset = (int) (glyphLayout.height / 2);
        }
        if (addBackgroundRectangle) {
            if (isCentered) {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(rectangleColor);
                shapeRenderer.rect((startX - 4) - (glyphLayout.width / 2), (startY - 4) - (glyphLayout.height / 2), glyphLayout.width + 8, glyphLayout.height + 8);
                shapeRenderer.end();
            } else {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(rectangleColor);
                shapeRenderer.rect((startX - 4), (startY - 4 - glyphLayout.height), glyphLayout.width + 8, glyphLayout.height + 8);
                shapeRenderer.end();
            }
        }
        batch.begin();
        font.setColor(textColor);
        font.draw(batch, content, startX - xOffset, startY + yOffset);
        batch.end();
    }

    public static void reloadTextRenderers() {
        batch.dispose();
        font.dispose();
        shapeRenderer.dispose();
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font/asteraniafont.fnt"));
        shapeRenderer = new ShapeRenderer();
        glyphLayout = new GlyphLayout();
    }

    public static Vector2 getTextDimensions(String content) {
        Vector2 dimensions = new Vector2();
        reloadTextRenderers();

        font.getData().setScale(DisplayConfig.TEXT_SIZE);
        glyphLayout.setText(font, content);
        dimensions.x = glyphLayout.width;
        dimensions.y = glyphLayout.height;

        reloadTextRenderers();

        return dimensions;
    }
}
