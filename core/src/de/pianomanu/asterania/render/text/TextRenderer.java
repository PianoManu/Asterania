package de.pianomanu.asterania.render.text;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextRenderer {
    private static SpriteBatch batch = new SpriteBatch();
    private static BitmapFont font = new BitmapFont();

    public static void renderText(int startX, int startY, String content) {
        batch.begin();
        font.draw(batch, content, startX, startY);
        batch.end();
    }

    public static void reloadTextRenderers() {
        batch.dispose();
        font.dispose();
        batch = new SpriteBatch();
        font = new BitmapFont();
    }
}
