# Akio's Furniture Sources

This folder contains all the useful tools to automatically generate all files needed for my mod. You are not allowed to
use the source files or the generated one, but you are free to use the generator itself in your project. It is a huge
time saver when creating block variants, trust me.

Everything in the `static` folder will be copied as-is into the main `resources` folder. As this is the last step in the
devtool generation chain, it is useful for static files (like the mod icon) or to overwrite a specific generated
variant.

### Templating

The source files (under the `minecraft` directory) are json files with *template strings* in them, all following the
`{{key}}` format.

The keys available are the following (using `chair.json` in the `oak` variant as example for the values):

| key  | value              | description                                                                                   |
|------|--------------------|-----------------------------------------------------------------------------------------------|
| mod  | anisekai           | The ID of your mod, as defined in fabric.mod.json                                             |
| name | chair              | Name of the file without the extension, useful if you need a single value across all variants |
| item | oak_chair          | Name of the item being generated (`{{type}}_{{name}}` if using variant, `{{name}}` otherwise) |
| id   | anisekai:oak_chair | ID of the item being generated (equivalent to `{{mod}}:{{item}}`)                             |
| type | oak                | Name of the variant (only available in variant generation context)                            |

Also, more elements (called aliases) are dynamically added to this table depending on your variant declaration in
`config.json`

For instance, something like this will add `log` and `planks` for the `oak` variant:

```json
{
  "name":   "woods",
  "values": [
    {
      "name":     "oak",
      "mappings": {
        "log":    "oak_log",
        "planks": "oak_planks"
      }
    }
  ]
}
```
