package dev.greenhouseteam.enchantmentdisabletag.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.greenhouseteam.enchantmentdisabletag.EnchantmentDisableTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.EnchantedBookItem;
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
