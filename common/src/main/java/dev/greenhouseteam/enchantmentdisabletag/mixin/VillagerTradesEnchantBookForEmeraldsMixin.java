package dev.greenhouseteam.enchantmentdisabletag.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(VillagerTrades.EnchantBookForEmeralds.class)
public class VillagerTradesEnchantBookForEmeraldsMixin {
    @Inject(method = "getOffer", at = @At(value = "INVOKE", target = "Ljava/util/List;get(I)Ljava/lang/Object;", shift = At.Shift.BEFORE), cancellable = true)
    private void enchantmentdisabletag$disableOffer(Entity entity, RandomSource random, CallbackInfoReturnable<MerchantOffer> cir, @Local List<Enchantment> enchantments) {
        if (enchantments.isEmpty())
            cir.setReturnValue(null);
    }
}
