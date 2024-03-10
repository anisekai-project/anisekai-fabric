package me.anisekai.utils.delegates;

import me.anisekai.interfaces.DelegatedField;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class DelegatedIdentifier implements DelegatedField {

    public static DelegatedIdentifier of(Map<Identifier, ?> map) {

        return of(map, null);
    }

    public static DelegatedIdentifier of(Map<Identifier, ?> map, Identifier defaultValue) {

        List<Identifier> identifiers = new ArrayList<>(map.keySet());

        return new DelegatedIdentifier(
                identifiers::get,
                identifiers::indexOf,
                defaultValue
        );
    }


    private final Function<Integer, Identifier> onInput;
    private final Function<Identifier, Integer> onOutput;

    private Identifier value;

    public DelegatedIdentifier(Function<Integer, Identifier> onInput, Function<Identifier, Integer> onOutput, Identifier value) {

        this.onInput  = onInput;
        this.onOutput = onOutput;
        this.value    = value;
    }

    @Override
    public void set(int v) {

        if (v == -1) {
            this.value = null;
        } else {
            this.value = this.onInput.apply(v);
        }
    }

    @Override
    public int get() {

        if (this.value == null) {
            return -1;
        }
        return this.onOutput.apply(this.value);
    }

    public boolean isSet() {

        return this.value != null;
    }

    @Nullable
    public Identifier getIdentifier() {

        return this.value;
    }

    public void set(@Nullable Identifier identifier) {

        this.value = identifier;
    }

    public void setFrom(NbtCompound nbt, String key) {

        if (!nbt.contains(key) || nbt.getString(key).isEmpty()) {
            this.value = null;
            return;
        }

        this.value = Identifier.tryParse(nbt.getString(key));
    }

    @Override
    public String toString() {

        return this.value == null ? "" : this.value.toString();
    }

}
