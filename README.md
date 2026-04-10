# Anisekai Fabric Mod

This is a personal mod created for my server. You are welcome to use it, but please note that very limited support will be provided. 

**Note:** A few features in this mod (specifically the Coins and Condenser) require a datapack to function fully.

### Furniture

This mod adds a set of furniture blocks for every vanilla wood variant (updated for 1.21):

- **Half Slab:** A true 1/4 block slab.
- **Decorative Staircase:** A sleek staircase design meant for interiors, rather than roofs.
- **Nightstand**
- **Stool**
- **Table**

All of these blocks dynamically reference vanilla textures. This means if you use a resource pack, the furniture will automatically use your resource pack's textures instead of the default vanilla ones!

### Coins

The mod provides coin items without any built-in crafting recipes or mechanics, giving you complete freedom to decide how your players use them. You can integrate them into your server's economy by adding them to villager trades, loot tables, or custom crafting recipes via a datapack.

Available coins:
- `anisekai:copper_coin`
- `anisekai:iron_coin`
- `anisekai:gold_coin`
- `anisekai:diamond_coin`
- `anisekai:emerald_coin`

### Fishing Basket

This feature started as an experimental "I want to try to make this" idea! It is essentially a shulker box that you can sit on, and it only accepts fish-type items. 

When you reel in an item while fishing and sitting on it, if it is a fish, it will bypass your inventory and go directly into the basket. Non-fish items (like junk or treasure) will behave normally and go to your inventory.

### Quality of Life: Hoes & Shovels

- **Easy Harvest:** Right-clicking fully grown crops with a hoe will automatically harvest them and reset their growth stage to 0. This saves you the hassle of breaking and replanting all your crops!
- **Easy Dirt:** Right-clicking farmland with a shovel converts it back to dirt. No more jumping around to trample it or breaking and replacing blocks!

### Spawners

Spawners can now be mined and retrieved using the **Silk Touch** enchantment.

### Condenser

The `condenser` is a new block that allows you to generate resources in a balanced, vanilla-friendly way, depending on how you configure it via datapacks.

**Example Datapack Recipe:**
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

**How it works:**
*   **`apply` (Top-Left Slot):** The primary item. In the example above, an oak sapling.
*   **`onto` (Top-Right Slot):** The secondary item/catalyst. In the example above, dirt.
*   **`booster` (Bottom-Middle Slot):** An optional boosting item.
*   **`tool` (Right Slot):** The tool required to process the recipe. 

**Mechanics:**
*   **Consumption (`consume`):** When set to `true`, one unit will be removed from the slot when the recipe finishes. In the example above, the sapling is consumed (`true`), but the dirt stays in the condenser (`false`). *Tip: You could set both to `false` to create a cobblestone generator using a water bucket and a lava bucket!*
*   **Tool Durability:** Durability is processed exactly like vanilla Minecraft. The game applies damage to the tool (1 durability per block, factoring in the Unbreaking enchantment if applicable).
*   **Processing Time (`time`):** This represents the exact amount of ticks required to complete the condensing process and produce the output. **Note:** This time does not scale with the output amount. 400 ticks will take 400 ticks, whether the output gives you 6 blocks or 12 blocks.
