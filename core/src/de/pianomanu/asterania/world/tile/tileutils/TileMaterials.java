package de.pianomanu.asterania.world.tile.tileutils;

import de.pianomanu.asterania.registry.GameRegistry;

public class TileMaterials {
    public static final TileMaterial NONE = r(new TileMaterial("none"));
    public static final TileMaterial ORGANIC = r(new TileMaterial("organic"));
    public static final TileMaterial ROCK = r(new TileMaterial("rock"));
    public static final TileMaterial DIRT = r(new TileMaterial("dirt"));
    public static final TileMaterial METAL = r(new TileMaterial("metal"));
    public static final TileMaterial WOOD = r(new TileMaterial("wood"));
    public static final TileMaterial FLUID = r(new TileMaterial("fluid"));

    private static TileMaterial r(TileMaterial tileMaterial) {
        GameRegistry.registerTileMaterial(tileMaterial);
        return tileMaterial;
    }

    public static boolean isSolid(TileMaterial tileMaterial) {
        return tileMaterial != FLUID && tileMaterial != NONE;
    }

    public static boolean canGrowPlant(TileMaterial tileMaterial) {
        return tileMaterial == ORGANIC || tileMaterial == DIRT;
    }
}
