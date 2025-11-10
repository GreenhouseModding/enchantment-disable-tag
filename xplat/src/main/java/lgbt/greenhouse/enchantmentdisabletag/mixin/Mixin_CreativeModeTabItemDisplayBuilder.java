package lgbt.greenhouse.enchantmentdisabletag.mixin;

import lgbt.greenhouse.enchantmentdisabletag.duck.Duck_PotentialEnchantmentDisabledStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.Set;

@Mixin(CreativeModeTab.ItemDisplayBuilder.class)
public class Mixin_CreativeModeTabItemDisplayBuilder {
    @Shadow
    @Final
    public Collection<ItemStack> tabContents;

    @Shadow
    @Final
    public Set<ItemStack> searchTabContents;

    @SuppressWarnings("ConstantValue")
    @Inject(method = "accept", at = @At("HEAD"), cancellable = true)
    private void enchantmentdisabletag$filterOutInvalidCreativeItems(ItemStack stack, CreativeModeTab.TabVisibility visibility, CallbackInfo ci) {
        if (
                stack.getItem() instanceof EnchantedBookItem && EnchantedBookItem.getEnchantments(stack).isEmpty() && ((Duck_PotentialEnchantmentDisabledStack)(Object)stack).enchantmentdisabletag$wasDisabled() ||
                visibility != CreativeModeTab.TabVisibility.SEARCH_TAB_ONLY && tabContents.contains(stack) && ((Duck_PotentialEnchantmentDisabledStack)(Object)stack).enchantmentdisabletag$wasDisabled() ||
                visibility == CreativeModeTab.TabVisibility.SEARCH_TAB_ONLY && searchTabContents.contains(stack) && ((Duck_PotentialEnchantmentDisabledStack)(Object)stack).enchantmentdisabletag$wasDisabled()
        ) {
            ci.cancel();
        }
    }
}
