package de.pianomanu.asterania.entities;

import com.badlogic.gdx.math.Vector2;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.world.EntityCoordinates;

public class Player {
    private EntityCoordinates characterPos;
    private int stepSize = DisplayConfig.TILE_SIZE / 32;
    private Vector2 characterSize = new Vector2();
    private boolean isMoving = false;

    public Player() {
        characterPos = new EntityCoordinates();
        characterSize.x = DisplayConfig.TILE_SIZE * 3 / 4f;
        characterSize.y = DisplayConfig.TILE_SIZE * 3 / 2f;
        System.out.println(characterSize);
    }

    public EntityCoordinates getCharacterPos() {
        return characterPos;
    }

    public void setCharacterPos(EntityCoordinates characterPos) {
        this.characterPos = characterPos;
    }

    public int getStepSize() {
        return stepSize;
    }

    public void moveRight(float delta) {
        characterPos.x += stepSize * delta;
    }

    public void moveLeft(float delta) {
        characterPos.x -= stepSize * delta;
    }

    public void moveUp(float delta) {
        characterPos.y += stepSize * delta;
    }

    public void moveDown(float delta) {
        characterPos.y -= stepSize * delta;
    }

    public Vector2 getCharacterSize() {
        return characterSize;
    }

    public void setMoving() {
        isMoving = true;
    }

    public void setStanding() {
        isMoving = false;
    }

    public boolean isMoving() {
        return isMoving;
    }
}
