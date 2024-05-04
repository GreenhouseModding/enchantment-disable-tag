package dev.greenhouseteam.enchantmentdisabletag.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.greenhouseteam.enchantmentdisabletag.EnchantmentDisableTag;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantedBookItem.class)
public class EnchantedBookItemMixin {
    @ModifyReturnValue(method = "getEnchantments", at = @At(value = "RETURN", ordinal = 0))
    private static ListTag enchantmentdisabletag$removeFromEnchantmentTags(ListTag original) {
        EnchantmentDisableTag.removeDisabledEnchantments(original);
        return original;
    }
}
