package dev.greenhouseteam.enchantmentdisabletag.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.greenhouseteam.enchantmentdisabletag.EnchantmentDisableTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(method = "enchant", at = @At("HEAD"), cancellable = true)
    private void enchantmentdisabletag$disallowEnchant(Enchantment enchantment, int number, CallbackInfo ci) {
        if (EnchantmentDisableTag.getHolder(enchantment).is(EnchantmentDisableTag.DISABLED_ENCHANTMENT_TAG))
            ci.cancel();
    }

    @ModifyReturnValue(method = "getEnchantmentTags", at = @At(value = "RETURN", ordinal = 0))
    private ListTag enchantmentdisabletag$removeFromEnchantmentTags(ListTag original) {
        EnchantmentDisableTag.removeDisabledEnchantments(original);
        return original;
    }
}
