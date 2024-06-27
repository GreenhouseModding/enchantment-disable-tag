package dev.greenhouseteam.enchantmentdisabletag.mixin;

import dev.greenhouseteam.enchantmentdisabletag.EnchantmentDisableTag;
import dev.greenhouseteam.enchantmentdisabletag.EnchantmentDisableTags;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(EnchantRandomlyFunction.class)
public class EnchantRandomlyFunctionMixin {

    @Shadow @Mutable @Final
    List<Enchantment> enchantments;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void enchantmentdisabletag$modifyEnchantmentList(CallbackInfo ci) {
        enchantments = enchantments.stream().filter(enchantment -> !EnchantmentDisableTag.getHolder(enchantment).is(EnchantmentDisableTags.DISABLED)).toList();
    }
}
