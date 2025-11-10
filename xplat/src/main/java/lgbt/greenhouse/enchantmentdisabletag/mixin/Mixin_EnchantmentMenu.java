package lgbt.greenhouse.enchantmentdisabletag.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(EnchantmentMenu.class)
public class Mixin_EnchantmentMenu {
    @Shadow @Final public int[] costs;

    @ModifyReturnValue(method =  "getEnchantmentList", at = @At("RETURN"))
    private List<EnchantmentInstance> enchantmentdisabletag$setNoEnchantmentCost(List<EnchantmentInstance> original, @Local(ordinal = 0, argsOnly = true) int slot) {
        // Fix for when enchantments aren't present and a slot shouldn't show up.
        if (original.isEmpty()) {
            costs[slot] = 0;
        }
        return original;
    }
}
