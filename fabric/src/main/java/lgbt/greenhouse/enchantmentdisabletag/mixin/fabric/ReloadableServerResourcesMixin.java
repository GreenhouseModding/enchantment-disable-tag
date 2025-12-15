package lgbt.greenhouse.enchantmentdisabletag.mixin.fabric;

import lgbt.greenhouse.enchantmentdisabletag.EnchantmentDisableTag;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.tags.TagLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TagLoader.class)
public class ReloadableServerResourcesMixin {
    @Inject(method = "loadTagsForRegistry", at = @At("TAIL"))
    private static void enchantmentdisabletag$setToReloadCreativeTabsServer(CallbackInfo ci) {
        EnchantmentDisableTag.setCreativeTabToReload();
    }
}
