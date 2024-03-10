package me.anisekai.interfaces;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface DelegatedField {

    static DelegatedField of(Supplier<Integer> getter, Consumer<Integer> setter) {

        return new DelegatedField() {
            @Override
            public void set(int v) {

                setter.accept(v);
            }

            @Override
            public int get() {

                return getter.get();
            }
        };
    }

    void set(int v);

    int get();

}
