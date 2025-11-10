package lgbt.greenhouse.enchantmentdisabletag;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public class EnchantmentDisableTag {
    public static final String MOD_ID = "enchantmentdisabletag";
    public static final TagKey<Enchantment> DISABLED = TagKey.create(Registries.ENCHANTMENT, EnchantmentDisableTag.id("disabled"));

    private static boolean creativeTabReload = false;

    @SuppressWarnings("unchecked")
    public static void removeDisabledEnchantments(ItemStack stack) {
        // We can cheat here as all of the logic is contained within the set method.
        // Remove disabled enchantments from every component that is ItemEnchantments.
        for (var entry : stack.getComponentsPatch().entrySet()) {
            if (entry.getValue().isPresent() && entry.getValue().get() instanceof ItemEnchantments itemEnchantments) {
                stack.set((DataComponentType<? super ItemEnchantments>) entry.getKey(), itemEnchantments);
            }
        }
    }

    public static boolean getAndResetCreativeTabReloadState() {
        boolean retValue = creativeTabReload;
        creativeTabReload = false;
        return retValue;
    }

    public static void setCreativeTabToReload() {
        creativeTabReload = true;
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}