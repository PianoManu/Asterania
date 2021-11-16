package de.pianomanu.asterania.inventory;

import de.pianomanu.asterania.inventory.objects.InventoryObjectStack;
import de.pianomanu.asterania.inventory.objects.InventoryObjects;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private final List<InventoryObjectStack> inventoryObjects = new ArrayList<>();
    private int iOStackPointer = 0;

    public Inventory() {
        //TODO Remove Debug stuff
        this.addStack(new InventoryObjectStack(InventoryObjects.ROCK_TILE, 3));
        this.addStack(new InventoryObjectStack(InventoryObjects.GRASS_TILE, 30));
    }

    public List<InventoryObjectStack> getInventoryObjects() {
        return this.inventoryObjects;
    }

    public void addStack(InventoryObjectStack stack) {
        int stackPos = this.getPosition(stack);
        if (stackPos != -1) {
            InventoryObjectStack oldStack = this.getInventoryObjects().get(stackPos);
            oldStack.addStackCount(stack.getStackCount());
        } else {
            this.getInventoryObjects().add(stack);
        }
    }

    public boolean hasStack(InventoryObjectStack stack) {
        return this.getPosition(stack) != -1;
    }

    private int getPosition(InventoryObjectStack stack) {
        for (int i = 0; i < this.getInventoryObjects().size(); i++) {
            if (this.getInventoryObjects().get(i).getInventoryObject().getName().equals(stack.getInventoryObject().getName()))
                return i;
        }
        return -1;
    }

    public InventoryObjectStack getNextIOStack() {
        this.iOStackPointer++;
        if (this.iOStackPointer == this.getInventoryObjects().size())
            this.iOStackPointer = 0;
        return this.getCurrentIOStack();
    }

    public InventoryObjectStack getPreviousIOStack() {
        this.iOStackPointer--;
        if (this.iOStackPointer < 0)
            this.iOStackPointer = this.getInventoryObjects().size() - 1;
        return this.getCurrentIOStack();
    }

    public InventoryObjectStack getCurrentIOStack() {
        return this.getInventoryObjects().get(this.iOStackPointer);
    }
}
