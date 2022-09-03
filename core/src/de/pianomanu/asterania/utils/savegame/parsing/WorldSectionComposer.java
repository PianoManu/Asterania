package de.pianomanu.asterania.utils.savegame.parsing;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.tile.Tile;
import de.pianomanu.asterania.world.tile.TileType;
import de.pianomanu.asterania.world.worldsections.WorldSection;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

//WorldSection -> String
public class WorldSectionComposer {
    //sC = separatingCharacter ... for better readability
    static final char S_C = '|';
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    public static List<String> createWorldContentString(World world, TileType tileType) {
        LOGGER.fine("Creating WorldSection string for layer " + tileType.toString() + " out of " + world.getSections().size() + " world sections...");
        List<String> sections = new ArrayList<>();

        for (WorldSection s :
                world.getSections()) {
            if (s != null)
                sections.add(createWorldSectionString(s, tileType));
        }
        LOGGER.fine("Created WorldSection string for layer " + tileType.toString() + " out of " + world.getSections().size() + " world sections!");
        return sections;
    }

    private static String createWorldSectionString(WorldSection section, TileType tileType) {
        StringBuilder builder = new StringBuilder();
        //World section position
        builder.append(section.sectionPos.x).append(S_C).append(section.sectionPos.y).append("\n");

        Tile previousTile = switch (tileType) {
            case DECORATION -> section.getDecorationLayerTileRelativeCoordinates(0, 0);
            case BACKGROUND -> section.getTileRelativeCoordinates(0, 0);
        };
        Tile newTile;
        int tileCounter = 0;
        for (int x = 0; x < WorldSection.SECTION_SIZE; x++) {
            for (int y = 0; y < WorldSection.SECTION_SIZE; y++) {
                newTile = switch (tileType) {
                    case DECORATION -> section.getDecorationLayerTileRelativeCoordinates(x, y);
                    case BACKGROUND -> section.getTileRelativeCoordinates(x, y);
                };
                if (newTile == previousTile) {
                    tileCounter++;
                } else {
                    if (previousTile != null)
                        builder.append(tileCounter).append("*").append(previousTile.getSaveFileString()).append(S_C);
                    else
                        builder.append(tileCounter).append("*").append("null").append(S_C);
                    previousTile = newTile;
                    tileCounter = 1;
                }
                if (x == WorldSection.SECTION_SIZE - 1 && y == WorldSection.SECTION_SIZE - 1) {
                    if (previousTile != null)
                        builder.append(tileCounter).append("*").append(previousTile.getSaveFileString());
                    else
                        builder.append(tileCounter).append("*").append("null");
                }
            }
        }
        return builder.append("\n").toString();
    }
}
