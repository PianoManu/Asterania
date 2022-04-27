package de.pianomanu.asterania.inventory;

import de.pianomanu.asterania.inventory.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private final List<ItemStack> items = new ArrayList<>();
    private int iOStackPointer = 0;
    private float currentWeight = 0;

    public Inventory() {
        //TODO Remove Debug stuff
        //this.addStack(new ItemStack(Items.ROCK_TILE, 1));
        //this.addStack(new ItemStack(Items.GRASS_TILE, 12));
        //this.addStack(new ItemStack(Items.MINE_LADDER, 1));
        //this.addStack(new ItemStack(Items.DIRTY_STONE_TILE, 30));
        //this.addStack(new ItemStack(Items.SOIL_TILE, 30));
        //this.addStack(new ItemStack(Items.TITANIUM_DIBORIDE_TILE, 30));
    }

    public List<ItemStack> getItems() {
        return this.items;
    }

    public void addStack(ItemStack stack) {
        int stackPos = this.getPosition(stack);
        if (stackPos != -1) {
            ItemStack oldStack = this.getItems().get(stackPos);
            oldStack.addStackCount(stack.getStackCount());
        } else {
            addStackToInventory(stack);
        }
        this.currentWeight = calcCurrentWeight();
    }

    public boolean hasStack(ItemStack stack) {
        return this.getPosition(stack) != -1;
    }

    private int getPosition(ItemStack stack) {
        for (int i = 0; i < this.getItems().size(); i++) {
            if (this.getItems().get(i).getItem().getName().equals(stack.getItem().getName()))
                return i;
        }
        return -1;
    }

    public ItemStack getNextIOStack() {
        this.iOStackPointer++;
        if (this.iOStackPointer == this.getItems().size())
            this.iOStackPointer = 0;
        if (this.getCurrentIOStack().equals(ItemStack.EMPTY) && this.getItems().size() > 1) {
            return this.getNextIOStack();
        }
        return this.getCurrentIOStack();
    }

    public ItemStack getPreviousIOStack() {
        this.iOStackPointer--;
        if (this.iOStackPointer < 0)
            this.iOStackPointer = this.getItems().size() - 1;
        if (this.getCurrentIOStack().equals(ItemStack.EMPTY) && this.getItems().size() > 1) {
            return this.getPreviousIOStack();
        }
        return this.getCurrentIOStack();
    }

    public ItemStack getCurrentIOStack() {
        //prevents crash if the inventory is empty when initializing the game
        if (this.getItems().size() == 0)
            this.getItems().add(ItemStack.EMPTY);
        //prevents crash if the stack in the last slot is depleted
        if (this.iOStackPointer >= this.getItems().size())
            this.iOStackPointer = 0;
        if (this.getItems().get(this.iOStackPointer).getStackCount() <= 0)
            this.getItems().set(this.iOStackPointer, ItemStack.EMPTY);
        return this.getItems().get(this.iOStackPointer);
    }

    public int getiOStackPointer() {
        return this.iOStackPointer;
    }

    public ItemStack getStackAtPos(int i) {
        if (i >= this.items.size() || i < 0)
            return ItemStack.EMPTY;
        if (this.items.get(i).getStackCount() <= 0) {
            this.items.set(i, ItemStack.EMPTY);
            updateInventoryLength();
        }
        try {
            return this.items.get(i);
        } catch (IndexOutOfBoundsException e) {
            return ItemStack.EMPTY;
        }
    }

    public boolean addStackToInventory(ItemStack stack) {
        for (int i = 0; i < this.items.size(); i++) {
            if (this.items.get(i).equals(ItemStack.EMPTY)) {
                this.getItems().set(i, stack);
                return true;
            }
        }
        return this.getItems().add(stack);
    }

    public void updateInventoryLength() {
        for (int i = this.items.size() - 1; i > 0; i--) {
            if (!this.items.get(i).equals(ItemStack.EMPTY)) {
                //first non-empty slot: end of inventory found, stop searching for length
                break;
            } else {
                this.items.remove(i);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder("[").append(this.iOStackPointer).append("|");
        for (ItemStack iOS :
                this.getItems()) {
            b.append(iOS.toString()).append("|");
        }
        b.replace(b.length() - 1, b.length(), "]");
        return b.toString();
    }

    public float calcCurrentWeight() {
        float currentWeight = 0f;
        for (ItemStack ios :
                this.getItems()) {
            currentWeight += ios.getStackCount() * ios.getItem().getWeight();
        }
        currentWeight = Math.round(100 * currentWeight) / 100f;
        this.currentWeight = currentWeight;
        return this.currentWeight;
    }

    public String toSaveFileString() {
        StringBuilder builder = new StringBuilder("INVENTORY ");
        for (ItemStack iOS : this.getItems()) {
            builder.append(iOS.toSaveFileString()).append(" ");
        }
        return builder.toString();
    }
}
