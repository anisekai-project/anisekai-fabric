package me.anisekai.utils;

import me.anisekai.interfaces.DelegatedField;
import net.minecraft.screen.PropertyDelegate;

import java.util.HashMap;
import java.util.Map;

public class ReadWritePropertyDelegate implements PropertyDelegate {

    private final Map<Integer, DelegatedField> delegateMap = new HashMap<>();

    public ReadWritePropertyDelegate(DelegatedField... delegatedFields) {

        for (int i = 0; i < delegatedFields.length; i++) {
            this.delegateMap.put(i, delegatedFields[i]);
        }
    }

    @Override
    public int get(int index) {

        return this.delegateMap.get(index).get();
    }

    @Override
    public void set(int index, int value) {

        this.delegateMap.get(index).set(value);
    }

    @Override
    public int size() {

        return this.delegateMap.size();
    }

}
