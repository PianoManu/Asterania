package de.pianomanu.asterania.inventory;

import de.pianomanu.asterania.inventory.objects.InventoryObjectStack;
import de.pianomanu.asterania.inventory.objects.InventoryObjects;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private final List<InventoryObjectStack> inventoryObjects = new ArrayList<>();

    public Inventory() {
        //TODO Remove Debug stuff
        this.addStack(new InventoryObjectStack(InventoryObjects.ROCK_TILE, 3));
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
        /*for (InventoryObjectStack s: this.getInventoryObjects()) {
            if (s.getInventoryObject().getName().equals(stack.getInventoryObject().getName()))
                return true;
        }
        return false;*/
        return this.getPosition(stack) != -1;
    }

    private int getPosition(InventoryObjectStack stack) {
        for (int i = 0; i < this.getInventoryObjects().size(); i++) {
            if (this.getInventoryObjects().get(i).getInventoryObject().getName().equals(stack.getInventoryObject().getName()))
                return i;
        }
        return -1;
    }
}
