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
        //this.addStack(new InventoryObjectStack(InventoryObjects.GRASS_TILE, 30));
        this.addStack(new InventoryObjectStack(InventoryObjects.DIRTY_STONE_TILE, 30));
        this.addStack(new InventoryObjectStack(InventoryObjects.SOIL_TILE, 30));
        this.addStack(new InventoryObjectStack(InventoryObjects.TITANIUM_DIBORIDE_TILE, 30));
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
            addStackToInventory(stack);
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
        if (this.getCurrentIOStack().equals(InventoryObjectStack.EMPTY)) {
            return this.getNextIOStack();
        }
        return this.getCurrentIOStack();
    }

    public InventoryObjectStack getPreviousIOStack() {
        this.iOStackPointer--;
        if (this.iOStackPointer < 0)
            this.iOStackPointer = this.getInventoryObjects().size() - 1;
        if (this.getCurrentIOStack().equals(InventoryObjectStack.EMPTY)) {
            return this.getPreviousIOStack();
        }
        return this.getCurrentIOStack();
    }

    public InventoryObjectStack getCurrentIOStack() {
        //prevents crash if the stack in the last slot is depleted
        if (this.iOStackPointer >= this.getInventoryObjects().size())
            this.iOStackPointer = 0;
        if (this.getInventoryObjects().get(this.iOStackPointer).getStackCount() <= 0)
            this.getInventoryObjects().set(this.iOStackPointer, InventoryObjectStack.EMPTY);
        return this.getInventoryObjects().get(this.iOStackPointer);
    }

    public int getiOStackPointer() {
        return this.iOStackPointer;
    }

    public InventoryObjectStack getStackAtPos(int i) {
        if (i >= this.inventoryObjects.size() || i < 0)
            return InventoryObjectStack.EMPTY;
        if (this.inventoryObjects.get(i).getStackCount() <= 0) {
            this.inventoryObjects.set(i, InventoryObjectStack.EMPTY);
            updateInventoryLength();
        }
        try {
            return this.inventoryObjects.get(i);
        } catch (IndexOutOfBoundsException e) {
            return InventoryObjectStack.EMPTY;
        }
    }

    public boolean addStackToInventory(InventoryObjectStack stack) {
        for (int i = 0; i < this.inventoryObjects.size(); i++) {
            if (this.inventoryObjects.get(i).equals(InventoryObjectStack.EMPTY)) {
                this.getInventoryObjects().set(i, stack);
                return true;
            }
        }
        return this.getInventoryObjects().add(stack);
    }

    public void updateInventoryLength() {
        for (int i = this.inventoryObjects.size() - 1; i > 0; i--) {
            if (!this.inventoryObjects.get(i).equals(InventoryObjectStack.EMPTY)) {
                //first non-empty slot: end of inventory found, stop searching for length
                break;
            } else {
                this.inventoryObjects.remove(i);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder("[").append(this.iOStackPointer).append("|");
        for (InventoryObjectStack iOS :
                this.getInventoryObjects()) {
            b.append(iOS.toString()).append("|");
        }
        b.replace(b.length() - 1, b.length(), "]");
        return b.toString();
    }
}
