package lgbt.greenhouse.enchantmentdisabletag.mixin.fabric.client;

import lgbt.greenhouse.enchantmentdisabletag.EnchantmentDisableTag;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundUpdateTagsPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class Mixin_ClientPacketListener {
    @Inject(method = "handleUpdateTags", at = @At("TAIL"))
    private void enchantmentdisabletag$setToReloadCreativeTabsClient(ClientboundUpdateTagsPacket packet, CallbackInfo ci) {
        EnchantmentDisableTag.setCreativeTabToReload();
    }
}
