package lgbt.greenhouse.enchantmentdisabletag.mixin;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeModeTab.ItemDisplayBuilder.class)
public class Mixin_CreativeModeTabItemDisplayBuilder {
    // I could not get this to work with the event.
    // A mixin will do.
    @Inject(method = "accept", at = @At("HEAD"), cancellable = true)
    private void enchantmentdisabletag$filterOutInvalidCreativeItems(ItemStack stack, CreativeModeTab.TabVisibility visibility, CallbackInfo ci) {
        if (stack.getItem() instanceof EnchantedBookItem && EnchantedBookItem.getEnchantments(stack).isEmpty()) {
            ci.cancel();
        }
    }
}
