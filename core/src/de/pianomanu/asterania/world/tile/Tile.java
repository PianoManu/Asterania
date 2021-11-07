package de.pianomanu.asterania.world.tile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Tile {
    protected final String name;
    protected final TileSettings settings;

    protected Tile(String name, TileSettings settings) {
        this.name = name;
        this.settings = settings;
    }

    public boolean isAccessible() {
        return this.settings.isAccessible();
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
}
