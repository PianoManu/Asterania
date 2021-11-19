package de.pianomanu.asterania.world.tile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Tile {
    public static final int TOTAL_BREAKING_LEVELS = 4;
    protected final String name;
    protected final TileSettings settings;
    protected float breakingLevel = 0;

    protected Tile(String name, TileSettings settings) {
        this.name = name;
        this.settings = settings;
    }

    public TileSettings getSettings() {
        return settings;
    }

    public float getBreakingLevel() {
        return this.breakingLevel;
    }

    public void setBreakingLevel(float breakingLevel) {
        this.breakingLevel = breakingLevel;
    }

    public void increaseBreakingLevel() {
        this.breakingLevel++;
    }

    /**
     * Causes massive lags and memory leaks because a new texture instance is created everytime this method is called.
     * Use {@link #getTexture(TextureAtlas)} instead (more resource-friendly).
     */
    @Deprecated
    public Texture getTexture() {
        return new Texture("textures\\tiles\\" + this.name + ".png");
    }

    public TextureRegion getTexture(TextureAtlas atlas) {
        return atlas.findRegion(this.name);
    }

    @Override
    public String toString() {
        return "Tile{" +
                "name='" + this.name + '\'' +
                '}';
    }

    public String getSaveFileString() {
        return this.name;
    }
}
