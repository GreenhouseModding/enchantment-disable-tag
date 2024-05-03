package com.example.examplemod.mixin;

import com.example.examplemod.EnchantmentDisabledTag;
import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(VillagerTrades.EnchantBookForEmeralds.class)
public class VillagerTradesEnchantBookForEmeraldsMixin {
    @Shadow @Mutable @Final
    private List<Enchantment> tradeableEnchantments;

    @Inject(method = "<init>(III[Lnet/minecraft/world/item/enchantment/Enchantment;)V", at = @At("TAIL"))
    private void enchantmentdisabletag$disableFromVillagerTrades(int minLevel, int maxLevel, int villagerXp, Enchantment[] enchantments, CallbackInfo ci, @Share("shouldCancel") LocalRef<Unit> shouldCancel) {
        List<Enchantment> finalEnchantments = new ArrayList<>();
        for (Enchantment enchantment : enchantments) {
            if (!enchantment.builtInRegistryHolder().is(EnchantmentDisabledTag.DISABLED_ENCHANTMENT_TAG))
                finalEnchantments.add(enchantment);
        }
        if (finalEnchantments.isEmpty())
            shouldCancel.set(Unit.INSTANCE);
        this.tradeableEnchantments = ImmutableList.copyOf(finalEnchantments);
    }

    @Inject(method = "getOffer", at = @At("HEAD"), cancellable = true)
    private void enchantmentdisabletag$returnNullTrade(Entity entity, RandomSource source, CallbackInfoReturnable<MerchantOffer> cir, @Share("shouldCancel") LocalRef<Unit> shouldCancel) {
        if (shouldCancel.get() != null)
            cir.setReturnValue(null);
    }
}
