package dev.greenhouseteam.enchantmentdisabletag.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.greenhouseteam.enchantmentdisabletag.EnchantmentDisableTag;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(CreativeModeTabs.class)
public class CreativeModeTabsMixin {
    @Unique
    private static List<Holder<Enchantment>> enchantmentdisabletag$disabledTagHolders;

    @ModifyExpressionValue(method = "tryRebuildTabContents", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CreativeModeTab$ItemDisplayParameters;needsUpdate(Lnet/minecraft/world/flag/FeatureFlagSet;ZLnet/minecraft/core/HolderLookup$Provider;)Z"))
    private static boolean enchantmentdisabletag$refreshTabContents(boolean original, FeatureFlagSet flagSet, boolean hasPermissions, HolderLookup.Provider provider) {
        List<Holder<Enchantment>> currentDisabledTag = provider.lookup(Registries.ENCHANTMENT).flatMap(lookup -> lookup.get(EnchantmentDisableTag.DISABLED_ENCHANTMENT_TAG).map(tag -> List.copyOf(tag.stream().toList()))).orElse(List.of());
        boolean returnValue = original && enchantmentdisabletag$disabledTagHolders.equals(currentDisabledTag);
        enchantmentdisabletag$disabledTagHolders = currentDisabledTag;
        return returnValue;
    }
}
