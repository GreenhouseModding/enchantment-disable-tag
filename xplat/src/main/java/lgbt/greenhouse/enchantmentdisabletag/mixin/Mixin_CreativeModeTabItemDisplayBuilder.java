package lgbt.greenhouse.enchantmentdisabletag.mixin;

import lgbt.greenhouse.enchantmentdisabletag.duck.Duck_PotentialEnchantmentDisabledStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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

    @Inject(method = "accept", at = @At("HEAD"), cancellable = true)
    private void enchantmentdisabletag$filterOutInvalidCreativeItems(ItemStack stack, CreativeModeTab.TabVisibility visibility, CallbackInfo ci) {
        if (
                 ((Duck_PotentialEnchantmentDisabledStack)(Object)stack).enchantmentdisabletag$wasDisabled() &&
                 (
                         stack.is(Items.ENCHANTED_BOOK) && EnchantedBookItem.getEnchantments(stack).isEmpty() ||
                         visibility != CreativeModeTab.TabVisibility.SEARCH_TAB_ONLY && tabContents.contains(stack) ||
                         visibility == CreativeModeTab.TabVisibility.SEARCH_TAB_ONLY && searchTabContents.contains(stack)
                 )
        ) {
            ci.cancel();
        }
    }
}
