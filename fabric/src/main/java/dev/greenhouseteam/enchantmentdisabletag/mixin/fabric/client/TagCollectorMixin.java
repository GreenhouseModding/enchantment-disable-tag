package dev.greenhouseteam.enchantmentdisabletag.mixin.fabric.client;

import dev.greenhouseteam.enchantmentdisabletag.EnchantmentDisableTag;
import net.minecraft.client.multiplayer.TagCollector;
import net.minecraft.core.RegistryAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TagCollector.class)
public class TagCollectorMixin {
    @Inject(method = "updateTags", at = @At("TAIL"))
    private void enchantmentdisabletag$updateTagValue(RegistryAccess registryAccess, boolean bl, CallbackInfo ci) {
        EnchantmentDisableTag.setCreativeTabToReload();
    }
}
