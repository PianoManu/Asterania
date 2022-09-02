package de.pianomanu.asterania.world.worldsections;

import de.pianomanu.asterania.world.coordinates.TileCoordinates;
import de.pianomanu.asterania.world.tile.Tiles;

public class WorldSectionGeneration {
    public static void addDecorations(WorldSection section) {
        addPlantsAndWood(section);
    }

    private static void addPlantsAndWood(WorldSection section) {
        TileCoordinates start = section.getStart();
        TileCoordinates end = section.getEnd();
        for (int x = start.getX(); x < end.getX(); x++) {
            for (int y = start.getY(); y < end.getY(); y++) {
                if (Math.random() < 0.1)
                    if (section.getTileAbsoluteCoordinates(x, y).equals(Tiles.GRASS) && section.getDecorationLayerTileAbsoluteCoordinates(x, y) == null)
                        section.setDecorationLayerTileAbsoluteCoordinates(x, y, Tiles.FLOWER_YELLOW);
                if (Math.random() < 0.01) {
                    if (section.getTileAbsoluteCoordinates(x, y).equals(Tiles.GRASS) && section.getDecorationLayerTileAbsoluteCoordinates(x, y) == null)
                        section.setDecorationLayerTileAbsoluteCoordinates(x, y, Tiles.TWIG);
                }
                if (Math.random() < 0.04) {
                    if (section.getTileAbsoluteCoordinates(x, y).equals(Tiles.ROCK) && section.getDecorationLayerTileAbsoluteCoordinates(x, y) == null)
                        section.setDecorationLayerTileAbsoluteCoordinates(x, y, Tiles.STONE);
                }
            }
        }
        section.getDecorationLayerTiles();
    }
}
