package dev.greenhouseteam.enchantmentdisabletag.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.greenhouseteam.enchantmentdisabletag.EnchantmentDisableTag;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.CreativeModeTabs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CreativeModeTabs.class)
public class CreativeModeTabsMixin {

    @ModifyExpressionValue(method = "tryRebuildTabContents", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CreativeModeTab$ItemDisplayParameters;needsUpdate(Lnet/minecraft/world/flag/FeatureFlagSet;ZLnet/minecraft/core/HolderLookup$Provider;)Z"))
    private static boolean enchantmentdisabletag$refreshTabContents(boolean original, FeatureFlagSet flagSet, boolean hasPermissions, HolderLookup.Provider provider) {
        return original || EnchantmentDisableTag.getAndResetReloadState();
    }
}
