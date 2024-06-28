package dev.greenhouseteam.enchantmentdisabletag.mixin;

import dev.greenhouseteam.enchantmentdisabletag.EnchantmentDisableTags;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Optional;

@Mixin(VillagerTrades.EnchantBookForEmeralds.class)
public class VillagerTradesEnchantBookForEmeraldsMixin {
    @Shadow @Final private TagKey<Enchantment> tradeableEnchantments;

    @ModifyVariable(method = "getOffer", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/core/Registry;getRandomElementOf(Lnet/minecraft/tags/TagKey;Lnet/minecraft/util/RandomSource;)Ljava/util/Optional;"))
    private Optional<Holder<Enchantment>> enchantmentdisabletag$returnHolderOptional(Optional<Holder<Enchantment>> holder, Entity entity, RandomSource random) {
        if (holder.isPresent() && holder.get().is(EnchantmentDisableTags.DISABLED))
            return entity.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT).getTag(tradeableEnchantments).flatMap(holders -> Util.getRandomSafe(holders.stream().filter(h -> !h.is(EnchantmentDisableTags.DISABLED)).toList(), random));
        return holder;
    }
}
