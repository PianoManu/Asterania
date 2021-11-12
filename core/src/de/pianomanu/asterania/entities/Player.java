package de.pianomanu.asterania.entities;

import com.badlogic.gdx.math.Vector2;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.entities.hitboxes.SimpleHitbox;
import de.pianomanu.asterania.world.coordinates.EntityCoordinates;
import de.pianomanu.asterania.world.direction.Direction;

public class Player {
    private EntityCoordinates characterPos;
    private int stepSize = DisplayConfig.TILE_SIZE / 16 / DisplayConfig.ZOOM;
    private EntityCoordinates characterSize = new EntityCoordinates();
    private boolean isMoving = false;
    private SimpleHitbox playerHitbox;
    private final float timeBetweenAnimations = 0.1f;
    //determines which image to show
    private int movingAnimationCounter = 0;
    private float changeToNextAnimation = 0f;
    private Direction playerFacing = Direction.DOWN;

    public Player() {
        this.characterPos = new EntityCoordinates();
        //this.playerPosOnScreen = new Vector2(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        this.characterSize.x = 3 / 4f;
        this.characterSize.y = 3 / 2f;
        //this.playerHitbox = new SimpleHitbox(new EntityCoordinates(CoordinatesUtils.pixelToEntityCoordinates(0, 0, this.characterPos)), new EntityCoordinates(CoordinatesUtils.pixelToEntityCoordinates(0,0,this.characterPos)).add(CoordinatesUtils.pixelToEntityCoordinates((int) characterSize.x, (int) characterSize.y, this.characterPos))).move(-characterSize.x/2,-characterSize.y/2);
        this.playerHitbox = new SimpleHitbox(new EntityCoordinates(this.characterPos), new EntityCoordinates(this.characterPos).add(this.characterSize)).move(-this.characterSize.x / 2, -this.characterSize.y / 2);
    }

    public EntityCoordinates getCharacterPos() {
        return this.characterPos;
    }

    public EntityCoordinates getFootPos() {
        return new EntityCoordinates(this.getCharacterPos().x, this.getCharacterPos().y - characterSize.y / 2);
    }

    public void setFootPos(EntityCoordinates characterPos) {
        this.setCharacterPos(characterPos.x, characterPos.y - characterSize.y / 2);
    }

    public void setFootPos(float x, float y) {
        this.setCharacterPos(x, y + characterSize.y / 2);
    }

    public void setCharacterPos(EntityCoordinates characterPos) {
        this.characterPos = characterPos;
    }

    public int getStepSize() {
        return this.stepSize;
    }

    public void moveRight(float delta) {
        this.characterPos.x += this.stepSize * delta;
        this.setPlayerFacing(Direction.RIGHT);
    }

    public void moveLeft(float delta) {
        this.characterPos.x -= this.stepSize * delta;
        this.setPlayerFacing(Direction.LEFT);
    }

    public void moveUp(float delta) {
        this.characterPos.y += this.stepSize * delta;
        this.setPlayerFacing(Direction.UP);
    }

    public void moveDown(float delta) {
        this.characterPos.y -= this.stepSize * delta;
        this.setPlayerFacing(Direction.DOWN);
    }

    public void checkForAnimationUpdate(float delta) {
        this.changeToNextAnimation += delta;
        if (this.changeToNextAnimation >= this.timeBetweenAnimations) {
            this.changeToNextAnimation -= this.timeBetweenAnimations;
            this.movingAnimationCounter++;
            if (this.movingAnimationCounter >= 4)
                this.movingAnimationCounter = 0;
        }
    }

    public EntityCoordinates getCharacterSize() {
        return this.characterSize;
    }

    public void setMoving() {
        this.isMoving = true;
        updateHitbox();
    }

    public void setStanding() {
        this.movingAnimationCounter = 0;
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

    public void setCharacterPos(float x, float y) {
        this.characterPos.x = x;
        this.characterPos.y = y;
    }

    public Direction getPlayerFacing() {
        return playerFacing;
    }

    public void setPlayerFacing(Direction playerFacing) {
        this.playerFacing = playerFacing;
    }

    public int getMovingAnimationCounter() {
        return movingAnimationCounter;
    }
}
