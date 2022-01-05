package de.pianomanu.asterania.entities;

import com.badlogic.gdx.math.Vector2;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.entities.hitboxes.SimpleHitbox;
import de.pianomanu.asterania.inventory.Inventory;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.coordinates.EntityCoordinates;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;
import de.pianomanu.asterania.world.direction.Direction;

public class Player {
    public static final EntityCoordinates CHARACTER_SIZE = new EntityCoordinates(3 / 4f, 3 / 2f);

    private EntityCoordinates characterPos;
    private int stepSize = DisplayConfig.TILE_SIZE / 16 / DisplayConfig.ZOOM;
    private boolean isMoving = false;
    private SimpleHitbox playerHitbox;
    private final float timeBetweenAnimations = 0.1f;
    //determines which image to show
    private int movingAnimationCounter = 0;
    private float changeToNextAnimation = 0f;
    private Direction playerFacing = Direction.DOWN;
    private boolean isBreakingTile = false;
    private float currentBreakingPercentage = 0;
    private boolean canChangeBackgroundLayer = false;
    private final Inventory playerInventory = new Inventory();
    private float maxWeight = 20f;
    private World currentWorld = null;

    public Player() {
        this.characterPos = new EntityCoordinates();
        this.playerHitbox = new SimpleHitbox(new EntityCoordinates(this.characterPos), new EntityCoordinates(this.characterPos).add(CHARACTER_SIZE)).move(-CHARACTER_SIZE.x / 2, -CHARACTER_SIZE.y);
    }

    public EntityCoordinates getPos() {
        return this.characterPos;
    }

    public void setPos(EntityCoordinates characterPos) {
        this.characterPos = characterPos;
    }

    public void setPos(float x, float y) {
        this.setPos(new EntityCoordinates(x, y));
    }

    public int getStepSize() {
        return this.stepSize;
    }

    public World getCurrentWorld() {
        return this.currentWorld;
    }

    public void changeCurrentWorld(World currentWorld) {
        //first: leave previous world (if existing)
        if (this.currentWorld != null)
            this.currentWorld.leaveWorld(this);

        //then: join new world
        currentWorld.joinWorld(this, currentWorld.getEntryPoint());
        this.currentWorld = currentWorld;
    }

    public void changeCurrentWorld(World destinyWorld, TileCoordinates playerPos) {
        //first: leave previous world (if existing)
        if (this.currentWorld != null)
            this.currentWorld.leaveWorld(this);

        //then: join new world
        destinyWorld.joinWorld(this, playerPos);
        this.currentWorld = destinyWorld;
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
        return CHARACTER_SIZE;
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
        return new Vector2(CHARACTER_SIZE.x * DisplayConfig.TILE_SIZE, CHARACTER_SIZE.y * DisplayConfig.TILE_SIZE);
    }

    public void updateHitbox() {
        this.playerHitbox = new SimpleHitbox(new EntityCoordinates(this.characterPos), new EntityCoordinates(this.characterPos).add(CHARACTER_SIZE)).move(-CHARACTER_SIZE.x / 2, 0);
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

    public boolean isBreakingTile() {
        return this.isBreakingTile;
    }

    public void setBreakingTile(boolean breakingTile) {
        this.isBreakingTile = breakingTile;
    }

    public float getCurrentBreakingPercentage() {
        return this.currentBreakingPercentage;
    }

    public void setCurrentBreakingPercentage(float currentBreakingPercentage) {
        this.currentBreakingPercentage = currentBreakingPercentage;
    }

    public float getMaxWeight() {
        return this.maxWeight;
    }

    public void setMaxWeight(float maxWeight) {
        this.maxWeight = maxWeight;
    }

    public void setPlayerHoldNextIOStack() {
        this.getPlayerInventory().getNextIOStack();
    }

    public void setPlayerHoldPreviousIOStack() {
        this.getPlayerInventory().getPreviousIOStack();
    }

    public Inventory getPlayerInventory() {
        return this.playerInventory;
    }

    public boolean canChangeBackgroundLayer() {
        return this.canChangeBackgroundLayer;
    }

    public void setCanChangeBackgroundLayer(boolean canChangeBackgroundLayer) {
        this.canChangeBackgroundLayer = canChangeBackgroundLayer;
    }
}
