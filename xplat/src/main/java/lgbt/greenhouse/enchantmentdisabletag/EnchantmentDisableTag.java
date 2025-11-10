package lgbt.greenhouse.enchantmentdisabletag;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.util.Map;

public class EnchantmentDisableTag {
    public static final String MOD_ID = "enchantmentdisabletag";
    public static final TagKey<Enchantment> DISABLED = TagKey.create(Registries.ENCHANTMENT, EnchantmentDisableTag.id("disabled"));

    private static boolean creativeTabReload = false;

    public static ItemStack removeDisabledEnchantments(ItemStack stack) {
        ItemStack modifiedStack = stack.copy();

        if (!stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY).isEmpty()) {
            ItemEnchantments.Mutable enchantmentsMutable = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
            for (Map.Entry<Holder<Enchantment>, Integer> entry : stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY).entrySet()) {
                if (entry.getKey().isBound() && !entry.getKey().is(DISABLED)) {
                    enchantmentsMutable.set(entry.getKey(), entry.getValue());
                }
            }
            ItemEnchantments enchantments = enchantmentsMutable.toImmutable();
            if (enchantments.isEmpty()) {
                modifiedStack.remove(DataComponents.ENCHANTMENTS);
            } else {
                modifiedStack.set(DataComponents.ENCHANTMENTS, enchantments);
            }
        }
        if (!stack.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY).isEmpty()) {
            ItemEnchantments.Mutable storedEnchantmentsMutable = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
            for (Map.Entry<Holder<Enchantment>, Integer> entry : stack.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY).entrySet()) {
                if (entry.getKey().isBound() && !entry.getKey().is(DISABLED)) {
                    storedEnchantmentsMutable.set(entry.getKey(), entry.getValue());
                }
            }
            ItemEnchantments storedEnchantments = storedEnchantmentsMutable.toImmutable();
            if (storedEnchantments.isEmpty()) {
                modifiedStack.remove(DataComponents.STORED_ENCHANTMENTS);
                if (modifiedStack.is(Items.ENCHANTED_BOOK)) {
                    return modifiedStack.transmuteCopy(Items.BOOK);
                }
            } else {
                modifiedStack.set(DataComponents.STORED_ENCHANTMENTS, storedEnchantments);
            }
        }

        return !ItemStack.isSameItemSameComponents(stack, modifiedStack) ? modifiedStack : stack;
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