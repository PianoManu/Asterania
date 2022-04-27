package de.pianomanu.asterania.lifecycle;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.entities.player.chat.ChatElement;

public class ChatUpdates {
    public static void updateChat() {
        for (ChatElement e : AsteraniaMain.player.getChat().getMessages()) {
            e.tick();
        }
    }
}
