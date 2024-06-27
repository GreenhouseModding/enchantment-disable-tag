package dev.greenhouseteam.enchantmentdisabletag.mixin;

import dev.greenhouseteam.enchantmentdisabletag.EnchantmentDisableTag;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(AbstractContainerMenu.class)
public class AbstractContainerMenuMixin {
    @ModifyVariable(method = "setCarried", at = @At("HEAD"), argsOnly = true)
    private ItemStack enchantmentdisabletag$removeFromCarried(ItemStack stack) {
        EnchantmentDisableTag.removeDisabledEnchantments(stack);
        return stack;
    }
}
