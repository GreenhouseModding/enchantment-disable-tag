package dev.greenhouseteam.enchantmentdisabletag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentDisableTag {
    public static final String MOD_ID = "enchantmentdisabletag";

    public static final TagKey<Enchantment> DISABLED_ENCHANTMENT_TAG = TagKey.create(Registries.ENCHANTMENT, asResource("disabled"));

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}