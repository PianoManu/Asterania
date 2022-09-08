package de.pianomanu.asterania.world.entities.player.chat;

import com.badlogic.gdx.Gdx;

public class ChatElement {
    private String content;

    /**
     * Time (in milliseconds) how long a chat message will stay visible when sent/received, before fading out
     * ("notification display time"). The message can still be read when opening the chat
     */
    private int timer = 15000;

    /**
     * Determines when to start fading: if the value of {@link ChatElement#timer} becomes smaller than this value, the
     * Content of this ChatElement will start to fade
     */
    private int timeToStartFading = 5000;

    public ChatElement(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void tick() {
        if (this.timer >= (int) (Gdx.graphics.getDeltaTime() * 1000))
            this.timer -= (int) (Gdx.graphics.getDeltaTime() * 1000);
        else
            this.timer = 0;
    }

    public float getFadingPortion() {
        return this.timer >= this.timeToStartFading ? 1f : (float) this.timer / this.timeToStartFading;
    }
}
