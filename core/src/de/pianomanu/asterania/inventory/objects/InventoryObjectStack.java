package de.pianomanu.asterania.inventory.objects;

public class InventoryObjectStack {
    public static final InventoryObjectStack EMPTY = new InventoryObjectStack(InventoryObjects.NONE, Integer.MAX_VALUE);
    private final InventoryObject inventoryObject;
    private int stackCount;

    public InventoryObjectStack(InventoryObject inventoryObject) {
        this.inventoryObject = inventoryObject;
        this.stackCount = 1;
    }

    public InventoryObjectStack(InventoryObject inventoryObject, int stackCount) {
        this.inventoryObject = inventoryObject;
        if (stackCount > 0)
            this.stackCount = stackCount;
        else
            this.stackCount = 1;
    }

    public InventoryObject getInventoryObject() {
        return this.inventoryObject;
    }

    public int getStackCount() {
        return this.stackCount;
    }

    public void setStackCount(int stackCount) {
        this.stackCount = stackCount;
    }

    public void increment() {
        this.stackCount++;
    }

    public void decrement() {
        this.stackCount--;
    }

    public void addStackCount(int summand) {
        this.stackCount += summand;
    }

    @Override
    public String toString() {
        return "{" +
                "IO:" + this.inventoryObject.getName() +
                ", Count=" + this.stackCount +
                '}';
    }
}
