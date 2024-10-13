package dev.greenhouseteam.enchantmentdisabletag.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(EnchantmentMenu.class)
public class EnchantmentMenuMixin {
    @Shadow @Final public int[] costs;

    @Inject(method =  { "method_17411", "lambda$slotsChanged$0" }, at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z"))
    private void enchantmentdisabletag$setNoEnchantmentCost(ItemStack stack, Level level, BlockPos pos, CallbackInfo ci, @Local(ordinal = 1) int l, @Local List<EnchantmentInstance> enchantments) {
        // Fix for when enchantments aren't present and a slot shouldn't show up.
        if (enchantments == null || enchantments.isEmpty())
            costs[l] = 0;
    }
}
