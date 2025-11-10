package lgbt.greenhouse.enchantmentdisabletag.mixin.fabric;

import lgbt.greenhouse.enchantmentdisabletag.EnchantmentDisableTag;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.ReloadableServerResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ReloadableServerResources.class)
public class Mixin_ReloadableServerResources {
    @Inject(method = "updateRegistryTags(Lnet/minecraft/core/RegistryAccess;)V", at = @At("TAIL"))
    private void enchantmentdisabletag$updateTagValue(RegistryAccess registryAccess, CallbackInfo ci) {
        EnchantmentDisableTag.setCreativeTabToReload();
    }
}
