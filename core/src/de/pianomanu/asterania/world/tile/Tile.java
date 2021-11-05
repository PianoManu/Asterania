package de.pianomanu.asterania.world.tile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Tile {
    protected final String name;

    protected Tile(String name) {
        this.name = name;
    }

    public Texture getTexture() {
        return new Texture("textures\\tiles\\" + this.name + ".png");
    }

    public TextureRegion getTexture(TextureAtlas atlas) {
        return atlas.findRegion(this.name);
    }
}
