## Changes
- Any Enchanted Books that have no enchantments as a result of this mod will now turn into regular Books.

## Bugfixes
- Caught more edge cases where a disabled enchantment can be obtained from containers when playing with the mod serverside.
- Fixed /enchant and the enchanting table not functioning.
- Fixed items stacking incorrectly when moved to different inventories.

## Internal
- Moved EnchantmentDisableTag#DISABLED_ENCHANTMENT_TAG field to EnchantmentDisabledTags#DISABLED.
  - This was done to match the 1.20.1 version.