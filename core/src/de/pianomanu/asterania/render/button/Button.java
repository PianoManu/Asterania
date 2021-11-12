package de.pianomanu.asterania.render.button;

import com.badlogic.gdx.math.Vector2;

public class Button {
    public static final String BUTTON16x8NAME = "gen16x8";
    public static final String BUTTON32x8NAME = "gen32x8";
    public static final String BUTTON64x8NAME = "gen64x8";
    public static final String BUTTON128x8NAME = "gen128x8";

    public static final Vector2 BUTTON16x8FORMAT = new Vector2(16, 8);
    public static final Vector2 BUTTON32x8FORMAT = new Vector2(32, 8);
    public static final Vector2 BUTTON64x8FORMAT = new Vector2(64, 8);
    public static final Vector2 BUTTON128x8FORMAT = new Vector2(128, 8);
    private final Vector2 format;
    private final String buttonText;
    private Vector2 start;
    private Vector2 end;
    private boolean isClicked = false;

    public Button(Vector2 start, Vector2 end, String buttonText) {
        this.start = start;
        this.end = end;
        this.format = end.sub(start);
        this.buttonText = buttonText;
    }

    public Button(Vector2 format, String buttonText) {
        this.start = new Vector2();
        this.end = new Vector2();
        this.format = format;
        this.buttonText = buttonText;
    }

    public Vector2 getStart() {
        return this.start;
    }

    public Vector2 getEnd() {
        return this.end;
    }

    public boolean isClicked() {
        return this.isClicked;
    }

    public void unsetClicked() {
        this.isClicked = false;
    }

    public void setClicked() {
        this.isClicked = true;
    }

    public Vector2 getFormat() {
        return this.format;
    }

    public String getTextureName() {
        if (this.getFormat().equals(BUTTON16x8FORMAT))
            return BUTTON16x8NAME;
        if (this.getFormat().equals(BUTTON32x8FORMAT))
            return BUTTON32x8NAME;
        if (this.getFormat().equals(BUTTON64x8FORMAT))
            return BUTTON64x8NAME;
        if (this.getFormat().equals(BUTTON128x8FORMAT))
            return BUTTON128x8NAME;
        return "";
    }

    public void moveToCentered(Vector2 destination) {
        this.start.x = destination.x - format.x / 2;
        this.start.y = destination.y - format.y / 2;
        this.end.x = destination.x + format.x / 2;
        this.end.y = destination.y + format.y / 2;
    }

    public Button moveToCentered(float x, float y) {
        int destinationX = (int) x;
        int destinationY = (int) y;
        this.start.x = destinationX - this.format.x / 2;
        this.start.y = destinationY - this.format.y / 2;
        this.end.x = destinationX + this.format.x / 2;
        this.end.y = destinationY + this.format.y / 2;
        return this;
    }

    public Button scale(float factor) {
        Vector2 center = new Vector2(this.start.x + this.format.x / 2, this.start.y + this.format.y / 2);
        this.start.x = center.x - this.format.x * factor / 2;
        this.start.y = center.y - this.format.y * factor / 2;
        this.end.x = center.x + this.format.x * factor / 2;
        this.end.y = center.y + this.format.y * factor / 2;

        this.format.x *= factor;
        this.format.y *= factor;
        return this;
    }

    public String getButtonText() {
        return this.buttonText;
    }
}