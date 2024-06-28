package dev.greenhouseteam.enchantmentdisabletag;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.util.Map;

public class EnchantmentDisableTag {
    public static final String MOD_ID = "enchantmentdisabletag";

    private static boolean creativeTabReload = false;
    private static boolean setToBook = false;

    public static ItemStack removeDisabledEnchantments(ItemStack stack) {
        if (stack.has(DataComponents.ENCHANTMENTS) && !stack.getEnchantments().isEmpty()) {
            ItemEnchantments.Mutable itemEnchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
            for (Map.Entry<Holder<Enchantment>, Integer> entry : stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY).entrySet())
                if (entry.getKey().isBound() && !entry.getKey().is(EnchantmentDisableTags.DISABLED))
                    itemEnchantments.set(entry.getKey(), entry.getValue());
            if (itemEnchantments.keySet().isEmpty())
                stack.remove(DataComponents.ENCHANTMENTS);
            else
                stack.set(DataComponents.ENCHANTMENTS, itemEnchantments.toImmutable());
        }
        if (stack.has(DataComponents.STORED_ENCHANTMENTS) && !stack.get(DataComponents.STORED_ENCHANTMENTS).isEmpty()) {
            ItemEnchantments.Mutable itemEnchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
            for (Map.Entry<Holder<Enchantment>, Integer> entry : stack.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY).entrySet())
                if (entry.getKey().isBound() && !entry.getKey().is(EnchantmentDisableTags.DISABLED))
                    itemEnchantments.set(entry.getKey(), entry.getValue());
            if (itemEnchantments.keySet().isEmpty()) {
                stack.remove(DataComponents.STORED_ENCHANTMENTS);
                if (stack.is(Items.ENCHANTED_BOOK)) {
                    ItemStack book = new ItemStack(Items.BOOK);
                    book.applyComponents(stack.getComponentsPatch());
                    return book;
                }
            } else
                stack.set(DataComponents.STORED_ENCHANTMENTS, itemEnchantments.toImmutable());
        }
        return stack;
    }

    public static boolean shouldSetToBookAndResetState() {
        boolean retValue = setToBook;
        setToBook = false;
        return retValue;
    }

    public static void setBookState() {
        setToBook = true;
    }

    public static boolean getAndResetCreativeTabReloadState() {
        boolean retValue = creativeTabReload;
        creativeTabReload = false;
        return retValue;
    }

    public static void setCreativeTabToReload() {
        creativeTabReload = true;
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.tryBuild(MOD_ID, path);
    }
}