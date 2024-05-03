package dev.greenhouseteam.enchantmentdisabletag.mixin;

import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import dev.greenhouseteam.enchantmentdisabletag.EnchantmentDisableTag;
import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.sugar.Share;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
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

    @Unique
    private boolean enchantmentdisabletag$shouldDisable = false;

    @Inject(method = "<init>(III[Lnet/minecraft/world/item/enchantment/Enchantment;)V", at = @At("TAIL"))
    private void enchantmentdisabletag$disableFromVillagerTrades(int minLevel, int maxLevel, int villagerXp, Enchantment[] enchantments, CallbackInfo ci, @Share("shouldCancel") LocalBooleanRef shouldCancel) {
        List<Enchantment> finalEnchantments = new ArrayList<>();
        for (Enchantment enchantment : enchantments) {
            if (!enchantment.builtInRegistryHolder().is(EnchantmentDisableTag.DISABLED_ENCHANTMENT_TAG))
                finalEnchantments.add(enchantment);
        }
        enchantmentdisabletag$shouldDisable = finalEnchantments.isEmpty();
        this.tradeableEnchantments = ImmutableList.copyOf(finalEnchantments);
    }

    @Inject(method = "getOffer", at = @At("HEAD"), cancellable = true)
    private void enchantmentdisabletag$returnNullTrade(Entity entity, RandomSource source, CallbackInfoReturnable<MerchantOffer> cir, @Share("shouldCancel") LocalBooleanRef shouldCancel) {
        if (enchantmentdisabletag$shouldDisable)
            cir.setReturnValue(null);
    }
}
