package de.pianomanu.asterania.world.tile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;

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
     * Overwrite this method (when extending this class) to create interactive
     * tiles. E.g. "activating" a certain tile (via left-click) gives the
     * player an unhealthy amount of gold or something...
     *
     * @param player the interacting player
     * @param world  the world of this tile
     * @return true iff the action was performed successfully
     */
    public boolean performAction(Player player, World world) {
        return false;
    }

    public void runPlacementEvents(World world, Player player, TileCoordinates tileCoordinates) {

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
