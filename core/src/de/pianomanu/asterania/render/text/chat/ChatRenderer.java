package de.pianomanu.asterania.render.text.chat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.render.text.TextRenderer;

public class ChatRenderer {

    private static boolean chatIsOpen = false;

    public static void renderAll(ShapeRenderer shapeRenderer) {
        if (chatIsOpen) {
            renderTextLine(shapeRenderer);
        }
        renderChatLog(shapeRenderer);
    }

    private static void renderTextLine(ShapeRenderer shapeRenderer) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 0.6f);
        shapeRenderer.rect(40, 40, Gdx.graphics.getWidth() - 80, 40);
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        Player player = AsteraniaMain.player;
        TextRenderer.renderText(50, 70, player.getChat().getCurrentMessage(), false, 0.8f, false, Color.BLACK, Color.WHITE);
    }

    private static void renderChatLog(ShapeRenderer shapeRenderer) {
        Player player = AsteraniaMain.player;
        int chatSize = player.getChat().getMessages().size();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 0.3f);
        shapeRenderer.rect(40, 100, Gdx.graphics.getWidth() - 80, 40 * chatSize);
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        for (int i = 0; i < chatSize; i++) {
            String s = player.getChat().getMessages().get(i);
            TextRenderer.renderText(50, 40 * (chatSize - i) + 90, s, false, 0.8f, false, Color.BLACK, Color.WHITE);
        }
    }

    public static boolean isChatOpen() {
        return chatIsOpen;
    }

    public static void setChatIsOpen(boolean chatIsOpen) {
        ChatRenderer.chatIsOpen = chatIsOpen;
    }
}
