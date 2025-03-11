# Akio's Furniture Sources

This folder contains all the useful tools to automatically generate all files needed for my mod. You are not allowed to
use the source files or the generated one, but you are free to use the generator itself in your project. It is a huge
time saver when creating block variants, trust me.

Everything in the `static` folder will be copied as-is into the main `resources` folder. As this is the last step in the
devtool generation chain, it is useful for static files (like the mod icon) or to overwrite a specific generated
variant.

> Even if every file in the `static` folder will be copied into the main `resources` folder, it is required to keep the `fabric.mod.json`
> file within the `resources` folder so the generator can read the Mod ID from it.

## Configuration

The `config.json` file allows you to configure variants for the generator. Here's a basic example:

```json
{
  "variants": [
    {
      "name":   "coins",
      "values": [
        { "name": "copper" },
        { "name": "iron" },
        { "name": "gold" },
        { "name": "diamond" },
        { "name": "emerald" }
      ]
    }
  ]
}
```

Here, we declare the `coins` group, with 5 variants: `copper`, `iron`, `gold`, `diamond` and `emerald`.

For each variant, you can also declare a set of aliases. Those aliases can be used as template keys later in the template files. Here's an
example:

```json
{
  "variants": [
    {
      "name":   "woods",
      "values": [
        {
          "name":     "oak",
          "mappings": {
            "log":          "oak_log",
            "stripped_log": "stripped_oak_log",
            "fence":        "oak_fence",
            "slab":         "oak_slab",
            "planks":       "oak_planks"
          }
        },
        {
          "name":     "warped",
          "mappings": {
            "log":          "warped_stem",
            "stripped_log": "stripped_warped_stem",
            "fence":        "warped_fence",
            "slab":         "warped_slab",
            "planks":       "warped_planks"
          }
        }
      ]
    }
  ]
}
```

Here, the `oak` and `warped` variants of the `woods` group have 5 aliases: `log`, `stripped_log`, `fence`, `slab` and `planks`. Those will
be
used as template keys and be replaced by their corresponding values when the output files will be generated. This is useful in this case as
`warped` and `oak` has few naming differences.

If you wish, you can use this repository `config.json` in your project.

## Template keys

The generator uses template keys to help modders declare their block and item easily. There are multiple "layers" for template keys, each
having their specificities. From a layer, you can access all its keys and the keys from lower layers.

- Layer 0: This is the base layer. Every key here will ***always*** be available.
    - `namespace`: This is your mod id, as declared in your `fabric.mod.json` file.
    - `name`: Name of the current file, without the extension.
- Layer 1: This is the layer you reach when entering a dynamic variant context.
    - `id`: This is the ID of the block/item with the variant (equivalent to `{{namespace}}:{{type}}_{{name}}`)
    - `item`: This is the name of the block/item with the variant (equivalent to `{{type}}_{{name}}`)
    - `type`: Name of the current variant being handled.
    - Plus all keys defined in `config.json` for the current variant.
- Layer 2: This is the layer you reach if you are using the `maps` keys in your generation settings
    - It will contain all keys you defined in your file.

## Template files in `generator`

The source files under the `generator` directory are *template* files. It allows to have one file per item or block, avoiding navigating
between numerous folders to find what you need. It also allows to generate *variants* of a block easily (for example, all wood types.)

There are 4 root keys in those files, they requirements depending on the combination you are using.

- `files`: This key contain *static* files for the current item or block. This does not mean that template keys cannot be used, but rather
  that those file will be written once, regardless of variants. It is useful to declare the general shape of your block that will be used
  across all variants. It is optional, unless `hitboxes` is used.
- `hitboxes`: This key contain a pair of key-value, the key referencing a file in the `files` section and the value being a name that will
  be associated to the hitbox. The referenced path must be a block model, as the hitbox needs the block model to be calculated. It is
  optional
- `dynamic`: This key contain the definition of variants for the current block or item. It is optional.
- `tags`: This key is an array of tag declaration. It should not be used with the `dynamic` section, as it can use it own tagging sections.

## More about `dynamic`

As stated above, the `dynamic` section contain all the data required to generate variants for a block. Let's look at this example:

```json
{
  "dynamic": {
    "wood": {
      "settings": {
        "usingVariant": {
          "name": "woods",
          "maps": {
            "texture:legs": "{{log}}",
            "texture:seat": "{{planks}}",
            "craft:legs":   "minecraft:{{fence}}",
            "craft:seat":   "{{namespace}}:{{type}}_half_slab"
          }
        }
      },
      "files":    { },
      "tags":     [ ]
    }
  }
}
```

Each key in the `dynamic` section is a name that you can freely give to your generator. In each generator, we will have `files` and `tags`
again, that have the same purpose as the one located at the root, except that this time, specific templating keys will be available, as we
are now in a variant context.

One of the most important part is `settings.usingVariant` which will help you shape the generation how you want.

- `name` references the group name that you have declared in your `config.json` file.
- `maps` is optional and allows you to declare more specific template keys for the `files` and `tags` of the current section. It is
  particularly useful if you want to use better name that will better reflect their use in your block or item (it is the layer 2 mentioned
  in the previous section of this readme).

You can also declare "ghost" generator that will only be used to be extended by other generators:

```json
{
  "dynamic": {
    "base": {
      "settings": {
        "skipGeneration": true
      },
      "files":    { },
      "tags":     [ ]
    },
    "wood": {
      "settings": {
        "usingVariant": {
          "extends": "base",
          "name":    "woods"
        }
      },
      "files":    { },
      "tags":     [ ]
    }
  }
}
```

In this example, `wood` *extends* `base`. All files and tags of `base` will be used, and the content of `wood` will be added on top. If
there are duplicated files, the one in the `wood` section will have priority. For tags however, both array will be merged, but if there are
any duplicates, it will have no side effects.

> Note: It is important to use `skipGeneration` on a section that you **do not want** to generate. Here `base` is only, well, a *base* and
> cannot exist by itself, so the generation has to be skipped. It is, however, totally valid to extends another generator that declare its
> own variants.

You can also exclude specific variants of a variant group for your generator using the `exclude` key.

```json
{
  "dynamic": {
    "wood": {
      "settings": {
        "usingVariant": {
          "name":    "woods",
          "exclude": [ "warped" ]
        }
      },
      "files":    { },
      "tags":     [ ]
    }
  }
}
```

In this example, the variant `warped` will not be generated but all other will.

*Side Notes:*

- If you use a template key that is not defined, the key will be kept as-is and will appear in the output file.
- If you use an unknown variant group, the generator will stop with an error displaying which variant group caused the error.

For example on how to declare `files` and `tags`, please refer to files in the `generator` folder.
