package de.pianomanu.asterania.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.entities.hitboxes.SimpleHitbox;
import de.pianomanu.asterania.utils.CoordinatesUtils;
import de.pianomanu.asterania.world.EntityCoordinates;

public class Player {
    private EntityCoordinates characterPos;
    private EntityCoordinates footPos;
    private Vector2 playerPosOnScreen;
    private int stepSize = DisplayConfig.TILE_SIZE / 32;
    private EntityCoordinates characterSize = new EntityCoordinates();
    private boolean isMoving = false;
    private SimpleHitbox playerHitbox;

    public Player() {
        this.characterPos = new EntityCoordinates();
        this.playerPosOnScreen = new Vector2(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        this.characterSize.x = 3 / 4f;
        this.characterSize.y = 3 / 2f;
        //this.playerHitbox = new SimpleHitbox(new EntityCoordinates(CoordinatesUtils.pixelToEntityCoordinates(0, 0, this.characterPos)), new EntityCoordinates(CoordinatesUtils.pixelToEntityCoordinates(0,0,this.characterPos)).add(CoordinatesUtils.pixelToEntityCoordinates((int) characterSize.x, (int) characterSize.y, this.characterPos))).move(-characterSize.x/2,-characterSize.y/2);
        this.playerHitbox = new SimpleHitbox(new EntityCoordinates(this.characterPos), new EntityCoordinates(this.characterPos).add(this.characterSize)).move(-this.characterSize.x / 2, -this.characterSize.y / 2);
    }

    public EntityCoordinates getCharacterPos() {
        return this.characterPos;
    }

    public EntityCoordinates getFootPos() {
        float yOffset = CoordinatesUtils.pixelToEntityCoordinates(0, (int) (this.playerPosOnScreen.y - characterSize.y / 2), this.getCharacterPos()).y;
        return new EntityCoordinates(this.getCharacterPos().x, yOffset);
    }

    public void setCharacterPos(EntityCoordinates characterPos) {
        this.characterPos = characterPos;
    }

    public int getStepSize() {
        return this.stepSize;
    }

    public void moveRight(float delta) {
        this.characterPos.x += this.stepSize * delta;
    }

    public void moveLeft(float delta) {
        this.characterPos.x -= this.stepSize * delta;
    }

    public void moveUp(float delta) {
        this.characterPos.y += this.stepSize * delta;
    }

    public void moveDown(float delta) {
        this.characterPos.y -= this.stepSize * delta;
    }

    public EntityCoordinates getCharacterSize() {
        return this.characterSize;
    }

    public void setMoving() {
        this.isMoving = true;
        updateHitbox();
    }

    public void setStanding() {
        this.isMoving = false;
    }

    public boolean isMoving() {
        return this.isMoving;
    }

    public SimpleHitbox getPlayerHitbox() {
        return this.playerHitbox;
    }

    public Vector2 getCharacterSizeInPixels() {
        return new Vector2(this.characterSize.x * DisplayConfig.TILE_SIZE, this.characterSize.y * DisplayConfig.TILE_SIZE);
    }

    public void updateHitbox() {
        this.playerHitbox = new SimpleHitbox(new EntityCoordinates(this.characterPos), new EntityCoordinates(this.characterPos).add(this.characterSize)).move(-this.characterSize.x / 2, -this.characterSize.y / 2);
    }
}
