package lgbt.greenhouse.enchantmentdisabletag.mixin;

import lgbt.greenhouse.enchantmentdisabletag.EnchantmentDisableTag;
import lgbt.greenhouse.enchantmentdisabletag.duck.Duck_PotentialEnchantmentDisabledStack;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnchantedBookItem.class)
public abstract class Mixin_EnchantedBookItem {
    @Inject(method = "addEnchantment", at = @At("HEAD"), cancellable = true)
    private static void enchantmentdisabletag$disableEnchantmentFromBeingDirectlyAddedToBook(ItemStack stack, EnchantmentInstance instance, CallbackInfo ci) {
        var holder = EnchantmentDisableTag.getHelper().getHolder(instance.enchantment);
        if (holder.isPresent() && holder.get().is(EnchantmentDisableTag.DISABLED)) {
            ((Duck_PotentialEnchantmentDisabledStack)(Object)stack).enchantmentdisabletag$setWasDisabled();
            ci.cancel();
        }
    }
}
