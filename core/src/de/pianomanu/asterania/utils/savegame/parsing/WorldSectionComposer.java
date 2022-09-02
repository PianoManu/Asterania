package de.pianomanu.asterania.utils.savegame.parsing;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.tile.Tile;
import de.pianomanu.asterania.world.worldsections.WorldSection;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

//WorldSection -> String
public class WorldSectionComposer {
    //sC = separatingCharacter ... for better readability
    static final char sC = '|';
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    public static List<String> createWSString(World world) {
        LOGGER.fine("Creating WorldSection string out of " + world.getSections().size() + " world sections...");
        List<String> sections = new ArrayList<>();

        for (WorldSection s :
                world.getSections()) {
            if (s != null)
                sections.add(createWSString(s));
        }
        LOGGER.fine("Created WorldSection string out of " + world.getSections().size() + " world sections!");
        return sections;
    }

    private static String createWSString(WorldSection section) {
        StringBuilder builder = new StringBuilder();
        //World section position
        builder.append(sC).append(section.sectionPos.x).append(sC).append(section.sectionPos.y);

        Tile previousTile = section.getTileRelativeCoordinates(0, 0);
        Tile newTile;
        int tileCounter = 0;
        for (int x = 0; x < WorldSection.SECTION_SIZE; x++) {
            for (int y = 0; y < WorldSection.SECTION_SIZE; y++) {
                newTile = section.getTileRelativeCoordinates(x, y);
                if (newTile == previousTile) {
                    tileCounter++;
                } else {
                    builder.append(sC).append(tileCounter).append("*").append(previousTile.getSaveFileString());
                    previousTile = newTile;
                    tileCounter = 1;
                }
                if (x == WorldSection.SECTION_SIZE - 1 && y == WorldSection.SECTION_SIZE - 1) {
                    builder.append(sC).append(tileCounter).append("*").append(previousTile.getSaveFileString());
                }
            }
        }
        return builder.append(sC).append("\n").toString();
    }

    public static List<String> createWSDecorativeLayerString(World world) {
        LOGGER.fine("Adding decorative layers to " + world.getSections().size() + " world sections...");
        List<String> sections = new ArrayList<>();

        for (WorldSection s :
                world.getSections()) {
            if (s != null)
                sections.add(createWSDecorativeLayerString(s));
        }
        LOGGER.fine("Added decorative layers to " + world.getSections().size() + " world sections!");
        return sections;
    }

    private static String createWSDecorativeLayerString(WorldSection section) {
        StringBuilder builder = new StringBuilder();
        //World section position
        builder.append(sC).append(section.sectionPos.x).append(sC).append(section.sectionPos.y);

        Tile previousTile = section.getDecorationLayerTileRelativeCoordinates(0, 0);
        Tile newTile;
        int tileCounter = 0;
        for (int x = 0; x < WorldSection.SECTION_SIZE; x++) {
            for (int y = 0; y < WorldSection.SECTION_SIZE; y++) {
                newTile = section.getDecorationLayerTileRelativeCoordinates(x, y);
                if (newTile == previousTile) {
                    tileCounter++;
                } else {
                    if (previousTile != null)
                        builder.append(sC).append(tileCounter).append("*").append(previousTile.getSaveFileString());
                    else
                        builder.append(sC).append(tileCounter).append("*").append("null");
                    previousTile = newTile;
                    tileCounter = 1;
                }
                if (x == WorldSection.SECTION_SIZE - 1 && y == WorldSection.SECTION_SIZE - 1) {
                    if (previousTile != null)
                        builder.append(sC).append(tileCounter).append("*").append(previousTile.getSaveFileString());
                    else
                        builder.append(sC).append(tileCounter).append("*").append("null");
                }
            }
        }
        return builder.append(sC).append("\n").toString();
    }
}
