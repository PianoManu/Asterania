package de.pianomanu.asterania.render.text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.render.RendererUtils;
import de.pianomanu.asterania.utils.TextInputBox;

public class TextInputBoxRenderer {
    private static SpriteBatch batch = new SpriteBatch();
    private static BitmapFont font = new BitmapFont(Gdx.files.internal("font/asteraniafont.fnt"));
    private static ShapeRenderer shapeRenderer = new ShapeRenderer();
    private static GlyphLayout glyphLayout = new GlyphLayout();

    private static float cursorCounter = 0;
    private static boolean showCursorThisHalfSecond = true;

    public static void renderTextInputBox(TextInputBox box) {
        renderTextInputBox(box, false, DisplayConfig.TEXT_SIZE, true);

    }

    public static void renderTextInputBox(TextInputBox box, boolean isCentered, float textSize, boolean renderCursor) {
        Vector2 dif = new Vector2(box.getEnd().x - box.getStart().x, box.getEnd().y - box.getStart().y);
        int xOffset = 0;
        int yOffset = 0;
        font.getData().setScale(textSize);
        glyphLayout.setText(font, box.getInputString());
        if (isCentered) {
            xOffset = (int) (glyphLayout.width / 2);
            yOffset = (int) (glyphLayout.height / 2);
        }

        if (isCentered) {
            RendererUtils.getInstance().rectFull(box.getStart().x - xOffset / 2f, box.getStart().y + yOffset / 2f, dif.x + xOffset, dif.y - yOffset, box.getBackgroundColor());
        } else {
            RendererUtils.getInstance().rectFull(box.getStart().x, box.getStart().y, dif.x, dif.y, box.getBackgroundColor());
        }

        batch.begin();
        font.setColor(box.getTextColor());
        font.draw(batch, box.getInputString(), box.getStart().x + glyphLayout.height, box.getStart().y + dif.y / 2 + glyphLayout.height / 2);
        batch.end();

        if (renderCursor) {
            renderCursor(box.getTextColor(), (int) (box.getStart().x + glyphLayout.height + glyphLayout.width), (int) (box.getStart().y + dif.y / 2 + glyphLayout.height / 2));
        }
    }

    private static void renderCursor(Color cursorColor, int xStart, int yStart) {
        cursorCounter += Gdx.graphics.getDeltaTime();
        if (cursorCounter > 0.5) {
            cursorCounter -= 0.5;
            showCursorThisHalfSecond = !showCursorThisHalfSecond;
        }

        if (showCursorThisHalfSecond) {
            batch.begin();
            font.setColor(cursorColor);
            font.draw(batch, "_", xStart + 2, yStart);
            batch.end();
        }
    }

    public static void reloadTextInputBoxRenderers() {
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
        reloadTextInputBoxRenderers();

        font.getData().setScale(DisplayConfig.TEXT_SIZE);
        glyphLayout.setText(font, content);
        dimensions.x = glyphLayout.width;
        dimensions.y = glyphLayout.height;

        reloadTextInputBoxRenderers();

        return dimensions;
    }
}
