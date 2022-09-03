package de.pianomanu.asterania.utils.savegame.parsing;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.registry.GameRegistry;
import de.pianomanu.asterania.utils.StringUtils;
import de.pianomanu.asterania.world.tile.LayerType;
import de.pianomanu.asterania.world.tile.Tile;
import de.pianomanu.asterania.world.worldsections.WorldSection;
import de.pianomanu.asterania.world.worldsections.WorldSectionSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

//String -> WorldSection
public class WorldSectionParser {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    //sC = separatingCharacter ... for better readability
    private static final char sC = '|';

    private static List<String> createParts(String partList) {
        List<String> parts = new ArrayList<>();
        int start = 0;
        for (int i = 0; i < partList.length(); i++) {
            if (partList.charAt(i) == sC) {
                //separating character found
                parts.add(partList.substring(start, i));
                start = i + 1;
            }
            if (i == partList.length() - 1) {
                parts.add(partList.substring(start));
            }
        }
        return parts;
    }

    public static WorldSection getWSfromString(String backgroundLayer, String decorationLayer) {
        //separatingCharacterCounter ... for better readability
        int backgroundLayerLines = StringUtils.countSymbolOccurances(backgroundLayer, '\n');
        int decorationLayerLines = StringUtils.countSymbolOccurances(decorationLayer, '\n');
        if (backgroundLayerLines == decorationLayerLines) {
            String[] backgroundLayerStrings = backgroundLayer.split("\n");
            String[] decorationLayerStrings = decorationLayer.split("\n");
            List<String> backgroundLayerParts = createParts(backgroundLayerStrings[1]);
            List<String> decorationLayerParts = createParts(decorationLayerStrings[1]);
            if (backgroundLayerParts.size() != 0 && decorationLayerParts.size() != 0 && backgroundLayerStrings[0].equals(decorationLayerStrings[0]))
                return buildWSfromPartList(backgroundLayerStrings[0], backgroundLayerParts, decorationLayerParts);
        }
        return null;
    }

    private static WorldSection buildWSfromPartList(String positionLine, List<String> backgroundLayerParts, List<String> decorationLayerParts) {
        System.out.println(positionLine);
        int xPos = Integer.parseInt(createParts(positionLine).get(0));
        int yPos = Integer.parseInt(createParts(positionLine).get(1));

        //TODO change GrasslandPlain
        WorldSection worldSection = new WorldSection(xPos, yPos, WorldSectionSettings.SettingList.GRASSLAND_PLAIN);
        List<Tile> backgroundTiles = getParts(backgroundLayerParts, true);
        List<Tile> decorationTiles = getParts(decorationLayerParts, false);
        if (!addTilesToWorldSection(worldSection, backgroundTiles, LayerType.BACKGROUND))
            LOGGER.warning("Background tile array for WorldSection [" + xPos + "|" + yPos + "] is null!");
        if (!addTilesToWorldSection(worldSection, decorationTiles, LayerType.DECORATION))
            LOGGER.warning("Decoration tile array for WorldSection [" + xPos + "|" + yPos + "] is null!");

        return worldSection;
    }

    private static List<Tile> getParts(List<String> layerParts, boolean useWhiteTiles) {
        List<Tile> tiles = new ArrayList<>();
        for (String t : layerParts) {
            tiles.addAll(Arrays.asList(getTilesFromPart(t, useWhiteTiles)));
        }
        return tiles;
    }

    private static boolean addTilesToWorldSection(WorldSection worldSection, List<Tile> tiles, LayerType layerType) {
        Tile[] tileArrayUnprocessed = toTileArray(tiles);
        Tile[][] tileArray = getTilesFromTileArray(tileArrayUnprocessed);
        if (tileArray != null) {
            if (layerType == LayerType.BACKGROUND)
                worldSection.setTiles(tileArray);
            else
                worldSection.setDecorationLayerTiles(tileArray);
            return true;
        }
        return false;
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
            //background layer: no tile == white tile
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
