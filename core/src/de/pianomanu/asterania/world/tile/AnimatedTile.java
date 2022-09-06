package de.pianomanu.asterania.world.tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimatedTile extends BasicTile {
    public static final int TOTAL_ANIMATIONS = 4;
    private int animationCounter = 0;
    private float changeToNextAnimation = 0f;
    private float timeBetweenAnimations = 0.2f;

    public AnimatedTile(String name, TileMaterial tileMaterial, TileSettings settings) {
        super(name, tileMaterial, settings);
    }

    public int getAnimationCounter() {
        return this.animationCounter;
    }

    public void increaseAnimationCounter() {
        this.animationCounter++;
        if (this.animationCounter == TOTAL_ANIMATIONS)
            this.animationCounter = 0;
    }

    public void checkForAnimationUpdate() {
        this.changeToNextAnimation += Gdx.graphics.getDeltaTime();
        if (this.changeToNextAnimation >= this.timeBetweenAnimations) {
            this.changeToNextAnimation -= this.timeBetweenAnimations;
            this.increaseAnimationCounter();
        }
    }

    public TextureRegion getTexture(TextureAtlas atlas) {
        return atlas.findRegion(this.name + "" + (this.animationCounter + 1));
    }
}
