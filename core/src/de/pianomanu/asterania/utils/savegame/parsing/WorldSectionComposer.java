package de.pianomanu.asterania.utils.savegame.parsing;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.tile.Tile;
import de.pianomanu.asterania.world.worldsections.WorldSection;

import java.util.logging.Logger;

//WorldSection -> String
public class WorldSectionComposer {
    //sC = separatingCharacter ... for better readability
    static final char sC = '|';
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    public static String createWSString(World world) {
        LOGGER.fine("Creating WorldSection string out of " + world.getSections().size() + " world sections...");
        StringBuilder builder = new StringBuilder();

        for (WorldSection s :
                world.getSections()) {
            if (s != null)
                builder.append(createWSString(s));
        }
        LOGGER.fine("Created WorldSection string out of " + world.getSections().size() + " world sections!");
        return builder.toString();
    }

    private static String createWSString(WorldSection section) {
        StringBuilder builder = new StringBuilder();
        //World section position
        builder.append(sC).append(section.sectionPos.x).append(sC).append(section.sectionPos.y);

        Tile previousTile = section.getTile(0, 0);
        Tile newTile;
        int tileCounter = 0;
        for (int x = 0; x < WorldSection.SECTION_SIZE; x++) {
            for (int y = 0; y < WorldSection.SECTION_SIZE; y++) {
                newTile = section.getTile(x, y);
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

    public static String createWSDecorativeLayerString(World world) {
        LOGGER.fine("Adding decorative layers to " + world.getSections().size() + " world sections...");
        StringBuilder builder = new StringBuilder();

        for (WorldSection s :
                world.getSections()) {
            if (s != null)
                builder.append(createWSDecorativeLayerString(s));
        }
        LOGGER.fine("Added decorative layers to " + world.getSections().size() + " world sections!");
        return builder.toString();
    }

    private static String createWSDecorativeLayerString(WorldSection section) {
        StringBuilder builder = new StringBuilder();
        //World section position
        builder.append(WorldSectionParser.sC).append(section.sectionPos.x).append(WorldSectionParser.sC).append(section.sectionPos.y);

        Tile previousTile = section.getDecorationLayerTile(0, 0);
        Tile newTile;
        int tileCounter = 0;
        for (int x = 0; x < WorldSection.SECTION_SIZE; x++) {
            for (int y = 0; y < WorldSection.SECTION_SIZE; y++) {
                newTile = section.getDecorationLayerTile(x, y);
                if (newTile == previousTile) {
                    tileCounter++;
                } else {
                    if (previousTile != null)
                        builder.append(WorldSectionParser.sC).append(tileCounter).append("*").append(previousTile.getSaveFileString());
                    else
                        builder.append(WorldSectionParser.sC).append(tileCounter).append("*").append("null");
                    previousTile = newTile;
                    tileCounter = 1;
                }
                if (x == WorldSection.SECTION_SIZE - 1 && y == WorldSection.SECTION_SIZE - 1) {
                    if (previousTile != null)
                        builder.append(WorldSectionParser.sC).append(tileCounter).append("*").append(previousTile.getSaveFileString());
                    else
                        builder.append(WorldSectionParser.sC).append(tileCounter).append("*").append("null");
                }
            }
        }
        return builder.append(WorldSectionParser.sC).append("\n").toString();
    }
}
