package dev.greenhouseteam.enchantmentdisabletag.mixin.forge;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeModeTab.ItemDisplayBuilder.class)
public class CreativeModeTabMixin {
    @Inject(method = "accept", at = @At("HEAD"), cancellable = true)
    private void enchantmentdisabletag$filterOutInvalidCreativeItems(ItemStack stack, CreativeModeTab.TabVisibility visibility, CallbackInfo ci) {
        if (stack.getItem() instanceof EnchantedBookItem && EnchantedBookItem.getEnchantments(stack).isEmpty())
            ci.cancel();
    }
}
