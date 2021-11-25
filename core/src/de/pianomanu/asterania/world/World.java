package de.pianomanu.asterania.world;

import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.world.coordinates.EntityCoordinates;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;
import de.pianomanu.asterania.world.coordinates.WorldSectionCoordinates;
import de.pianomanu.asterania.world.worldsections.WorldSection;

import java.util.ArrayList;
import java.util.List;

public class World {
    private List<WorldSection> sections = new ArrayList<>();
    private Player player = new Player();
    private TileCoordinates entryPoint;
    private final String worldName;

    public World(TileCoordinates entryPoint, String worldName) {
        this.sections.add(new WorldSection(0, 0));
        this.sections.get(0).createTerrain();
        this.entryPoint = entryPoint;
        this.worldName = worldName;
    }

    public void joinWorld(Player player, TileCoordinates entryPoint) {
        this.player = player;
        this.player.setCharacterPos(entryPoint.toEntityCoordinates());
        this.player.updateHitbox();

        this.preGenerateSurroundingWorldSections();
    }

    public void loadWorldSections(List<WorldSection> sections) {
        this.sections = sections;
    }

    public Player getPlayer() {
        return this.player;
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
            this.sections.add(new WorldSection(x, y));
            return true;
        }
        return false;
    }

    public boolean addNewSection(TileCoordinates tileCoordinates) {
        if (findSection(tileCoordinates) == null) {
            this.sections.add(new WorldSection(tileCoordinates.toWorldSectionCoordinates().x, tileCoordinates.toWorldSectionCoordinates().y));
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
        WorldSectionCoordinates center = this.player.getCharacterPos().toWorldSectionCoordinates();
        addNewSection(center.x - 1, center.y - 1);
        addNewSection(center.x - 1, center.y);
        addNewSection(center.x - 1, center.y + 1);
        addNewSection(center.x, center.y - 1);
        addNewSection(center.x, center.y + 1);
        addNewSection(center.x + 1, center.y - 1);
        addNewSection(center.x + 1, center.y);
        addNewSection(center.x + 1, center.y + 1);
    }

    public String getWorldName() {
        return this.worldName;
    }
}
