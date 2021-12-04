package de.pianomanu.asterania.world;

import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.world.coordinates.EntityCoordinates;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;
import de.pianomanu.asterania.world.coordinates.WorldSectionCoordinates;
import de.pianomanu.asterania.world.worldsections.WorldSection;
import de.pianomanu.asterania.world.worldsections.WorldSectionSettings;

import java.util.ArrayList;
import java.util.List;

public class World {
    private List<WorldSection> sections = new ArrayList<>();
    private final List<Player> players = new ArrayList<>();
    private TileCoordinates entryPoint;
    private final String worldName;
    private final WorldType worldType;

    public World(String worldName, TileCoordinates entryPoint) {
        this.sections.add(new WorldSection(0, 0, WorldSectionSettings.SettingList.GRASSLAND_PLAIN));
        this.sections.get(0).generateTerrain();
        this.entryPoint = entryPoint;
        this.worldName = worldName;
        this.worldType = WorldType.GRASSLAND;
    }

    public World(String worldName) {
        this.sections.add(new WorldSection(0, 0, WorldSectionSettings.SettingList.GRASSLAND_PLAIN));
        this.sections.get(0).generateTerrain();
        this.entryPoint = new TileCoordinates(0, 0);
        this.worldName = worldName;
        this.worldType = WorldType.GRASSLAND;
    }

    public World(String worldName, TileCoordinates entryPoint, WorldType worldType) {
        this.sections.add(new WorldSection(0, 0, worldType.getSettings()));
        this.sections.get(0).generateTerrain();
        this.entryPoint = entryPoint;
        this.worldName = worldName;
        this.worldType = worldType;
    }

    public void joinWorld(Player player, TileCoordinates entryPoint) {
        this.players.add(player);
        player.setCharacterPos(entryPoint.toEntityCoordinates());
        for (Player p : players) {
            p.updateHitbox();
        }
        this.preGenerateSurroundingWorldSections();
    }

    public boolean leaveWorld(Player player) {
        return this.players.remove(player);
    }

    public void loadWorldSections(List<WorldSection> sections) {
        this.sections = sections;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public WorldSection getStartTerrain() {
        return this.findSection(0, 0);
    }

    public WorldSection findSection(int x, int y) {
        for (WorldSection s :
                this.sections) {
            if (s.getSectionPos().x == x && s.getSectionPos().y == y)
                return s;
        }
        return null;
    }

    public WorldSection findSection(EntityCoordinates entityCoordinates) {
        for (WorldSection s :
                this.sections) {
            if (s.getStart().getX() <= entityCoordinates.x && s.getStart().getY() <= entityCoordinates.y && s.getEnd().getX() + 1 >= entityCoordinates.x && s.getEnd().getY() + 1 >= entityCoordinates.y)
                return s;
        }
        return null;
    }

    public List<WorldSection> getSections() {
        return sections;
    }

    public WorldSection findSection(TileCoordinates tileCoordinates) {
        for (WorldSection s :
                this.sections) {
            if (s.getStart().getX() <= tileCoordinates.getX() && s.getStart().getY() <= tileCoordinates.getY() && s.getEnd().getX() >= tileCoordinates.getX() && s.getEnd().getY() >= tileCoordinates.getY())
                return s;
        }
        return null;
    }

    public boolean addNewSection(int x, int y) {
        if (findSection(x, y) == null) {
            this.sections.add(new WorldSection(x, y, this.worldType.getSettings()));
            return true;
        }
        return false;
    }

    public boolean addNewSection(TileCoordinates tileCoordinates) {
        if (findSection(tileCoordinates) == null) {
            this.sections.add(new WorldSection(tileCoordinates.toWorldSectionCoordinates().x, tileCoordinates.toWorldSectionCoordinates().y, this.worldType.getSettings()));
            return true;
        }
        return false;
    }

    public TileCoordinates getEntryPoint() {
        return entryPoint;
    }

    public void setEntryPoint(TileCoordinates entryPoint) {
        this.entryPoint = entryPoint;
    }

    public void preGenerateSurroundingWorldSections() {
        //8 surrounding sections
        for (Player p : players) {
            WorldSectionCoordinates center = p.getCharacterPos().toWorldSectionCoordinates();
            addNewSection(center.x - 1, center.y - 1);
            addNewSection(center.x - 1, center.y);
            addNewSection(center.x - 1, center.y + 1);
            addNewSection(center.x, center.y - 1);
            addNewSection(center.x, center.y + 1);
            addNewSection(center.x + 1, center.y - 1);
            addNewSection(center.x + 1, center.y);
            addNewSection(center.x + 1, center.y + 1);
        }
    }

    public String getWorldName() {
        return this.worldName;
    }
}
