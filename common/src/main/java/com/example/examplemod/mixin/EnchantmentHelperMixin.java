package com.example.examplemod.mixin;

import com.example.examplemod.access.ItemEnchantmentsMutableAccess;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @Inject(method = "updateEnchantments", at = @At(value = "INVOKE", target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V"))
    private static void enchantmentdisabletag$filterOutDisabledEnchantments(ItemStack stack, Consumer<ItemEnchantments.Mutable> consumer, CallbackInfoReturnable<ItemEnchantments> cir, @Local ItemEnchantments.Mutable mutable) {
        ((ItemEnchantmentsMutableAccess)mutable).enchantmentdisabletag$setToValidate();
    }
}
