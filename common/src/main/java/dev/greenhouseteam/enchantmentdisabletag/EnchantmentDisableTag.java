package dev.greenhouseteam.enchantmentdisabletag;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.util.Map;

public class EnchantmentDisableTag {
    public static final String MOD_ID = "enchantmentdisabletag";

    public static final TagKey<Enchantment> DISABLED_ENCHANTMENT_TAG = TagKey.create(Registries.ENCHANTMENT, asResource("disabled"));
    public static boolean reloaded = false;

    public static void removeDisabledEnchantments(ItemStack stack) {
        if (stack.has(DataComponents.ENCHANTMENTS)) {
            ItemEnchantments.Mutable itemEnchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
            for (Map.Entry<Holder<Enchantment>, Integer> entry : stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY).entrySet())
                if (entry.getKey().isBound() && !entry.getKey().is(EnchantmentDisableTag.DISABLED_ENCHANTMENT_TAG))
                    itemEnchantments.set(entry.getKey().value(), entry.getValue());
            if (itemEnchantments.keySet().isEmpty())
                stack.remove(DataComponents.ENCHANTMENTS);
            else
                stack.set(DataComponents.ENCHANTMENTS, itemEnchantments.toImmutable());
        }
        if (stack.has(DataComponents.STORED_ENCHANTMENTS)) {
            ItemEnchantments.Mutable itemEnchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
            for (Map.Entry<Holder<Enchantment>, Integer> entry : stack.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY).entrySet())
                if (entry.getKey().isBound() && !entry.getKey().is(EnchantmentDisableTag.DISABLED_ENCHANTMENT_TAG))
                    itemEnchantments.set(entry.getKey().value(), entry.getValue());
            if (itemEnchantments.keySet().isEmpty())
                stack.set(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY);
            else
                stack.set(DataComponents.STORED_ENCHANTMENTS, itemEnchantments.toImmutable());
        }
    }

    public static boolean getAndResetReloadState() {
        boolean retValue = reloaded;
        if (reloaded)
            reloaded = false;
        return retValue;
    }

    public static void setReloaded() {
        reloaded = true;
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}