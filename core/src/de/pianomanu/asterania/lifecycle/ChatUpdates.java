package de.pianomanu.asterania.lifecycle;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.world.entities.Player;
import de.pianomanu.asterania.world.entities.player.chat.ChatElement;

public class ChatUpdates {
    public static void updateChat(Player player) {
        for (ChatElement e : AsteraniaMain.INSTANCE.getCurrentActiveSavegame().getCurrentActivePlayer().getChat().getMessages()) {
            e.tick();
        }
    }
}
