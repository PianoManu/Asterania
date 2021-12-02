package de.pianomanu.asterania.world.worldsections;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.registry.GameRegistry;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.tile.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class WorldSectionParser {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    //sC = separatingCharacter ... for better readability
    private static final char sC = '|';

    public static String createWSString(World world) {
        StringBuilder builder = new StringBuilder();

        for (WorldSection s :
                world.getSections()) {
            if (s != null)
                builder.append(createWSString(s));
        }
        return builder.toString();
    }

    public static String createWSDecorativeLayerString(World world) {
        StringBuilder builder = new StringBuilder();

        for (WorldSection s :
                world.getSections()) {
            if (s != null)
                builder.append(createWSDecorativeLayerString(s));
        }
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

    private static String createWSDecorativeLayerString(WorldSection section) {
        StringBuilder builder = new StringBuilder();
        //World section position
        builder.append(sC).append(section.sectionPos.x).append(sC).append(section.sectionPos.y);

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

    public static List<WorldSection> getWSfromString(String fileName, List<String> content) {
        List<WorldSection> sections = new ArrayList<>();
        //1st line: version number
        //LOGGER.info("Found version " + content.get(0) + " !");

        //2nd line: empty

        //3rd line: terrain starts
        if (fileName.contains("decorative_layer")) {
            //for (int i = 2; i < content.size(); i++) {
            //sections.add(getWSfromString(removeLineBreaks(content.get(i)), true));
            //find corresponding section
            int worldIndex = -1;
            List<World> worlds = AsteraniaMain.saveFile.getUniverse().getWorlds();
            for (int i = 0; i < worlds.size(); i++) {
                if (fileName.contains(AsteraniaMain.saveFile.getUniverse().getWorlds().get(i).getWorldName())) {
                    worldIndex = i;
                    sections.addAll(worlds.get(i).getSections());
                    break;
                }
            }
            for (WorldSection s : sections) {
                //add content to each section
                //TODO sections correct order?
                //"+ 2" because first two lines of save file contain meta info
                addDecorativeLayerToWS(content.get(worldIndex + 2), s);
            }
        } else {
            for (int i = 0; i < content.size(); i++) {
                sections.add(getWSfromString(removeLineBreaks(content.get(i))));
            }
        }
        return sections;
    }

    private static String removeLineBreaks(String string) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) != '\n')
                sb.append(string.charAt(i));
        }
        return sb.toString();
    }

    private static List<String> createParts(String partList) {
        int sCcounter = 0;
        List<String> parts = new ArrayList<>();
        int start = 0;
        for (int i = 0; i < partList.length(); i++) {
            if (partList.charAt(i) == sC) {
                //separating character found
                if (sCcounter > 0) {
                    parts.add(partList.substring(start, i));
                }
                sCcounter++;
                start = i + 1;
            }
        }
        return parts;
    }

    private static WorldSection getWSfromString(String oneWS) {
        //separatingCharacterCounter ... for better readability
        List<String> parts = createParts(oneWS);
        if (parts.size() != 0)
            return buildWSfromPartList(parts);
        return null;
    }

    private static void addDecorativeLayerToWS(String decorativeLayerWS, WorldSection ws) {
        //one line of the save file: one ws
        //each entry represents:
        //      first two entries: position of ws
        //      next entries: tiles of ws
        List<String> parts = createParts(decorativeLayerWS);
        int xPos = Integer.parseInt(parts.get(0));
        int yPos = Integer.parseInt(parts.get(1));

        List<String> tilesString = parts.subList(2, parts.size());
        List<Tile> tiles = new ArrayList<>();
        for (String t : tilesString) {
            tiles.addAll(Arrays.asList(getTilesFromPart(t, false)));
        }

        Tile[] tileArrayUnprocessed = toTileArray(tiles);
        Tile[][] tileArray = getTilesFromTileArray(tileArrayUnprocessed);
        if (tileArray != null) {
            ws.setDecorationLayerTile(tileArray);
        } else {
            LOGGER.warning("Tile array for WorldSection [" + xPos + "|" + yPos + "] is null!");
        }
    }

    private static WorldSection buildWSfromPartList(List<String> parts) {
        int xPos = Integer.parseInt(parts.get(0));
        int yPos = Integer.parseInt(parts.get(1));

        List<String> tilesString = parts.subList(2, parts.size());

        //TODO change GrasslandPlain
        WorldSection worldSection = new WorldSection(xPos, yPos, WorldSectionSettings.SettingList.GRASSLAND_PLAIN);
        List<Tile> tiles = new ArrayList<>();
        for (String t : tilesString) {
            tiles.addAll(Arrays.asList(getTilesFromPart(t, true)));

        }

        Tile[] tileArrayUnprocessed = toTileArray(tiles);
        Tile[][] tileArray = getTilesFromTileArray(tileArrayUnprocessed);
        if (tileArray != null) {
            worldSection.setTiles(tileArray);
        } else {
            LOGGER.warning("Tile array for WorldSection [" + xPos + "|" + yPos + "] is null!");
        }
        return worldSection;
    }

    private static Tile[][] getTilesFromTileArray(Tile[] unprocessedTiles) {
        if (unprocessedTiles.length != WorldSection.SECTION_SIZE * WorldSection.SECTION_SIZE) {
            LOGGER.warning("Tile array should contain " + WorldSection.SECTION_SIZE * WorldSection.SECTION_SIZE + " tiles, but has " + unprocessedTiles.length + " tiles!");
            LOGGER.warning("This WorldSection is corrupted and must be generated again!");
            return null;
        }
        Tile[][] tiles = new Tile[WorldSection.SECTION_SIZE][WorldSection.SECTION_SIZE];
        for (int x = 0; x < WorldSection.SECTION_SIZE; x++) {
            System.arraycopy(unprocessedTiles, x * 64, tiles[x], 0, WorldSection.SECTION_SIZE);
        }
        return tiles;
    }

    private static Tile[] getTilesFromPart(String part, boolean useWhiteTiles) {
        int asterisk = part.indexOf("*");
        int count = Integer.parseInt(part.substring(0, asterisk));
        String tile = part.substring(asterisk + 1);

        Tile[] tiles = new Tile[count];
        for (int i = 0; i < tiles.length; i++) {
            //useWhiteTiles will fill "null" tiles with Tiles.WHITE instead to prevent crashes
            //decorative layer: no tile == null
            //default layer: no tile == white tile
            if (tile.equals("null") && !useWhiteTiles)
                tiles[i] = null;
            else
                tiles[i] = getTileFromString(tile);
        }
        return tiles;
    }

    private static Tile[] toTileArray(List<Tile> tiles) {
        Tile[] tileArray = new Tile[tiles.size()];
        for (int i = 0; i < tiles.size(); i++) {
            tileArray[i] = tiles.get(i);
        }
        return tileArray;
    }

    private static Tile getTileFromString(String tile) {
        return GameRegistry.getTileFromString(tile);
    }

}
