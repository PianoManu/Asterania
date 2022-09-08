package de.pianomanu.asterania.world.tile.tileutils;

public class PlacementEventInfos {
    public static final PlacementEventInfo NONE = new PlacementEventInfo(true, "");
    public static final PlacementEventInfo TILE_CAN_BE_PLACED = new PlacementEventInfo(true, "This tile can be placed here!");
    public static final PlacementEventInfo TILE_CANNOT_BE_PLACED = new PlacementEventInfo(false, "This tile cannot be placed here!");
    public static final PlacementEventInfo PLANT_CAN_GROW = new PlacementEventInfo(true, "This plant can grow here!");
    public static final PlacementEventInfo PLANT_CANNOT_GROW = new PlacementEventInfo(false, "This plant cannot grow here!");
}
