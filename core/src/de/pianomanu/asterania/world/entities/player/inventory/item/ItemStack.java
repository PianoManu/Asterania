package de.pianomanu.asterania.world.entities.player.inventory.item;

public class ItemStack {
    public static final ItemStack EMPTY = new ItemStack(Items.NONE, Integer.MAX_VALUE);
    private final Item item;
    private int stackCount;

    public ItemStack(Item item) {
        this.item = item;
        this.stackCount = 1;
    }

    public ItemStack(Item item, int stackCount) {
        this.item = item;
        if (stackCount > 0)
            this.stackCount = stackCount;
        else
            this.stackCount = 1;
    }

    public Item getItem() {
        return this.item;
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
                "ItemStack:" + this.item.getName() +
                ", Count=" + this.stackCount +
                '}';
    }

    public String toSaveFileString() {
        return this.stackCount + "*" + this.item.getName();
    }
}
