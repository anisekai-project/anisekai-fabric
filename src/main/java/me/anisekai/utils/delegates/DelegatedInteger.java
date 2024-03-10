package me.anisekai.utils.delegates;

import me.anisekai.interfaces.DelegatedField;

public class DelegatedInteger implements DelegatedField {

    private int value;

    public DelegatedInteger(int defaultValue) {

        this.value = defaultValue;
    }

    @Override
    public void set(int v) {

        this.value = v;
    }

    @Override
    public int get() {

        return this.value;
    }

    public void increment(int value) {

        this.value += value;
    }

}
