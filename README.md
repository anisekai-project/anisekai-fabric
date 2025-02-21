# Anisekai Fabric

This is a personal mod for my server.
This mod is free to use but very limited support will be provided (ie: only bugfixes)

Few features of this mod has to be used alongside a datapack (not provided).

### Furniture

The mod provides a set of furniture blocks for each vanilla wood variant (as of 1.21):

- Half Slab (1/4 block, the real "half slab")
- Staircase (a design one that you don't want to use as roof)
- Nightstand
- Stool
- Table

All of those uses references to vanilla resources, which mean that if you use a resourcepack, those texture will be
applied instead of the vanilla one.

### Coins

The mod provides coins item without any craft / features associated to it to let you handle how your player may use it.
You can use them in your economy by adding
those in villager trades, loot tables, craft, etc...

- `anisekai:copper_coin`
- `anisekai:iron_coin`
- `anisekai:gold_coin`
- `anisekai:diamond_coin`
- `anisekai:emerald_coin`

### Fishing Basket

This one is more of a "I want to try to do that" thing more than something that was actually planned. It is basically a
shulkerbox that only accept fish-type items which you can sit on.

If you fish something out of the water and the item reeled in is a fish-type item, it will go directly into the basket,
otherwise it will act as normal.

### Hoes And Shovels

Right-clicking a fully aged crop with a hoe will harvest it and reset its age to 0. This avoids the pain of breaking all
of your crops to replant right after.

Right-clicking a farmland with a shovel convert it back to dirt. No more jumping or breaking/placing block !

### Condenser

The mod provides a block called `condenser` which allows you to create resources in a less "cheating" or "non-vanilla"
way (if you use it / configure it correctly).

```json
{
  "type":    "anisekai:condenser",
  "apply":   { "use": { "item": "minecraft:oak_sapling" }, "consume": true },
  "onto":    { "use": { "tag": "minecraft:dirt" }, "consume": false },
  "tool":    { "tag": "minecraft:axes" },
  "booster": { "item": "minecraft:bone_meal" },
  "time":    400,
  "result":  {
    "count": 6,
    "id":    "minecraft:oak_log"
  }
}
```

Here `apply` refer to the first slot (top-left), `onto` the second (top-right), `booster` the third one (bottom-middle)
and `tool` to the last slot (right).

When `consume` is set to `true`, upon *condensing* the result, one unit will be removed from the slot. In the example
above, one sapling will be consumed but the dirt will stay in the condenser. You could set both to `false` to create a
cobblestone generator using a water bucket and a lava bucket.

The way durability is processed is the same as vanilla, as it uses the game method to damage the tool (1 durability per
block without unbreaking, or random for each damage depending on the unbreaking level).

The time required to complete the *condensing* is declared using `time` which represent the amount of tick required to
produce the output. This does not scale with the amount of block produced as output; 400 ticks for 6 blocks will
still be 400 ticks, much like 400 ticks for 12 blocks will also be 400 ticks.
