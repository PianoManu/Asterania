package de.pianomanu.asterania.world.tile;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.render.Atlases;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;

public abstract class Tile {
    public static final int TOTAL_BREAKING_LEVELS = 4;
    protected final String name;
    protected final TileSettings settings;
    protected float breakingLevel = 0;
    protected int numberOfDifferentTextures = 1;
    protected final LayerType layerType;
    protected final TileMaterial tileMaterial;

    protected Tile(String name, TileMaterial tileMaterial, TileSettings settings) {
        this.name = name;
        this.tileMaterial = tileMaterial;
        this.settings = settings;
        this.layerType = LayerType.BACKGROUND;
    }

    protected Tile(String name, TileMaterial tileMaterial, TileSettings settings, LayerType layerType) {
        this.name = name;
        this.tileMaterial = tileMaterial;
        this.settings = settings;
        this.layerType = layerType;
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

    public int getNumberOfDifferentTextures() {
        return this.numberOfDifferentTextures;
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

    public boolean runPrePlacementEvents(World world, Player player, TileCoordinates tileCoordinates) {
        return true;
    }

    public boolean runPostPlacementEvents(World world, Player player, TileCoordinates tileCoordinates) {
        return true;
    }

    public boolean runPreBreakingEvents(World world, Player player, TileCoordinates tileCoordinates) {
        return true;
    }

    public boolean runPostBreakingEvents(World world, Player player, TileCoordinates tileCoordinates) {
        return true;
    }

    public LayerType getTileType() {
        return this.layerType;
    }

    public TextureRegion getTexture(TextureAtlas atlas) {
        //Some tiles have multiple textures ==> default texture is texture 1
        String multipleTextureOffset = this.numberOfDifferentTextures > 1 ? "1" : "";
        return atlas.findRegion(this.name + multipleTextureOffset);
    }

    public TextureRegion getTexture() {
        //Some tiles have multiple textures ==> default texture is texture 1
        String multipleTextureOffset = this.numberOfDifferentTextures > 1 ? "1" : "";
        if (this.layerType == LayerType.BACKGROUND)
            return AsteraniaMain.INSTANCE.getAssetManager().get(Atlases.TILE_ATLAS_LOCATION, TextureAtlas.class).findRegion(this.name + multipleTextureOffset);
        if (this.layerType == LayerType.DECORATION)
            return AsteraniaMain.INSTANCE.getAssetManager().get(Atlases.DECORATION_ATLAS_LOCATION, TextureAtlas.class).findRegion(this.name + multipleTextureOffset);
        return AsteraniaMain.INSTANCE.getAssetManager().get(Atlases.DECORATION_ATLAS_LOCATION, TextureAtlas.class).findRegion(Tiles.WHITE.name);
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
