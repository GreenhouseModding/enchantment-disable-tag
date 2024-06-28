package dev.greenhouseteam.enchantmentdisabletag.mixin.fabric;

import dev.greenhouseteam.enchantmentdisabletag.EnchantmentDisableTag;
import net.minecraft.server.ReloadableServerResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ReloadableServerResources.class)
public class ReloadableServerResourcesMixin {
    @Inject(method = "updateRegistryTags()V", at = @At("TAIL"))
    private void enchantmentdisabletag$setToReloadCreativeTabsServer(CallbackInfo ci) {
        EnchantmentDisableTag.setCreativeTabToReload();
    }
}
