package dev.greenhouseteam.enchantmentdisabletag.mixin;

import dev.greenhouseteam.enchantmentdisabletag.EnchantmentDisableTags;
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
import java.util.Optional;

@Mixin(EnchantRandomlyFunction.class)
public class EnchantRandomlyFunctionMixin {

    @Mutable @Shadow @Final
    private Optional<HolderSet<Enchantment>> options;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void enchantmentdisabletag$modifyEnchantmentSetCodec(CallbackInfo ci) {
        if (options.isEmpty())
            return;
        List<Holder<Enchantment>> enchantmentHolders = new ArrayList<>();
        for (int i = 0; i < options.get().size(); ++i) {
            if (!options.get().get(i).is(EnchantmentDisableTags.DISABLED))
                enchantmentHolders.add(options.get().get(i));
        }
        if (options.stream().toList().equals(enchantmentHolders))
            return;
        this.options = Optional.of(HolderSet.direct(enchantmentHolders));
    }
}
