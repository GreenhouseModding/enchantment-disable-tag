package dev.greenhouseteam.enchantmentdisabletag;

import dev.greenhouseteam.enchantmentdisabletag.platform.EnchantmentDisableTagPlatformHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentDisableTag {
    public static final String MOD_ID = "enchantmentdisabletag";

    private static EnchantmentDisableTagPlatformHelper helper;
    private static boolean reloaded = false;

    public static void init(EnchantmentDisableTagPlatformHelper helper) {
        EnchantmentDisableTag.helper = helper;
    }

    public static EnchantmentDisableTagPlatformHelper getHelper() {
        return helper;
    }

    public static void removeDisabledEnchantments(ItemStack stack) {
        if (stack.getTag() == null)
            return;
        if (stack.getTag().contains("Enchantments", Tag.TAG_LIST)) {
            removeDisabledEnchantments(stack.getTag().getList("Enchantments", Tag.TAG_COMPOUND));
            if (stack.getTag().getList("Enchantments", Tag.TAG_COMPOUND).isEmpty())
                stack.removeTagKey("Enchantments");
        }
        if (stack.getItem() instanceof EnchantedBookItem && stack.getTag().contains("StoredEnchantments", Tag.TAG_LIST)) {
            removeDisabledEnchantments(stack.getTag().getList("StoredEnchantments", Tag.TAG_COMPOUND));
            if (stack.getTag().getList("StoredEnchantments", Tag.TAG_COMPOUND).isEmpty())
                stack.removeTagKey("StoredEnchantments");
        }
    }

    public static void removeDisabledEnchantments(ListTag list) {
        if (list == null)
            return;
        list.removeIf(tag -> {
            if (!(tag instanceof CompoundTag compoundTag))
                return false;
            if (compoundTag.contains("id", Tag.TAG_STRING)) {
                Holder<Enchantment> enchantmentHolder = EnchantmentDisableTag.getHolder(BuiltInRegistries.ENCHANTMENT.get(new ResourceLocation(compoundTag.getString("id"))));
                return enchantmentHolder != null && enchantmentHolder.is(EnchantmentDisableTags.DISABLED);
            }
            return false;
        });
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

    public static Holder<Enchantment> getHolder(Enchantment enchantment) {
        return helper.getHolder(enchantment).orElse(null);
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}