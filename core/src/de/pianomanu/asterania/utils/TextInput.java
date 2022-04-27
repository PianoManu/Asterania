package de.pianomanu.asterania.utils;

public class TextInput {
    private int lastPressedKey = 0;
    private int lastReleasedKey = 0;
    private char lastTypedChar = ' ';
    private boolean characterHasBeenTyped = false;
    private StringBuilder builder;

    public TextInput() {
        this.builder = new StringBuilder();
    }

    public String getInputString() {
        if (this.characterHasBeenTyped) {
            this.characterHasBeenTyped = false;
            this.builder.append(this.lastTypedChar);
        }
        return this.builder.toString();
    }

    public void clear() {
        this.builder = new StringBuilder();
        this.characterHasBeenTyped = false;
    }

    public int getLastPressedKey() {
        return this.lastPressedKey;
    }

    public void setLastPressedKey(int lastPressedKey) {
        this.lastPressedKey = lastPressedKey;
    }

    public int getLastReleasedKey() {
        return this.lastReleasedKey;
    }

    public void setLastReleasedKey(int lastReleasedKey) {
        this.lastReleasedKey = lastReleasedKey;
    }

    public char getLastTypedChar() {
        return this.lastTypedChar;
    }

    public void setLastTypedChar(char lastTypedChar) {
        this.lastTypedChar = lastTypedChar;
    }

    public boolean hasCharacterBeenTyped() {
        return this.characterHasBeenTyped;
    }

    public void setCharacterHasBeenTyped(boolean characterHasBeenTyped) {
        this.characterHasBeenTyped = characterHasBeenTyped;
    }

    public boolean isValidCharacter(char character) {
        if (Character.isLetterOrDigit(character) || Character.isWhitespace(character))
            return true;

        if (character == '\b') { //BACKSPACE
            if (this.builder.length() >= 1)
                this.builder.delete(this.builder.length() - 1, this.builder.length());
            return false;
        }
        return isSpecialAllowedCharacter(character);
    }

    private boolean isSpecialAllowedCharacter(Character character) {
        switch (character) {
            case '-':
            case '_':
            case '.':
            case ',':
            case '#':
            case '+':
            case 'ß': //TODO fix font for sharp s symbol or remove
            case '§': //TODO fix font for paragraph symbol or remove
            case '$':
            case '%':
            case '&':
            case '(':
            case ')':
            case '=':
            case '°': //TODO fix font for degree symbol or remove
            case '^':
            case '~':
                //TODO fix special characters
            case 'ä':
            case 'ö':
            case 'ü':
            case 'á':
            case 'à':
            case 'â':
            case 'é':
            case 'è':
            case 'ê':
            case 'í':
            case 'ì':
            case 'î':
            case 'ó':
            case 'ò':
            case 'ô':
            case 'ú':
            case 'ù':
            case 'û':
            case 'ý':
                return true;
        }
        return false;
    }
}
