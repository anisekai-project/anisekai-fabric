package me.anisekai.utils.delegates;

import me.anisekai.interfaces.DelegatedField;

public class DelegatedBoolean implements DelegatedField {

    private boolean value;

    public DelegatedBoolean(boolean defaultValue) {

        this.value = defaultValue;
    }

    @Override
    public void set(int v) {

        this.value = v == 1;
    }

    @Override
    public int get() {

        return this.value ? 1 : 0;
    }

    public boolean isTrue() {

        return this.value;
    }

    public void set(boolean value) {

        this.value = value;
    }

}
