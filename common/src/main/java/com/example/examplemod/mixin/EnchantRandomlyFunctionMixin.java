package com.example.examplemod.mixin;

import com.example.examplemod.EnchantmentDisabledTag;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Mixin(EnchantRandomlyFunction.class)
public class EnchantRandomlyFunctionMixin {
    @Shadow @Mutable @Final private static Codec<HolderSet<Enchantment>> ENCHANTMENT_SET_CODEC;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void enchantmentdisabletag$modifyEnchantmentSetCodec(CallbackInfo ci) {
        ENCHANTMENT_SET_CODEC.xmap(holders -> {
            List<Holder<Enchantment>> enchantmentHolders = new ArrayList<>();
            for (int i = 0; i < holders.size(); ++i) {
                if (!holders.get(i).is(EnchantmentDisabledTag.DISABLED_ENCHANTMENT_TAG))
                    enchantmentHolders.add(holders.get(i));
            }
            return HolderSet.direct(enchantmentHolders);
        }, Function.identity());
    }
}
