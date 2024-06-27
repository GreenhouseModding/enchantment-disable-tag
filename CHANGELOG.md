## Bugfixes
- [FABRIC] Fixed crash involving platform helper being loaded at the wrong time.
- Fixed setting enchantments operating on an immutable map.
- Caught more edge cases where a disabled enchantment can slip through when playing with the mod serverside.

## Internal
- Moved EnchantmentDisableTag#DISABLED_ENCHANTMENT_TAG field to EnchantmentDisabledTags#DISABLED.