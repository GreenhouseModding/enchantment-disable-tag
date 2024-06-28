package dev.greenhouseteam.enchantmentdisabletag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentDisableTags {
    public static final TagKey<Enchantment> DISABLED = TagKey.create(Registries.ENCHANTMENT, EnchantmentDisableTag.asResource("disabled"));
}