package de.pianomanu.asterania.render.text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.utils.RendererUtils;
import de.pianomanu.asterania.utils.text.TextInputBox;

public class TextInputBoxRenderer {
    private SpriteBatch batch = AsteraniaMain.INSTANCE.getBatch();
    private BitmapFont font = AsteraniaMain.INSTANCE.getFont();
    private GlyphLayout glyphLayout = AsteraniaMain.INSTANCE.getGlyphLayout();

    private static float cursorCounter = 0;
    private static boolean showCursorThisHalfSecond = true;

    public static TextInputBoxRenderer getInstance() {
        return AsteraniaMain.INSTANCE.getTextInputBoxRenderer();
    }

    public void renderTextInputBox(TextInputBox box) {
        renderTextInputBox(box, false, DisplayConfig.TEXT_SIZE, true);

    }

    public void renderTextInputBox(TextInputBox box, boolean isCentered, float textSize, boolean renderCursor) {
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
            RendererUtils.getInstance().rect(box.getStart().x - xOffset / 2f, box.getStart().y + yOffset / 2f, dif.x + xOffset, dif.y - yOffset, box.getBackgroundColor());
        } else {
            RendererUtils.getInstance().rect(box.getStart().x, box.getStart().y, dif.x, dif.y, box.getBackgroundColor());
        }

        batch.begin();
        font.setColor(box.getTextColor());
        font.draw(batch, box.getInputString(), box.getStart().x + glyphLayout.height, box.getStart().y + dif.y / 2 + glyphLayout.height / 2);
        batch.end();

        if (renderCursor) {
            renderCursor(box.getTextColor(), (int) (box.getStart().x + glyphLayout.height + glyphLayout.width), (int) (box.getStart().y + dif.y / 2 + glyphLayout.height / 2));
        }
    }

    private void renderCursor(Color cursorColor, int xStart, int yStart) {
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


    public Vector2 getTextDimensions(String content) {
        Vector2 dimensions = new Vector2();
        font.getData().setScale(DisplayConfig.TEXT_SIZE);
        glyphLayout.setText(font, content);
        dimensions.x = glyphLayout.width;
        dimensions.y = glyphLayout.height;
        return dimensions;
    }
}
