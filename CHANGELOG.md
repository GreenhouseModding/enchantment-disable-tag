## Changes
- Any Enchanted Books that have no enchantments as a result of this mod will now turn into regular Books.

## Bugfixes
- [FABRIC] Fixed crash involving platform helper being loaded at the wrong time.
- Fixed setting enchantments operating on an immutable map.
- Caught more edge cases where a disabled enchantment can be obtained from containers when playing with the mod serverside.

## Internal
- Moved EnchantmentDisableTag#DISABLED_ENCHANTMENT_TAG field to EnchantmentDisabledTags#DISABLED.
  - This was unfortunately a required change to fix Fabric platform helper.