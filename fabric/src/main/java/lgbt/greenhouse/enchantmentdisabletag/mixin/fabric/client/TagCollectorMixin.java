package lgbt.greenhouse.enchantmentdisabletag.mixin.fabric.client;

import lgbt.greenhouse.enchantmentdisabletag.EnchantmentDisableTag;
import net.minecraft.client.multiplayer.RegistryDataCollector;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.packs.resources.ResourceProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RegistryDataCollector.class)
public class TagCollectorMixin {
    @Inject(method = "loadNewElementsAndTags", at = @At("TAIL"))
    private void enchantmentdisabletag$updateTagValue(ResourceProvider resourceProvider, RegistryDataCollector.ContentsCollector contentCollector, boolean isMemoryConnection, CallbackInfoReturnable<RegistryAccess> cir) {
        EnchantmentDisableTag.setCreativeTabToReload();
    }
}
