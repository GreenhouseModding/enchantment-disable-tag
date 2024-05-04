package dev.greenhouseteam.enchantmentdisabletag.mixin;

import dev.greenhouseteam.enchantmentdisabletag.EnchantmentDisableTag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Map;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @ModifyVariable(method = "setEnchantments", at = @At("HEAD"), argsOnly = true)
    private static Map<Enchantment, Integer> enchantmentdisabletag$removeFromEnchantmentSetting(Map<Enchantment, Integer> original) {
        original.keySet().removeIf(enchantment -> EnchantmentDisableTag.getHolder(enchantment).is(EnchantmentDisableTag.DISABLED_ENCHANTMENT_TAG));
        return original;
    }
}
