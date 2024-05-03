package com.example.examplemod;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnchantmentDisabledTag {    public static final String MOD_ID = "enchantmentdisabledtag";
    public static final String MOD_NAME = "Enchantment Disabled Tag";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final TagKey<Enchantment> DISABLED_ENCHANTMENT_TAG = TagKey.create(Registries.ENCHANTMENT, asResource("disabled"));

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}