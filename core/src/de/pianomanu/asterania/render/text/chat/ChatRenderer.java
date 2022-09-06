package de.pianomanu.asterania.render.text.chat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.entities.player.chat.ChatElement;
import de.pianomanu.asterania.render.RendererUtils;
import de.pianomanu.asterania.render.text.TextRenderer;

public class ChatRenderer {

    private static boolean chatIsOpen = false;

    public static void renderAll(Player player, ShapeRenderer shapeRenderer) {
        if (chatIsOpen) {
            renderTextLine(player, shapeRenderer);
        }
        if (chatIsOpen) {
            renderChatLog(player, shapeRenderer);
        } else {
            renderChatLog(player, shapeRenderer, true);
        }
    }

    private static void renderTextLine(Player player, ShapeRenderer shapeRenderer) {
        RendererUtils.enableTransparency();
        RendererUtils.rect(shapeRenderer, 40, 40, Gdx.graphics.getWidth() - 80, 40, new Color(1, 1, 1, 0.6f));
        RendererUtils.disableTransparency();
        TextRenderer.renderText(50, 70, player.getChat().getCurrentMessage(), false, 0.8f, false, Color.BLACK, Color.WHITE);
    }

    private static void renderChatLog(Player player, ShapeRenderer shapeRenderer) {
        renderChatLog(player, shapeRenderer, false);
    }

    private static void renderChatLog(Player player, ShapeRenderer shapeRenderer, boolean shouldFade) {
        int chatSize = player.getChat().getMessages().size();
        float intensity = 1;

        RendererUtils.enableTransparency();

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

        RendererUtils.disableTransparency();

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
