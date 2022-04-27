package de.pianomanu.asterania.entities.player.chat;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    private final List<ChatElement> CHAT_LOG = new ArrayList<>();

    private String currentMessage = "";

    public Chat() {

    }

    private void openChat() {

    }

    public List<ChatElement> getMessages() {
        return this.CHAT_LOG;
    }

    public String getCurrentMessage() {
        return this.currentMessage;
    }

    public void setCurrentMessage(String currentMessage) {
        this.currentMessage = currentMessage;
    }

    public boolean isEmpty() {
        if (this.CHAT_LOG.size() == 0)
            return true;
        for (ChatElement e : this.CHAT_LOG) {
            String s = e.getContent();
            if (!s.isEmpty())
                return false;
        }
        return true;
    }

    public boolean addLastMessageToChat() {
        if (!currentMessage.isEmpty()) {
            this.CHAT_LOG.add(new ChatElement(currentMessage));
            currentMessage = "";
            return true;
        }
        return false;
    }
}
