package dev.greenhouseteam.enchantmentdisabletag.mixin;

import dev.greenhouseteam.enchantmentdisabletag.EnchantmentDisableTag;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Slot.class)
public class SlotMixin {
    @ModifyVariable(method = "set", at = @At("HEAD"), argsOnly = true)
    private ItemStack enchantmentdisabletag$removeDisabledEnchantments(ItemStack stack) {
        EnchantmentDisableTag.removeDisabledEnchantments(stack);
        return stack;
    }
}
