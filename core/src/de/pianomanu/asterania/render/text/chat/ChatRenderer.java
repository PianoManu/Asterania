package de.pianomanu.asterania.render.text.chat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import de.pianomanu.asterania.render.ShapeRendererUtils;
import de.pianomanu.asterania.render.text.TextRenderer;
import de.pianomanu.asterania.world.entities.Player;
import de.pianomanu.asterania.world.entities.player.chat.ChatElement;

public class ChatRenderer {

    private static boolean chatIsOpen = false;

    public static void renderAll(Player player) {
        if (chatIsOpen) {
            renderTextLine(player);
        }
        if (chatIsOpen) {
            renderChatLog(player);
        } else {
            renderChatLog(player, true);
        }
    }

    private static void renderTextLine(Player player) {
        ShapeRendererUtils.enableTransparency();
        ShapeRendererUtils.getInstance().rect(40, 40, Gdx.graphics.getWidth() - 80, 40, new Color(1, 1, 1, 0.6f));
        ShapeRendererUtils.disableTransparency();
        TextRenderer.getInstance().renderText(50, 70, player.getChat().getCurrentMessage(), false, 0.8f, false, Color.BLACK, Color.WHITE);
    }

    private static void renderChatLog(Player player) {
        renderChatLog(player, false);
    }

    private static void renderChatLog(Player player, boolean shouldFade) {
        int chatSize = player.getChat().getMessages().size();
        float intensity = 1;

        ShapeRendererUtils.enableTransparency();

        ShapeRendererUtils.getInstance().begin();

        for (int i = 0; i < chatSize; i++) {
            ChatElement e = player.getChat().getMessages().get(i);
            if (shouldFade) {
                intensity = e.getFadingPortion();
            }
            ShapeRendererUtils.getInstance().rectPlain(40, 40 * (chatSize - i) + 60, Gdx.graphics.getWidth() - 80, 40, new Color(1, 1, 1, 0.3f * intensity));
        }
        ShapeRendererUtils.getInstance().end();

        ShapeRendererUtils.disableTransparency();

        for (int i = 0; i < chatSize; i++) {
            ChatElement e = player.getChat().getMessages().get(i);
            String s = e.getContent();
            if (shouldFade)
                TextRenderer.getInstance().renderText(50, 40 * (chatSize - i) + 90, s, false, 0.8f, false, Color.BLACK, Color.WHITE, true, e.getFadingPortion());
            else
                TextRenderer.getInstance().renderText(50, 40 * (chatSize - i) + 90, s, false, 0.8f, false, Color.BLACK, Color.WHITE, true, 1);
        }
    }

    public static boolean isChatOpen() {
        return chatIsOpen;
    }

    public static void setChatIsOpen(boolean chatIsOpen) {
        ChatRenderer.chatIsOpen = chatIsOpen;
    }
}
