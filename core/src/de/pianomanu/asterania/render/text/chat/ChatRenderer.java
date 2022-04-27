package de.pianomanu.asterania.render.text.chat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.entities.player.chat.ChatElement;
import de.pianomanu.asterania.render.text.TextRenderer;

public class ChatRenderer {

    private static boolean chatIsOpen = false;

    public static void renderAll(ShapeRenderer shapeRenderer) {
        if (chatIsOpen) {
            renderTextLine(shapeRenderer);
        }
        if (chatIsOpen) {
            renderChatLog(shapeRenderer);
        } else {
            renderChatLog(shapeRenderer, true);
        }
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
        renderChatLog(shapeRenderer, false);
    }

    private static void renderChatLog(ShapeRenderer shapeRenderer, boolean shouldFade) {
        Player player = AsteraniaMain.player;
        int chatSize = player.getChat().getMessages().size();
        float intensity = 1;

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int i = 0; i < chatSize; i++) {
            ChatElement e = player.getChat().getMessages().get(i);
            if (shouldFade) {
                intensity = e.getFadingPortion();
            }
            shapeRenderer.setColor(1, 1, 1, 0.3f * intensity);
            shapeRenderer.rect(40, 40 * (chatSize - i) + 60, Gdx.graphics.getWidth() - 80, 40);
        }
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        for (int i = 0; i < chatSize; i++) {
            ChatElement e = player.getChat().getMessages().get(i);
            String s = e.getContent();
            if (shouldFade)
                TextRenderer.renderText(50, 40 * (chatSize - i) + 90, s, false, 0.8f, false, Color.BLACK, Color.WHITE, true, e.getFadingPortion());
            else
                TextRenderer.renderText(50, 40 * (chatSize - i) + 90, s, false, 0.8f, false, Color.BLACK, Color.WHITE, true, 1);
        }
    }

    public static boolean isChatOpen() {
        return chatIsOpen;
    }

    public static void setChatIsOpen(boolean chatIsOpen) {
        ChatRenderer.chatIsOpen = chatIsOpen;
    }
}
