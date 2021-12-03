package de.pianomanu.asterania.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class TextInputBox {
    private Vector2 start;
    private Vector2 end;
    private Color backgroundColor;
    private Color textColor;
    private String boxName;
    private String inputString;

    public TextInputBox(Vector2 start, Vector2 end, String boxName) {
        this.start = start;
        this.end = end;
        this.boxName = boxName;
        this.backgroundColor = Color.WHITE;
        this.textColor = Color.BLACK;
        this.inputString = "";
    }

    public Vector2 getStart() {
        return this.start;
    }

    public void setStart(Vector2 start) {
        this.start = start;
    }

    public Vector2 getEnd() {
        return this.end;
    }

    public void setEnd(Vector2 end) {
        this.end = end;
    }

    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getTextColor() {
        return this.textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public String getBoxName() {
        return this.boxName;
    }

    public void setBoxName(String boxName) {
        this.boxName = boxName;
    }

    public String getInputString() {
        this.inputString = AsteraniaInputProcessor.getTextInput().getInputString();
        return this.inputString;
    }
}
