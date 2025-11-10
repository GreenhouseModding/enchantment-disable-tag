package lgbt.greenhouse.enchantmentdisabletag.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import lgbt.greenhouse.enchantmentdisabletag.EnchantmentDisableTag;
import lgbt.greenhouse.enchantmentdisabletag.duck.Duck_PotentialEnchantmentDisabledStack;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantmentHelper.class)
public abstract class Mixin_EnchantmentHelper {
    @WrapWithCondition(method = "setEnchantments", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/ListTag;add(Ljava/lang/Object;)Z"))
    private static boolean enchantmentdisabletag$disableEnchantmentFromBeingDirectlySet(ListTag instance, Object o, @Local(argsOnly = true) ItemStack stack, @Local Enchantment enchantment) {
        var holder = EnchantmentDisableTag.getHelper().getHolder(enchantment);
        if (holder.isPresent() && holder.get().is(EnchantmentDisableTag.DISABLED)) {
            ((Duck_PotentialEnchantmentDisabledStack)(Object)stack).enchantmentdisabletag$setWasDisabled();
            return false;
        }
        return true;
    }
}
