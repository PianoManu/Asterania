package de.pianomanu.asterania.render.text;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.render.RendererUtils;

public class TextRenderer {
    private SpriteBatch batch = AsteraniaMain.INSTANCE.getBatch();
    private BitmapFont font = AsteraniaMain.INSTANCE.getFont();
    private GlyphLayout glyphLayout = AsteraniaMain.INSTANCE.getGlyphLayout();

    public static TextRenderer getInstance() {
        return AsteraniaMain.INSTANCE.getTextRenderer();
    }

    public void renderText(int startX, int startY, String content) {
        renderText(startX, startY, content, true, DisplayConfig.TEXT_SIZE, false, Color.BLACK, new Color(0.3f, 0.3f, 0.3f, 0.4f));
    }

    public void renderText(int startX, int startY, String content, boolean isCentered) {
        renderText(startX, startY, content, isCentered, DisplayConfig.TEXT_SIZE, false, Color.BLACK, new Color(0.3f, 0.3f, 0.3f, 0.4f));
    }

    public void renderText(int startX, int startY, String content, Color textColor) {
        renderText(startX, startY, content, true, DisplayConfig.TEXT_SIZE, false, textColor, new Color(0.3f, 0.3f, 0.3f, 0.4f));
    }

    public void renderText(int startX, int startY, String content, Color textColor, Color rectangleColor) {
        renderText(startX, startY, content, true, DisplayConfig.TEXT_SIZE, true, textColor, rectangleColor);
    }

    public void renderText(int startX, int startY, String content, boolean isCentered, float textSize, boolean addBackgroundRectangle, Color textColor, Color rectangleColor) {
        renderText(startX, startY, content, isCentered, textSize, addBackgroundRectangle, textColor, rectangleColor, false, 0);
    }

    public void renderText(int startX, int startY, String content, boolean isCentered, float textSize, boolean addBackgroundRectangle, Color textColor, Color rectangleColor, boolean enableTransparency, float intensity) {
        int xOffset = 0;
        int yOffset = 0;
        font.getData().setScale(textSize);
        glyphLayout.setText(font, content);
        if (isCentered) {
            xOffset = (int) (glyphLayout.width / 2);
            yOffset = (int) (glyphLayout.height / 2);
        }

        if (enableTransparency) {
            RendererUtils.enableTransparency();
            rectangleColor.set(rectangleColor.r, rectangleColor.g, rectangleColor.b, intensity);
            textColor.set(textColor.r, textColor.g, textColor.b, intensity);
        }

        if (addBackgroundRectangle) {
            if (isCentered) {
                RendererUtils.getInstance().rect((startX - 4) - (glyphLayout.width / 2), (startY - 4) - (glyphLayout.height / 2), glyphLayout.width + 8, glyphLayout.height + 8, rectangleColor);
            } else {
                RendererUtils.getInstance().rect((startX - 4), (startY - 4 - glyphLayout.height), glyphLayout.width + 8, glyphLayout.height + 8, rectangleColor);
            }
        }
        batch.begin();
        font.setColor(textColor);
        font.draw(batch, content, startX - xOffset, startY + yOffset);
        batch.end();

        if (enableTransparency) {
            RendererUtils.disableTransparency();
        }
    }

    public Vector2 getTextDimensions(String content) {
        Vector2 dimensions = new Vector2();
        font.getData().setScale(DisplayConfig.TEXT_SIZE);
        glyphLayout.setText(font, content);
        dimensions.x = glyphLayout.width;
        dimensions.y = glyphLayout.height;
        return dimensions;
    }
}
