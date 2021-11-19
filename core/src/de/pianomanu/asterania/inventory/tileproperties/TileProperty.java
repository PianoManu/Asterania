package de.pianomanu.asterania.inventory.tileproperties;

import de.pianomanu.asterania.AsteraniaMain;

import java.util.logging.Logger;

public class TileProperty<C extends Comparable<C>> {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    public final String name;
    public final C LOWER_BOUND;
    public final C UPPER_BOUND;
    public final C DEFAULT_VALUE;
    public C value;

    public TileProperty(String name, C lower, C upper, C value) {
        C DEFAULT_VALUE_tmp;
        this.name = name;
        this.LOWER_BOUND = lower;
        this.UPPER_BOUND = upper;
        this.value = value;
        DEFAULT_VALUE_tmp = value;
        if (this.LOWER_BOUND.compareTo(value) > 0) {
            this.value = lower;
            DEFAULT_VALUE_tmp = lower;
        }
        if (this.UPPER_BOUND.compareTo(value) < 0) {
            this.value = upper;
            DEFAULT_VALUE_tmp = upper;
        }
        this.DEFAULT_VALUE = DEFAULT_VALUE_tmp;
    }

    public TileProperty<C> setValue(C newValue) {
        if (this.LOWER_BOUND.compareTo(newValue) <= 0 && this.UPPER_BOUND.compareTo(newValue) >= 0)
            this.value = newValue;
        else {
            LOGGER.warning("Cannot set \"" + newValue + "\" as new value to TileProperty \"" + this.name + "\", as it is not between the TileProperty's bounds!");
            LOGGER.warning("Upper bound: \"" + this.UPPER_BOUND + "\"");
            LOGGER.warning("Lower bound: \"" + this.LOWER_BOUND + "\"");
            LOGGER.warning("The value will not be changed and remains \"" + this.value + "\"");
        }
        return this;
    }

    public TileProperty<C> copyThisProperty() {
        return new TileProperty<>(this.name, this.LOWER_BOUND, this.UPPER_BOUND, this.value);
    }
}
